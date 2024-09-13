package com.stathis.currencyconverter

import android.content.SharedPreferences
import com.stathis.model.currency_convertor.ExchangeRates
import com.stathis.testing.repository.TestCurrencyRepository
import com.stathis.testing.worker.TestWorkManagerSyncManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class CurrencyViewModelTest {

    lateinit var viewModel: CurrencyConvertorViewModel
    private lateinit var currencyRepository: TestCurrencyRepository

    @Mock
    lateinit var sharedPreferences: SharedPreferences

    private val workManagerSyncManager = TestWorkManagerSyncManager()

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        currencyRepository = TestCurrencyRepository()

        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        MockitoAnnotations.openMocks(this)

        viewModel = CurrencyConvertorViewModel(
            repository = currencyRepository,
            sharedPreferences = sharedPreferences,
            workManagerSyncManager = workManagerSyncManager
        )
    }

    @Test
    fun `initial state is loading`() {
        assert(viewModel.uiState.value.isLoading)
    }

    @Test
    fun `state is not loading when syncing is done`() = testScope.runTest {
        workManagerSyncManager.emit(false)

        val job = launch {
            viewModel.uiState.take(1).toList()
        }

        job.join()
        assert(viewModel.uiState.value.isLoading.not())
    }

    @Test
    fun `state has changed after a successful emition from repository`() = testScope.runTest {
        workManagerSyncManager.emit(true)

        currencyRepository.sendExchangeRates(
            ExchangeRates(
                baseCurrency = "USD",
                rates = mapOf("USD" to 10.0, "EUR" to 0.92),
                lastUpdatedDate = "16-09-2024"
            )
        )

        workManagerSyncManager.emit(false)

        val job = launch {
            viewModel.uiState.take(1).toList()
        }

        job.join()
        assert(viewModel.uiState.value.isLoading.not())
        assert(viewModel.uiState.value.indicativeExchangeRate.isNotEmpty())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}