package br.com.victorcs.cryptodroid.presentation.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.victorcs.core.constants.ZERO
import br.com.victorcs.core.theme.LocalCustomColors
import br.com.victorcs.cryptodroid.R
import br.com.victorcs.cryptodroid.presentation.navigation.ScreenRouter

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
) {
    val selectedNavigationIndex = rememberSaveable {
        mutableIntStateOf(ZERO)
    }

    val bottomNavigationItems = setOf<NavigationItem>(
        NavigationItem(
            route = ScreenRouter.Exchanges.route,
            icon = painterResource(R.drawable.ic_bitcoin),
            iconContentDescription = stringResource(R.string.exchanges_content_description),
            label = stringResource(R.string.exchanges_label),
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
            NavigationBarItem(
                selected = selectedNavigationIndex.intValue == index,
                onClick = {
                    selectedNavigationIndex.intValue = index
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(
                        painter = item.icon,
                        contentDescription = item.iconContentDescription,
                        modifier = Modifier.padding(ZERO.dp),
                        tint = if (selectedNavigationIndex.intValue == index) {
                            LocalCustomColors.current.selection
                        } else {
                            LocalCustomColors.current.appBarInfo
                        },
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        color = if (selectedNavigationIndex.intValue == index) {
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

data class NavigationItem(
    val route: String,
    val icon: Painter,
    val iconContentDescription: String,
    val label: String,
)
