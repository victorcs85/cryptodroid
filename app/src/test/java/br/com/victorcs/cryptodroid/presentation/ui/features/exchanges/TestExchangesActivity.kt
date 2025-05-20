package br.com.victorcs.cryptodroid.presentation.ui.features.exchanges

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import br.com.victorcs.cryptodroid.presentation.features.exchanges.ui.ExchangesScreen
import br.com.victorcs.cryptodroid.presentation.features.exchanges.ui.ExchangesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TestExchangesActivity : ComponentActivity() {

    private val viewModel: ExchangesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val state = viewModel.screenState.collectAsStateWithLifecycle().value

            ExchangesScreen(
                navController = rememberNavController(),
                state = state,
                execute = viewModel::execute
            )
        }
    }
}