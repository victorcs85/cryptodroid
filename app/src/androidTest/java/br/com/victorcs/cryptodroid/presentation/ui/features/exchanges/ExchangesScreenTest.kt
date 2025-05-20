package br.com.victorcs.cryptodroid.presentation.ui.features.exchanges

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeDown
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import br.com.victorcs.core.constants.PULL_TO_REFRESH_TAG
import br.com.victorcs.cryptodroid.domain.repository.IExchangesRepository
import br.com.victorcs.cryptodroid.presentation.MainActivity
import br.com.victorcs.cryptodroid.presentation.features.exchanges.ui.ExchangesScreen
import br.com.victorcs.cryptodroid.presentation.features.exchanges.ui.ExchangesViewModel
import br.com.victorcs.cryptodroid.shared.test.PresentationMockTest
import br.com.victorcs.cryptodroid.utils.TestDispatchersProvider
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@MediumTest
class ExchangesScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var viewModel: ExchangesViewModel

    private val repository = mockk<IExchangesRepository>()

    @Before
    fun setUp() {
        viewModel = ExchangesViewModel(repository, TestDispatchersProvider)
        coEvery { repository.getExchanges() } returns PresentationMockTest.mockSuccessExchangeResponse
        coEvery { repository.getIcons() } returns PresentationMockTest.mockSuccessExchangeIconsResponse

        composeTestRule.activity.setContent {
            val state = viewModel.screenState.collectAsStateWithLifecycle().value

            ExchangesScreen(
                navController = rememberNavController(),
                state,
                execute = viewModel::execute
            )
        }
    }

    @Test
    fun givenScreen_whenLoadedData_thenSuccessfullyData() {
        composeTestRule.run {
            onNodeWithText(PresentationMockTest.BINANCE).assertIsDisplayed()
        }
    }

    @Test
    fun givenAction_whenPullToRefresh_thenDataIsUpdated() {
        coEvery { repository.getExchanges() } returns PresentationMockTest.mockSuccessExchangeResponse

        composeTestRule.run {
            onNodeWithText(PresentationMockTest.BINANCE).assertIsDisplayed()

            coEvery { repository.getExchanges() } returns PresentationMockTest.mockSuccessExchangeRefreshResponse

            onNodeWithTag(PULL_TO_REFRESH_TAG).performTouchInput {
                swipeDown()
            }

            onNodeWithText(PresentationMockTest.COINBASE).assertIsDisplayed()
        }
    }

    @Test
    fun givenScreen_whenRotated_thenStateIsPreserved() {
        composeTestRule.run {
            onNodeWithText(PresentationMockTest.BINANCE).assertIsDisplayed()

            UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).also { device ->
                device.setOrientationLeft()
                device.setOrientationNatural()
            }

            onNodeWithText(PresentationMockTest.BINANCE).assertIsDisplayed()
        }
    }
}
