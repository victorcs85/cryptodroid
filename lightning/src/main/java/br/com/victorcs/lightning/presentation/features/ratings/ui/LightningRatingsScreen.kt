package br.com.victorcs.lightning.presentation.features.ratings.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import br.com.victorcs.core.R
import br.com.victorcs.core.base.MainViewModel
import br.com.victorcs.core.extensions.orFalse
import br.com.victorcs.core.views.EmptyListView
import br.com.victorcs.core.views.LoadingView
import br.com.victorcs.core.views.ShowErrorMessage
import br.com.victorcs.lightning.presentation.features.ratings.command.LightningRatingsCommand
import br.com.victorcs.lightning.presentation.features.ratings.ui.view.LightningRatingsList
import org.koin.androidx.compose.koinViewModel
import br.com.victorcs.core.R as coreR

@Composable
fun LightningsScreen(
    state: LightningRatingsScreenState,
    execute: (LightningRatingsCommand) -> Unit,
    mainViewModel: MainViewModel = koinViewModel(),
) {
    mainViewModel.setTitleAppBar(stringResource(coreR.string.exchanges_label))
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        LightningRatingsScreenContent(state, execute)
    }
}

@Composable
private fun LightningRatingsScreenContent(
    state: LightningRatingsScreenState,
    execute: (LightningRatingsCommand) -> Unit
) {
    val listState = rememberLazyListState()

    when {
        state.errorMessage != null -> ShowErrorMessage(
            state.errorMessage,
            buttonLabel = stringResource(R.string.reload),
            buttonAction =
                {
                    execute(
                        LightningRatingsCommand.GetRatings,
                    )
                },
            modifier = null,
        )

        state.isLoading -> LoadingView()
        state.ratings.isEmpty().orFalse() -> EmptyListView(
            buttonLabel = stringResource(R.string.reload),
            buttonAction =
                {
                    execute(
                        LightningRatingsCommand.GetRatings,
                    )
                },
            modifier = null,
        )

        else -> LightningRatingsList(state.ratings, listState)
    }
}
