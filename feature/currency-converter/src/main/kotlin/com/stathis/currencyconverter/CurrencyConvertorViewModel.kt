package com.stathis.currencyconverter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stathis.data.repository.CurrencyRepository
import com.stathis.model.currency_convertor.CurrencyInfo
import com.stathis.model.currency_convertor.ExchangeRates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CurrencyConvertorViewModel @Inject constructor(
    private val repository: CurrencyRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CurrencyConvertorUiState())

    val uiState = _uiState.asStateFlow()

    private lateinit var exchangeRates: ExchangeRates

    init {
        initUiState()
    }

    private fun initUiState() {
        val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
            //temp
        }

        viewModelScope.launch(handler) {
            exchangeRates = repository.getExchangeRates()

            _uiState.update {
                it.copy(
                    isLoading = false,
                    allCurrencies = exchangeRates.rates.keys.map {
                        CurrencyUiModel(code = it, value = "")
                    },
                    lastUpdated = exchangeRates.lastUpdatedDate,
                )
            }
            setInitialCurrencies()
        }
    }

    private fun setInitialCurrencies() {
        val initialCurrencies = getUserCurrencies()
        val convertedResult = convert(
            fromVsBaseValue = initialCurrencies.first.value,
            toVsBaseValue = initialCurrencies.second.value,
            amount = 1.0
        )

        _uiState.update {
            it.copy(
                fromCurrency = CurrencyUiModel(
                    code = initialCurrencies.first.code,
                    "1.00"
                ),
                toCurrency = CurrencyUiModel(
                    code = initialCurrencies.second.code,
                    convertedResult
                ),
                indicativeExchangeRate = "1 ${initialCurrencies.first.code} = $convertedResult ${initialCurrencies.second.code}"
            )
        }
    }

    private fun getUserCurrencies(): Pair<CurrencyInfo, CurrencyInfo> {
        val fromCurrency = CurrencyInfo("USD", exchangeRates.rates.getValue("USD"))
        val toCurrency = CurrencyInfo("EUR", exchangeRates.rates.getValue("EUR"))
        return Pair(fromCurrency, toCurrency)
    }

    private fun convert(
        fromVsBaseValue: Double,
        toVsBaseValue: Double,
        amount: Double
    ): String {
        return CurrencyConverter.convert(fromVsBaseValue, toVsBaseValue, amount).let {
            String.format(Locale.ENGLISH, "%.2f", it)
        }
    }
}