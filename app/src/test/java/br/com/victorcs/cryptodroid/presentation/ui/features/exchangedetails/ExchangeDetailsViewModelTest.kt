package br.com.victorcs.cryptodroid.presentation.ui.features.exchangedetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import br.com.victorcs.cryptodroid.base.BaseViewModelTest
import br.com.victorcs.cryptodroid.base.CoroutineTestRule
import br.com.victorcs.cryptodroid.core.constants.EXCHANGE_ID
import br.com.victorcs.cryptodroid.core.model.Response
import br.com.victorcs.cryptodroid.domain.repository.IExchangeDetailsRepository
import br.com.victorcs.cryptodroid.presentation.features.exchangedetails.command.ExchangeDetailsCommand
import br.com.victorcs.cryptodroid.presentation.features.exchangedetails.ui.ExchangeDetailsViewModel
import br.com.victorcs.cryptodroid.shared.test.DataMockTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.util.UUID
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
@SmallTest
class ExchangeDetailsViewModelTest : BaseViewModelTest() {

    @get:Rule
    val coroutinesTestRule = CoroutineTestRule()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private val savedState = SavedStateHandle(mapOf(EXCHANGE_ID to UUID.randomUUID().toString()))

    private val repository: IExchangeDetailsRepository = mockk(relaxed = true)

    private lateinit var viewModel: ExchangeDetailsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun givenExchangeId_whenGetDetails_thenReturnSuccessfully() = runTest {
        viewModel = ExchangeDetailsViewModel(repository, savedState, testDispatcherProvider)
        val mockResponse = DataMockTest.mockSuccessExchangeDetailsResponse

        coEvery { repository.getExchangeDetails(any<String>()) } returns mockResponse

        viewModel.execute(
            ExchangeDetailsCommand.GetExchangeDetails(UUID.randomUUID().toString())
        )

        viewModel.screenState.test {
            val successResponse = awaitItem()
            assertTrue(
                successResponse.exchange != null &&
                        successResponse.exchange == DataMockTest.mockExchangeDetails.first()
            )
            cancelAndIgnoreRemainingEvents()
        }

        coVerify { repository.getExchangeDetails(any<String>()) }
    }

    @Test
    fun givenWrongExchangeId_whenGetDetails_thenReturnFail() = runTest {
        val expected = Response.Error(DataMockTest.DEFAULT_ERROR_MOCK)
        viewModel = ExchangeDetailsViewModel(repository, savedState, testDispatcherProvider)
        coEvery { repository.getExchangeDetails(any<String>()) } returns expected

        viewModel.execute(
            ExchangeDetailsCommand.GetExchangeDetails(UUID.randomUUID().toString())
        )

        viewModel.screenState.test {
            val failResponse = awaitItem()
            assertTrue(
                failResponse.exchange == null &&
                        failResponse.errorMessage == DataMockTest.DEFAULT_ERROR_MOCK
            )
            cancelAndIgnoreRemainingEvents()
        }

        coVerify { repository.getExchangeDetails(any<String>()) }
    }
}