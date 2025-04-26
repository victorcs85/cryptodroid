package br.com.victorcs.cryptodroid.presentation.features.exchanges.ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.victorcs.cryptodroid.R
import br.com.victorcs.cryptodroid.core.theme.LocalCustomColors
import br.com.victorcs.cryptodroid.presentation.views.ActionButton

@Composable
fun EmptyListView(buttonAction: () -> Unit, buttonLabel: String?, modifier: Modifier?) {
    Box(modifier = Modifier.fillMaxSize().padding(8.dp), contentAlignment = Alignment.Center) {
        Column {
            Text(
                text = stringResource(R.string.no_data_available),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                color = LocalCustomColors.current.exchangeInfo,
                textAlign = TextAlign.Center,
            )
            buttonLabel?.let {
                val contentButtonDescription = stringResource(R.string.semantic_button, buttonLabel)
                ActionButton(modifier?.semantics { contentDescription =  contentButtonDescription }, buttonAction, it)
            }
        }
    }
}

@Preview
@Composable
fun ShowEmptyListPreview() {
    EmptyListView(modifier = Modifier.fillMaxWidth(), buttonAction = {}, buttonLabel = "Retry")
}
