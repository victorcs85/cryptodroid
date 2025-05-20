package br.com.victorcs.cryptodroid.presentation.features.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import br.com.victorcs.cryptodroid.presentation.navigation.AppNavigation
import br.com.victorcs.cryptodroid.presentation.views.BottomNavigationBar
import br.com.victorcs.cryptodroid.presentation.views.ExchangeTopAppBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = koinViewModel(),
) {
    val navController = rememberNavController()
    val title = mainViewModel.titleAppBar.collectAsStateWithLifecycle().value

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            ExchangeTopAppBar(title = title)
        },
        content = { innerPadding ->
            AppNavigation(innerPadding, navController)
        },
        bottomBar = {
            BottomNavigationBar(navController)
        },
    )
}
