package br.com.victorcs.cryptodroid.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.victorcs.cryptodroid.core.constants.EXCHANGE_ID
import br.com.victorcs.cryptodroid.presentation.features.exchangedetails.ui.ExchangeDetailScreen
import br.com.victorcs.cryptodroid.presentation.features.exchangedetails.ui.ExchangeDetailsViewModel
import br.com.victorcs.cryptodroid.presentation.features.exchanges.ui.ExchangesScreen
import br.com.victorcs.cryptodroid.presentation.features.exchanges.ui.ExchangesViewModel
import org.koin.androidx.compose.koinViewModel

private const val EXCHANGE_SCREEN = "exchanges"
private const val EXCHANGE_DETAILS_SCREEN = "details/{exchangeId}"

private const val ANIMATION_TIMER = 300

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = EXCHANGE_SCREEN) {
        composable(EXCHANGE_SCREEN) {
            val viewModel: ExchangesViewModel = koinViewModel()
            val state = viewModel.screenState.collectAsStateWithLifecycle().value

            ExchangesScreen(navController, state, viewModel::execute)
        }
        composable(
            EXCHANGE_DETAILS_SCREEN,
            arguments = listOf(navArgument(EXCHANGE_ID) { type = NavType.StringType }),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        ANIMATION_TIMER, easing = LinearEasing,
                    ),
                ) + slideIntoContainer(
                    animationSpec = tween(ANIMATION_TIMER, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        ANIMATION_TIMER, easing = LinearEasing,
                    ),
                ) + slideOutOfContainer(
                    animationSpec = tween(ANIMATION_TIMER, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                )
            },
        ) {
            val exchangeDetailsViewModel: ExchangeDetailsViewModel = koinViewModel()
            val state = exchangeDetailsViewModel.screenState.collectAsStateWithLifecycle().value

            ExchangeDetailScreen(navController, state, exchangeDetailsViewModel::execute)
        }
    }
}
