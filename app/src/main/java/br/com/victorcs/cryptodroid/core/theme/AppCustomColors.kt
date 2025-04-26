package br.com.victorcs.cryptodroid.core.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppCustomColors(
    val appBarBackground: Color,
    val appBarInfo: Color,
    val pullToRefreshBackground: Color,
    val pullToRefreshArrow: Color,
    val exchangeTitle: Color,
    val exchangeDetailsTitle: Color,
    val exchangeInfo: Color,
    val exchangeVolume: Color,
    val exchangeBorder: Color,
    val cardExchangesShaderGradient: List<Color>,
    val cardExchangeDetailsShaderGradient: List<Color>,
    val divider: Color,
    val buttonBackground: Color,
)

val LightAppCustomColors = AppCustomColors(
    appBarBackground = LightAppBarBackground,
    appBarInfo = LightAppBarInfo,
    pullToRefreshBackground = LightPullToRefreshBackground,
    pullToRefreshArrow = LightPullToRefreshArrow,
    exchangeTitle = LightExchangeTitle,
    exchangeDetailsTitle = LightExchangeDetailsTitle,
    exchangeInfo = LightExchangeInfo,
    exchangeVolume = LightExchangeVolume,
    exchangeBorder = LightExchangeBorder,
    cardExchangesShaderGradient = LightCardExchangesShaderGradient,
    cardExchangeDetailsShaderGradient = LightCardExchangeDetailsShaderGradient,
    divider = GrayAlpha30,
    buttonBackground = LightButtonBackground,
)

val DarkAppCustomColors = AppCustomColors(
    appBarBackground = DarkAppBarBackground,
    appBarInfo = DarkAppBarInfo,
    pullToRefreshBackground = DarkPullToRefreshBackground,
    pullToRefreshArrow = DarkPullToRefreshArrow,
    exchangeTitle = DarkExchangeTitle,
    exchangeDetailsTitle = DarkExchangeDetailsTitle,
    exchangeInfo = DarkExchangeInfo,
    exchangeVolume = DarkExchangeVolume,
    exchangeBorder = DarkExchangeBorder,
    cardExchangesShaderGradient = DarkCardExchangesShaderGradient,
    cardExchangeDetailsShaderGradient = DarkCardExchangeDetailsShaderGradient,
    divider = WhiteAlpha30,
    buttonBackground = DarkButtonBackground,
)

val LocalCustomColors = compositionLocalOf { LightAppCustomColors }
