package br.com.victorcs.cryptodroid.presentation.ui.features.main

import android.app.Application
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import br.com.victorcs.core.utils.IDispatchersProvider
import br.com.victorcs.cryptodroid.base.ComposeUiTest
import br.com.victorcs.cryptodroid.base.INSTRUMENTED_PACKAGE
import br.com.victorcs.cryptodroid.base.SCREEN_SIZE
import br.com.victorcs.cryptodroid.base.SDK
import br.com.victorcs.cryptodroid.domain.repository.IExchangesRepository
import br.com.victorcs.cryptodroid.presentation.features.exchanges.ui.ExchangesViewModel
import br.com.victorcs.cryptodroid.presentation.features.main.MainScreen
import br.com.victorcs.cryptodroid.presentation.features.main.MainViewModel
import br.com.victorcs.cryptodroid.shared.test.DataMockTest
import br.com.victorcs.cryptodroid.utils.TestDispatchersProvider
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
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

@ComposeUiTest
@Config(
    sdk = [SDK],
    application = Application::class,
    instrumentedPackages = [INSTRUMENTED_PACKAGE],
    qualifiers = SCREEN_SIZE,
)
@RunWith(RobolectricTestRunner::class)
@ExperimentalCoroutinesApi
class MainScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: MainViewModel

    private val repository = mockk<IExchangesRepository>()

    @Before
    fun setUp() {
        ShadowLog.stream = System.out

        setUpKoin()

        viewModel = MainViewModel(TestDispatchersProvider).apply {
            setTitleAppBar(DataMockTest.MAIN_SCREEN_TITLE)
        }

        composeTestRule.setContent {
            MainScreen(mainViewModel = viewModel)
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

    @Test
    fun givenATextTitle_whenLoadScreen_thenShowTitleCorrectly() {
        composeTestRule.onNodeWithText(DataMockTest.MAIN_SCREEN_TITLE).assertIsDisplayed()
    }
}
