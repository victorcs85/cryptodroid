package br.com.victorcs.cryptodroid.presentation.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import br.com.victorcs.cryptodroid.R
import br.com.victorcs.cryptodroid.core.theme.LocalCustomColors

@Composable
fun LoadingView() {
    val loadingContentDescription = stringResource(R.string.semantic_loading)
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            color = LocalCustomColors.current.pullToRefreshBackground,
            trackColor = LocalCustomColors.current.pullToRefreshArrow,
            modifier = Modifier.semantics {
                contentDescription = loadingContentDescription
            },
        )
    }
}
