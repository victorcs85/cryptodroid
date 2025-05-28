package br.com.victorcs.cryptodroid.presentation.features.main.ui

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.filters.MediumTest
import br.com.victorcs.cryptodroid.presentation.MainActivity
import br.com.victorcs.cryptodroid.presentation.features.main.MainScreen
import br.com.victorcs.cryptodroid.presentation.features.main.MainViewModel
import br.com.victorcs.cryptodroid.shared.test.PresentationMockTest
import br.com.victorcs.cryptodroid.utils.TestDispatchersProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@MediumTest
class MainScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        MainViewModel(TestDispatchersProvider).apply {
            viewModel = this
        }
        viewModel.setTitleAppBar(PresentationMockTest.MAIN_SCREEN_TITLE)

        composeTestRule.activity.setContent {
            MainScreen(mainViewModel = viewModel)
        }
    }

    @Test
    fun givenATextTitle_whenLoadScreen_thenShowTitleCorrectly() {
        composeTestRule.onNodeWithText(PresentationMockTest.MAIN_SCREEN_TITLE).assertIsDisplayed()
    }
}