package com.example.moneyconvertor.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.stathis.currencyconverter.navigation.CurrencyConvertorRoute
import com.stathis.currencyconverter.navigation.currencyConvertorScreen

@Composable
fun MoneyConvertorNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = CurrencyConvertorRoute
    ) {
        currencyConvertorScreen()
    }
}