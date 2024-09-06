package com.stathis.currencyconverter

data class CurrencyConvertorUiState(
    val isLoading: Boolean = true,
    val allCurrencies: List<CurrencyUiModel> = emptyList(),
    val fromCurrency: CurrencyUiModel = CurrencyUiModel("", ""),
    val toCurrency: CurrencyUiModel = CurrencyUiModel("", ""),
    val indicativeExchangeRate: String = "",
    val lastUpdated: String = ""
) {
    companion object {
        val PreviewData = CurrencyConvertorUiState(
            fromCurrency = CurrencyUiModel(code = "USD", "1000.00"),
            toCurrency = CurrencyUiModel(code = "EUR", "1001.00"),
            indicativeExchangeRate = "1 USD = 1SD"
        )
    }
}