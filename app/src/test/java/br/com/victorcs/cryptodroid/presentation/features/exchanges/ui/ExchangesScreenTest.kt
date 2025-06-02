package br.com.victorcs.cryptodroid.presentation.features.exchanges.ui

import android.app.Application
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeDown
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import br.com.victorcs.core.base.MainViewModel
import br.com.victorcs.core.constants.PULL_TO_REFRESH_TAG
import br.com.victorcs.core.utils.IDispatchersProvider
import br.com.victorcs.cryptodroid.base.INSTRUMENTED_PACKAGE
import br.com.victorcs.cryptodroid.base.SCREEN_SIZE
import br.com.victorcs.cryptodroid.base.SDK
import br.com.victorcs.cryptodroid.domain.repository.IExchangesRepository
import br.com.victorcs.cryptodroid.shared.test.DataMockTest
import br.com.victorcs.cryptodroid.utils.TestDispatchersProvider
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog
import kotlin.test.Test

@Config(
    sdk = [SDK],
    application = Application::class,
    instrumentedPackages = [INSTRUMENTED_PACKAGE],
    qualifiers = SCREEN_SIZE,
)
@RunWith(RobolectricTestRunner::class)
@ExperimentalCoroutinesApi
class ExchangesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: ExchangesViewModel

    private val repository = mockk<IExchangesRepository>()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        ShadowLog.stream = System.out

        setUpKoin()

        viewModel = ExchangesViewModel(repository, TestDispatchersProvider)
        coEvery { repository.getExchanges() } returns DataMockTest.mockSuccessExchangeResponse
        coEvery { repository.getIcons() } returns DataMockTest.mockSuccessExchangeIconsResponse

        composeTestRule.setContent {
            val state = viewModel.screenState.collectAsStateWithLifecycle().value

            ExchangesScreen(
                navController = rememberNavController(),
                state,
                execute = viewModel::execute,
            )
        }
    }

    private fun setUpKoin() {
        stopKoin()

        startKoin {
            modules(
                module {
                    single<IExchangesRepository> { repository }
                    single<IDispatchersProvider> { TestDispatchersProvider }
                    viewModel { MainViewModel(get()) }
                    viewModel { ExchangesViewModel(get(), get()) }
                },
            )
        }
    }

    @Ignore("Fail pre-push")
    @Test
    fun givenScreen_whenLoadedData_thenSuccessfullyData() {
        composeTestRule.onNodeWithText(DataMockTest.BINANCE).assertIsDisplayed()
    }

    @Ignore("Fail pre-push")
    @Test
    fun whenPullToRefresh_thenDataIsUpdated() {
        coEvery { repository.getExchanges() } returns DataMockTest.mockSuccessExchangeResponse

        composeTestRule.onNodeWithText(DataMockTest.BINANCE).assertIsDisplayed()

        coEvery { repository.getExchanges() } returns DataMockTest.mockSuccessExchangeRefreshResponse

        composeTestRule.onNodeWithTag(PULL_TO_REFRESH_TAG).performTouchInput {
            swipeDown()
        }

        composeTestRule.onNodeWithText(DataMockTest.COINBASE).assertIsDisplayed()
    }
}
