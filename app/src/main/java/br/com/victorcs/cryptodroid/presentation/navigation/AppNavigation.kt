package br.com.victorcs.cryptodroid.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.victorcs.core.constants.EXCHANGE_ID
import br.com.victorcs.cryptodroid.presentation.features.exchangedetails.ui.ExchangeDetailScreen
import br.com.victorcs.cryptodroid.presentation.features.exchangedetails.ui.ExchangeDetailsViewModel
import br.com.victorcs.cryptodroid.presentation.features.exchanges.ui.ExchangesScreen
import br.com.victorcs.cryptodroid.presentation.features.exchanges.ui.ExchangesViewModel
import br.com.victorcs.lightning.presentation.features.lightnings.ui.LightningsScreen
import org.koin.androidx.compose.koinViewModel


private const val ANIMATION_TIMER = 300

@Composable
fun AppNavigation(innerPadding: PaddingValues, navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = ScreenRouter.Exchanges.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(ScreenRouter.Exchanges.route) {
            val viewModel: ExchangesViewModel = koinViewModel()
            val state = viewModel.screenState.collectAsStateWithLifecycle().value

            ExchangesScreen(navController, state, viewModel::execute)
        }
        composable(
            ScreenRouter.ExchangeDetails.route,
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
        composable(ScreenRouter.Lightning.route) {
            LightningsScreen()
        }
    }
}
