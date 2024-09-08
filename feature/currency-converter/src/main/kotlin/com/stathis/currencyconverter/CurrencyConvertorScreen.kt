package com.stathis.currencyconverter

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stathis.designsystem.R
import com.stathis.designsystem.components.MCBackgroundScreen
import com.stathis.designsystem.components.MCCard
import com.stathis.designsystem.components.MCTextMenu
import com.stathis.designsystem.components.McTextField
import com.stathis.designsystem.theme.MoneyConvertorTheme
import kotlinx.coroutines.launch

@Composable
fun CurrencyConvertorRoute(
    viewModel: CurrencyConvertorViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CurrencyConverterScreen(
        uiState = uiState,
        onFromCurrencyChange = viewModel::onFromCurrencyChange,
        onToCurrencyChange = viewModel::onToCurrencyChange,
        onSwap = viewModel::swapCurrencies
    )
}

@Composable
internal fun CurrencyConverterScreen(
    uiState: CurrencyConvertorUiState,
    onFromCurrencyChange: (CurrencyUiModel) -> Unit,
    onToCurrencyChange: (CurrencyUiModel) -> Unit,
    onSwap: () -> Unit
) {
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
                allCurrencies = uiState.allCurrencies,
                fromCurrency = uiState.fromCurrency,
                toCurrency = uiState.toCurrency,
                onFromCurrencyChanged = onFromCurrencyChange,
                onToCurrencyChanged = onToCurrencyChange,
                onSwap = onSwap
            )

            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "${stringResource(R.string.indicative_exchange_rate)} ${uiState.lastUpdated}",
                modifier = Modifier.padding(horizontal = 22.dp),
                style = MaterialTheme.typography.labelSmall
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = uiState.indicativeExchangeRate,
                modifier = Modifier.padding(horizontal = 22.dp),
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Color.Black
                )
            )
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
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
    onSwap: () -> Unit
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

        Spacer(modifier = Modifier.height(30.dp))

        CurrenciesSwapper(
            onSwap = onSwap
        )

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

@Composable
fun CurrenciesSwapper(
    modifier: Modifier = Modifier,
    onSwap: () -> Unit
) {
    val animatable = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    val scope = rememberCoroutineScope()
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        HorizontalDivider()
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .clickable {
                    if (animatable.isRunning) return@clickable

                    scope.launch {
                        onSwap()
                        animatable.animateTo(
                            animatable.value + 180f,
                            tween(300)
                        )
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .padding(10.dp)
                    .rotate(animatable.value),
                painter = painterResource(R.drawable.ic_swap_white),
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

@Preview
@Composable
fun CurrencyConverterScreenPreview(modifier: Modifier = Modifier) {
    MoneyConvertorTheme {
        CurrencyConverterScreen(
            uiState = CurrencyConvertorUiState.PreviewData,
            onFromCurrencyChange = {},
            onToCurrencyChange = {},
            onSwap = {},
        )
    }
}