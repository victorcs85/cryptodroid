package br.com.victorcs.cryptodroid.presentation.navigation

private const val EXCHANGE_SCREEN = "exchanges"
private const val LIGHTNING_SCREEN = "lightning"
private const val EXCHANGE_DETAILS_SCREEN = "details/{exchangeId}"

sealed class ScreenRouter(val route: String) {
    object Exchanges : ScreenRouter(EXCHANGE_SCREEN)
    object ExchangeDetails : ScreenRouter(EXCHANGE_DETAILS_SCREEN)
    object Lightning : ScreenRouter(LIGHTNING_SCREEN)
}
