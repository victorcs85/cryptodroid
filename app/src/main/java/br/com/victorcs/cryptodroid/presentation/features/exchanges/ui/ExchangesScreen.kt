package br.com.victorcs.cryptodroid.presentation.features.exchanges.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.navigation.NavController
import br.com.victorcs.core.base.MainViewModel
import br.com.victorcs.core.constants.PULL_TO_REFRESH_TAG
import br.com.victorcs.core.extensions.orFalse
import br.com.victorcs.core.views.EmptyListView
import br.com.victorcs.core.views.LoadingView
import br.com.victorcs.core.views.ShowErrorMessage
import br.com.victorcs.cryptodroid.R
import br.com.victorcs.cryptodroid.presentation.features.exchanges.command.ExchangesCommand
import br.com.victorcs.cryptodroid.presentation.features.exchanges.ui.views.ExchangeList
import org.koin.androidx.compose.koinViewModel
import br.com.victorcs.core.R as coreR

@Composable
fun ExchangesScreen(
    navController: NavController,
    state: ExchangesScreenState,
    execute: (ExchangesCommand) -> Unit,
    mainViewModel: MainViewModel = koinViewModel(),
) {
    mainViewModel.setTitleAppBar(stringResource(coreR.string.exchanges_label))
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        ExchangesScreenContent(state, navController, execute) {
            execute(
                ExchangesCommand.RefreshExchanges,
            )
        }
    }
}

@Composable
private fun ExchangesScreenContent(
    state: ExchangesScreenState,
    navController: NavController,
    execute: (ExchangesCommand) -> Unit,
    onRefresh: () -> Unit,
) {
    val listState = rememberLazyListState()

    PullToRefreshWrapper(
        isRefreshing = state.isRefreshing,
        onRefresh = onRefresh,
        modifier = Modifier.fillMaxSize(),
    ) {
        when {
            state.errorMessage != null -> ShowErrorMessage(
                state.errorMessage,
                buttonLabel = stringResource(coreR.string.reload),
                buttonAction =
                {
                    execute(
                        ExchangesCommand.FetchExchanges,
                    )
                },
                modifier = null,
            )

            state.isLoading -> LoadingView()
            state.exchanges?.isEmpty().orFalse() -> EmptyListView(
                buttonLabel = stringResource(coreR.string.reload),
                buttonAction =
                {
                    execute(
                        ExchangesCommand.RefreshExchanges,
                    )
                },
                modifier = null,
            )

            state.exchanges == null -> Unit

            else -> ExchangeList(state.exchanges, navController, listState)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PullToRefreshWrapper(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    enabled: Boolean = true,
    content: @Composable BoxScope.() -> Unit,
) {
    val refreshState = rememberPullToRefreshState()
    val pullToRefreshContentDescription = stringResource(R.string.semantic_pull_to_refresh)
    Box(
        modifier
            .fillMaxSize()
            .pullToRefresh(
                state = refreshState,
                isRefreshing = isRefreshing,
                onRefresh = onRefresh,
                enabled = enabled,
            )
            .semantics() {
                contentDescription = pullToRefreshContentDescription
            }
            .testTag(PULL_TO_REFRESH_TAG),
        contentAlignment = contentAlignment,
    ) {
        content()
        if (isRefreshing.not()) {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = isRefreshing,
                state = refreshState,
            )
        }
    }
}
