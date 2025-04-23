package br.com.victorcs.cryptodroid.presentation.theme

import androidx.compose.ui.graphics.Color

private const val MERCADO_BITCOIN_ORANGE = 0xFFD14C0C
private const val ALPHA_30_PERCENTAGE = 0.3f
private const val ALPHA_80_PERCENTAGE = 0.8f
private const val BLACK = 0xFF000000
private const val TRANSPARENT = 0x00000000
private const val YELLOW_GOLD = 0xFF5E531B
private const val WHITE = 0xFFFFFFFF
private const val GRAY_30_ALPHA = 0x4D989898
private const val WHITE_30_ALPHA = 0x4DFFFFFF

internal val MercadoBitcoinOrange = Color(MERCADO_BITCOIN_ORANGE)
internal val MercadoBitcoinOrangeAlpha30 = MercadoBitcoinOrange.copy(alpha = ALPHA_30_PERCENTAGE)
internal val MercadoBitcoinOrangeAlpha80 = MercadoBitcoinOrange.copy(alpha = ALPHA_80_PERCENTAGE)
internal val Black = Color(BLACK)
internal val Transparent = Color(TRANSPARENT)
internal val YellowGold = Color(YELLOW_GOLD)
internal val White = Color(WHITE)
internal val GrayAlpha30 = Color(GRAY_30_ALPHA)
internal val WhiteAlpha30 = Color(WHITE_30_ALPHA)

val LightBackground = White
val DarkBackground = Black

val LightAppBarBackground = MercadoBitcoinOrange
val DarkAppBarBackground = Black

val LightAppBarInfo = White
val DarkAppBarInfo = White

val LightPullToRefreshBackground = MercadoBitcoinOrange
val DarkPullToRefreshBackground = MercadoBitcoinOrange

val LightPullToRefreshArrow = White
val DarkPullToRefreshArrow = Black

val LightExchangeTitle = Black
val DarkExchangeTitle = White

val LightExchangeDetailsTitle = Black
val DarkExchangeDetailsTitle = White

val LightExchangeInfo = Black
val DarkExchangeInfo = White

val LightExchangeVolume = YellowGold
val DarkExchangeVolume = YellowGold

val LightExchangeBorder = MercadoBitcoinOrangeAlpha30
val DarkExchangeBorder = MercadoBitcoinOrangeAlpha80

val LightCardExchangesShaderGradient: List<Color> = listOf(MercadoBitcoinOrangeAlpha30, Transparent)
val DarkCardExchangesShaderGradient: List<Color> = listOf(MercadoBitcoinOrangeAlpha30, Transparent)

val LightCardExchangeDetailsShaderGradient: List<Color> = listOf(MercadoBitcoinOrangeAlpha80, MercadoBitcoinOrange)
val DarkCardExchangeDetailsShaderGradient: List<Color> = listOf(MercadoBitcoinOrangeAlpha80, MercadoBitcoinOrange)

val LightButtonBackground = MercadoBitcoinOrange
val DarkButtonBackground = MercadoBitcoinOrange
