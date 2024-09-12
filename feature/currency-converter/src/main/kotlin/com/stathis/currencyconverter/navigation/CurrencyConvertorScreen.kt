package com.stathis.currencyconverter.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.stathis.currencyconverter.CurrencyConvertorRoute

const val CurrencyConvertorRoute = "currencyConvertorRoute"
fun NavGraphBuilder.currencyConvertorScreen() {
    composable(CurrencyConvertorRoute) {
        CurrencyConvertorRoute()
    }
}