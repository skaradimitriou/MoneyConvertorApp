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

    fun onFromCurrencyChange(fromCurrency: CurrencyUiModel) {
        when (val validationResult = fromCurrency.value.safeToDouble()) {
            is StringToDoubleConversionResult.Valid -> {
                with(uiState.value) {
                    val convertResult = convert(
                        fromVsBaseValue = exchangeRates.rates.getValue(fromCurrency.code),
                        toVsBaseValue = exchangeRates.rates.getValue(toCurrency.code),
                        amount = validationResult.value
                    )
                    _uiState.update {
                        it.copy(
                            fromCurrency = fromCurrency,
                            toCurrency = toCurrency.copy(value = convertResult),
                            indicativeExchangeRate = "1 ${fromCurrency.code} = ${
                                getIndicativeExchangeRate(
                                    fromCurrency.code,
                                    toCurrency.code
                                )
                            } ${toCurrency.code}"
                        )
                    }
                }
            }

            is StringToDoubleConversionResult.Empty -> {
                _uiState.update {
                    it.copy(
                        fromCurrency = it.fromCurrency.copy(value = ""),
                        toCurrency = it.toCurrency.copy(value = "")
                    )
                }
            }

            is StringToDoubleConversionResult.InValid -> Unit
        }
    }

    private fun getIndicativeExchangeRate(
        fromCurrencyCode: String,
        toCurrencyCode: String
    ): String {
        return convert(
            fromVsBaseValue = exchangeRates.rates.getValue(fromCurrencyCode),
            toVsBaseValue = exchangeRates.rates.getValue(toCurrencyCode),
            amount = 1.0
        )
    }

    private fun String.safeToDouble(): StringToDoubleConversionResult {
        return if (this.endsWith(".")) {
            //example: user types "12."
            StringToDoubleConversionResult.Valid(value = dropLast(1).toDouble())
        } else if (this.isEmpty()) {
            StringToDoubleConversionResult.Empty
        } else {
            this.toDoubleOrNull()?.let {
                StringToDoubleConversionResult.Valid(it)
            } ?: StringToDoubleConversionResult.InValid
        }
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

private sealed interface StringToDoubleConversionResult {
    data class Valid(val value: Double) : StringToDoubleConversionResult
    data object InValid : StringToDoubleConversionResult
    data object Empty : StringToDoubleConversionResult
}