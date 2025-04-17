package br.com.victorcs.cryptodroid.presentation.ui.features.exchanges

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import br.com.victorcs.cryptodroid.base.BaseViewModelTest
import br.com.victorcs.cryptodroid.base.CoroutineTestRule
import br.com.victorcs.cryptodroid.core.extensions.orFalse
import br.com.victorcs.cryptodroid.core.model.Response
import br.com.victorcs.cryptodroid.domain.repository.IExchangesRepository
import br.com.victorcs.cryptodroid.presentation.features.exchanges.command.ExchangesCommand
import br.com.victorcs.cryptodroid.presentation.features.exchanges.ui.ExchangesViewModel
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
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
@SmallTest
class ExchangesViewModelTest : BaseViewModelTest() {

    @get:Rule
    val coroutinesTestRule = CoroutineTestRule()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private val repository = mockk<IExchangesRepository>(relaxed = true)

    private lateinit var viewModel: ExchangesViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ExchangesViewModel(repository, testDispatcherProvider)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun givenSuccess_whenGetExchanges_thenReturnSuccessfully() = runTest {
        val mockResponse = DataMockTest.mockSuccessExchangeResponse

        coEvery { repository.getExchanges() } returns mockResponse
        coEvery { repository.getIcons() } returns DataMockTest.mockSuccessExchangeIconsResponse

        viewModel.execute(
            ExchangesCommand.FetchExchanges
        )

        viewModel.screenState.test {
            val successResponse = awaitItem()
            assertTrue(
                successResponse.exchanges != null && successResponse.exchanges?.isEmpty()?.not().orFalse() &&
                        successResponse.exchanges?.first() == DataMockTest.mockExchangeDetails.first()
            )
            cancelAndIgnoreRemainingEvents()
        }

        coVerify { repository.getExchanges() }
    }

    @Test
    fun givenErrorGetIcons_whenGetExchanges_thenReturnSuccessfully() = runTest {
        val mockResponse = DataMockTest.mockSuccessExchangeResponse

        coEvery { repository.getExchanges() } returns mockResponse
        coEvery { repository.getIcons() } returns Response.Error(DataMockTest.DEFAULT_ERROR_MOCK)

        viewModel.execute(
            ExchangesCommand.FetchExchanges
        )

        viewModel.screenState.test {
            val successResponse = awaitItem()
            assertTrue(
                successResponse.exchanges != null && successResponse.exchanges?.isEmpty()?.not().orFalse() &&
                        successResponse.exchanges?.first() == DataMockTest.mockExchangeDetails.first()
            )
            cancelAndIgnoreRemainingEvents()
        }

        coVerify { repository.getExchanges() }
    }

    @Test
    fun givenFailRequest_whenGetExchanges_thenReturnFail() = runTest {

        val expected = Response.Error(DataMockTest.DEFAULT_ERROR_MOCK)
        coEvery { repository.getExchanges() } returns expected

        coEvery { repository.getIcons() } returns expected

        viewModel.execute(
            ExchangesCommand.FetchExchanges
        )

        viewModel.screenState.test {
            val failResponse = awaitItem()
            assertTrue(
                failResponse.exchanges == null &&
                        failResponse.errorMessage == DataMockTest.DEFAULT_ERROR_MOCK
            )
            cancelAndIgnoreRemainingEvents()
        }

        coVerify { repository.getExchanges() }
    }
}