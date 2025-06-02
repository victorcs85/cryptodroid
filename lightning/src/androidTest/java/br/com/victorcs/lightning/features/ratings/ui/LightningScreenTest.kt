package br.com.victorcs.lightning.features.ratings.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.test.filters.MediumTest
import br.com.victorcs.lightning.domain.repository.IRankingsConnectivityRepository
import br.com.victorcs.lightning.presentation.features.ratings.ui.LightningRatingsViewModel
import br.com.victorcs.lightning.presentation.features.ratings.ui.LightningsScreen
import br.com.victorcs.lightning.shared.test.PresentationMockTest
import br.com.victorcs.lightning.utils.FakeMainActivity
import br.com.victorcs.lightning.utils.TestDispatchersProvider
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@MediumTest
class LightningScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<FakeMainActivity>()

    private lateinit var viewModel: LightningRatingsViewModel

    private val repository = mockk<IRankingsConnectivityRepository>()

    @Before
    fun setUp() {
        viewModel = LightningRatingsViewModel(repository, TestDispatchersProvider)

        coEvery { repository.getRankingsConnectivity() } returns PresentationMockTest.mockSuccessLightningResponse

        composeTestRule.setContent {
            val state = viewModel.screenState.collectAsStateWithLifecycle().value

            LightningsScreen(
                state = state,
                execute = viewModel::execute,
            )
        }
    }

    @Test
    fun givenScreen_whenLoaded_thenDisplayLightningData() {
        composeTestRule.run {
            onNodeWithText(PresentationMockTest.lightningPublicKey).assertIsDisplayed()
        }
    }
}
