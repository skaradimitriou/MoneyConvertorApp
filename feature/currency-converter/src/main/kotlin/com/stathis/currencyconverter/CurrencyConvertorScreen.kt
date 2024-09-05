package com.stathis.currencyconverter

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stathis.designsystem.R
import com.stathis.designsystem.components.MCBackgroundScreen
import com.stathis.designsystem.components.MCCard
import com.stathis.designsystem.components.MCTextMenu
import com.stathis.designsystem.components.McTextField
import com.stathis.designsystem.theme.MoneyConvertorTheme

@Composable
internal fun CurrencyConvertorRoute() {

}

@Composable
internal fun CurrencyConverterScreen() {
    MCBackgroundScreen {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.currency_converter),
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(R.string.currency_converter_description),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = Color(0xff808080)
            )

            Spacer(modifier = Modifier.height(50.dp))
            CurrencyConverterCard(
                allCurrencies = listOf(),
                fromCurrency = CurrencyUiModel("USD", ""),
                toCurrency = CurrencyUiModel("USD", ""),
                onFromCurrencyChanged = {},
                onToCurrencyChanged = {

                },
                swapCurrencies = {

                }
            )
        }
    }
}

@Composable
private fun CurrencyConverterCard(
    modifier: Modifier = Modifier,
    allCurrencies: List<CurrencyUiModel>,
    fromCurrency: CurrencyUiModel,
    toCurrency: CurrencyUiModel,
    onFromCurrencyChanged: (CurrencyUiModel) -> Unit,
    onToCurrencyChanged: (CurrencyUiModel) -> Unit,
    swapCurrencies: () -> Unit
) {
    MCCard(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        CurrencyInfoRow(
            label = stringResource(R.string.amount),
            selectedCurrency = fromCurrency,
            currencies = allCurrencies,
            onToCurrencyChange = onFromCurrencyChanged
        )

        //FIXME: Add the Swapper

        Spacer(modifier = Modifier.height(30.dp))
        CurrencyInfoRow(
            label = stringResource(R.string.converted_amount),
            selectedCurrency = toCurrency,
            currencies = allCurrencies,
            onToCurrencyChange = onToCurrencyChanged
        )
    }
}

@Composable
private fun CurrencyInfoRow(
    modifier: Modifier = Modifier,
    label: String,
    selectedCurrency: CurrencyUiModel,
    currencies: List<CurrencyUiModel>,
    onToCurrencyChange: (CurrencyUiModel) -> Unit
) {
    val currencyCodes = remember(currencies) {
        currencies.map { it.code }
    }

    Column(
        modifier = modifier
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall
        )
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedContent(
                modifier = Modifier.weight(1f),
                targetState = selectedCurrency.code
            ) {
                MCTextMenu(
                    selectedOption = it,
                    options = currencyCodes,
                    onOptionSelected = { index ->
                        onToCurrencyChange(currencies[index].copy(value = selectedCurrency.value))
                    }
                )
            }
            Spacer(modifier = Modifier.height(30.dp))

            McTextField(
                modifier = Modifier.weight(1f),
                text = selectedCurrency.value,
                onValueChange = {
                    onToCurrencyChange(selectedCurrency.copy(value = it))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
        }
    }
}

@Preview
@Composable
fun CurrencyConverterScreenPreview(modifier: Modifier = Modifier) {
    MoneyConvertorTheme {
        CurrencyConverterScreen()
    }
}