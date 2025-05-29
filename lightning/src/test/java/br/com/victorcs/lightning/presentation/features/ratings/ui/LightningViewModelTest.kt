package br.com.victorcs.lightning.presentation.features.ratings.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import br.com.victorcs.core.extensions.orFalse
import br.com.victorcs.core.model.Response
import br.com.victorcs.lightning.base.BaseViewModelTest
import br.com.victorcs.lightning.base.CoroutineTestRule
import br.com.victorcs.lightning.domain.repository.IRankingsConnectivityRepository
import br.com.victorcs.lightning.presentation.features.ratings.command.LightningRatingsCommand
import br.com.victorcs.lightning.shared.test.DataMockTest
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
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
@SmallTest
class LightningViewModelTest : BaseViewModelTest() {

    @get:Rule
    val coroutinesTestRule = CoroutineTestRule()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private val repository = mockk<IRankingsConnectivityRepository>(relaxed = true)

    private lateinit var viewModel: LightningRatingsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = LightningRatingsViewModel(repository, testDispatcherProvider)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun givenSuccess_whenFetchLightning_thenReturnSuccessfully() = runTest {
        coEvery { repository.getRankingsConnectivity() } returns DataMockTest.mockSuccessLightning

        viewModel.execute(LightningRatingsCommand.GetRatings)

        viewModel.screenState.test {
            val state = awaitItem()
            assertTrue(state.ratings.isNotEmpty().orFalse())
            cancelAndIgnoreRemainingEvents()
        }

        coVerify { repository.getRankingsConnectivity() }
    }

    @Test
    fun givenFailure_whenFetchLightning_thenReturnError() = runTest {
        coEvery { repository.getRankingsConnectivity() } returns Response.Error(DataMockTest.MOCK_DEFAULT_ERROR)

        viewModel.execute(LightningRatingsCommand.GetRatings)

        viewModel.screenState.test {
            val state = awaitItem()
            assertTrue(state.ratings.isEmpty())
            assertEquals(DataMockTest.MOCK_DEFAULT_ERROR, state.errorMessage)
            cancelAndIgnoreRemainingEvents()
        }

        coVerify { repository.getRankingsConnectivity() }
    }
}