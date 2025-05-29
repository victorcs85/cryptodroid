package br.com.victorcs.cryptodroid.presentation.views

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.victorcs.core.constants.ZERO
import br.com.victorcs.core.theme.LocalCustomColors
import br.com.victorcs.cryptodroid.R
import br.com.victorcs.cryptodroid.presentation.navigation.ScreenRouter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val SCALE_UP = 1.1f
private const val SCALE_NORMAL = 1f
private const val SCALE_DURATION = 100
private const val SHAKE_DELAY_MS = 40
private const val INITIAL_ANIMATION_VALUE = 0f

private const val NEGATIVE_FLOAT_FOUR = -4f
private const val FLOAT_FOUR = 4f
private const val FLOAT_TWO = 2f
private const val NEGATIVE_FLOAT_TWO = -2f

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
) {
    val selectedNavigationIndex = rememberSaveable {
        mutableIntStateOf(ZERO)
    }

    val bottomNavigationItems = listOf(
        NavigationItem(
            route = ScreenRouter.Exchanges.route,
            icon = painterResource(R.drawable.ic_bitcoin),
            iconContentDescription = stringResource(R.string.exchanges_content_description),
            label = stringResource(R.string.exchanges_item),
        ),
        NavigationItem(
            route = ScreenRouter.Lightning.route,
            icon = painterResource(R.drawable.ic_lightning),
            iconContentDescription = stringResource(R.string.lightning_content_description),
            label = stringResource(R.string.lightning_label),
        ),
    )

    NavigationBar(containerColor = LocalCustomColors.current.appBarBackground) {
        bottomNavigationItems.forEachIndexed { index, item ->
            val isSelected = selectedNavigationIndex.intValue == index

            val scale = remember { Animatable(SCALE_NORMAL) }
            val shakeOffset = remember { Animatable(INITIAL_ANIMATION_VALUE) }

            setUpAnimation(isSelected, scale, shakeOffset)

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    selectedNavigationIndex.intValue = index
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(
                        painter = item.icon,
                        contentDescription = item.iconContentDescription,
                        modifier = Modifier
                            .padding(0.dp)
                            .graphicsLayer {
                                scaleX = scale.value
                                scaleY = scale.value
                                translationX = shakeOffset.value
                            },
                        tint = if (isSelected) {
                            LocalCustomColors.current.selection
                        } else {
                            LocalCustomColors.current.appBarInfo
                        },
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        modifier = Modifier.graphicsLayer {
                            scaleX = scale.value
                            scaleY = scale.value
                            translationX = shakeOffset.value
                        },
                        color = if (isSelected) {
                            LocalCustomColors.current.selection
                        } else {
                            Color.White
                        },
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.surface,
                    indicatorColor = MaterialTheme.colorScheme.primary,
                ),
            )
        }
    }
}

@Composable
private fun setUpAnimation(
    isSelected: Boolean,
    scale: Animatable<Float, AnimationVector1D>,
    shakeOffset: Animatable<Float, AnimationVector1D>
) {
    val shakeSequence = listOf(
        INITIAL_ANIMATION_VALUE,
        NEGATIVE_FLOAT_FOUR,
        FLOAT_FOUR,
        NEGATIVE_FLOAT_TWO,
        FLOAT_TWO,
        INITIAL_ANIMATION_VALUE
    )

    if (isSelected) {
        LaunchedEffect(key1 = isSelected) {
            scale.animateTo(
                targetValue = SCALE_UP,
                animationSpec = tween(
                    durationMillis = SCALE_DURATION,
                    easing = LinearOutSlowInEasing
                )
            )
            launch {
                for (offset in shakeSequence) {
                    shakeOffset.snapTo(offset)
                    delay(SHAKE_DELAY_MS.toLong())
                }
            }
            scale.animateTo(
                targetValue = SCALE_NORMAL,
                animationSpec = tween(
                    durationMillis = SCALE_DURATION,
                    easing = FastOutLinearInEasing
                )
            )
        }
    }
}

data class NavigationItem(
    val route: String,
    val icon: Painter,
    val iconContentDescription: String,
    val label: String,
)
