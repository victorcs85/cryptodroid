package br.com.victorcs.cryptodroid.presentation.ui.features.exchangedetails

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import androidx.test.filters.MediumTest
import br.com.victorcs.core.constants.EXCHANGE_ID
import br.com.victorcs.cryptodroid.domain.repository.IExchangeDetailsRepository
import br.com.victorcs.cryptodroid.presentation.MainActivity
import br.com.victorcs.cryptodroid.presentation.features.exchangedetails.ui.ExchangeDetailScreen
import br.com.victorcs.cryptodroid.presentation.features.exchangedetails.ui.ExchangeDetailsViewModel
import br.com.victorcs.cryptodroid.shared.test.PresentationMockTest
import br.com.victorcs.cryptodroid.utils.TestDispatchersProvider
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import java.util.UUID

@MediumTest
class ExchangeDetailsScreenTest : KoinTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val testExchangeId = UUID.randomUUID().toString()

    private lateinit var viewModel: ExchangeDetailsViewModel

    private val repository = mockk<IExchangeDetailsRepository>()

    @Before
    fun setUp() {
        val savedState = mockk<SavedStateHandle>(relaxed = true)
        every { savedState.get<String>(EXCHANGE_ID) } returns testExchangeId
        viewModel = ExchangeDetailsViewModel(repository, savedState, TestDispatchersProvider)
        coEvery { repository.getExchangeDetails(any<String>()) } returns
            PresentationMockTest.mockSuccessExchangeDetailsResponse

        composeTestRule.activity.setContent {
            val state = viewModel.screenState.collectAsStateWithLifecycle().value
            ExchangeDetailScreen(navController = rememberNavController(), state, execute = viewModel::execute)
        }
    }

    @Test
    fun givenScreen_whenLoadedData_thenSuccessfullyData() = runTest {
        composeTestRule.run {
            onNodeWithText(PresentationMockTest.COINBASE_URL).assertIsDisplayed()
        }
    }
}
