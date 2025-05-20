package br.com.victorcs.cryptodroid.presentation.ui.features.main

import androidx.test.filters.SmallTest
import app.cash.turbine.test
import br.com.victorcs.cryptodroid.base.BaseViewModelTest
import br.com.victorcs.cryptodroid.base.CoroutineTestRule
import br.com.victorcs.cryptodroid.presentation.features.main.MainViewModel
import br.com.victorcs.cryptodroid.shared.test.DataMockTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
@SmallTest
class MainViewModelTest : BaseViewModelTest() {

    @get:Rule
    val coroutinesTestRule = CoroutineTestRule()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel(testDispatcherProvider)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun givenTitle_whenSetTitleAppBar_thenUpdateData() = runTest {
        val expectedTitle = DataMockTest.TEST_TITLE
        viewModel.setTitleAppBar(expectedTitle)

        viewModel.titleAppBar.test {
            val actualTitle = awaitItem()
            assertEquals(expectedTitle, actualTitle)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
