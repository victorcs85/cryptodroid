package br.com.victorcs.core.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import br.com.victorcs.core.R
import br.com.victorcs.core.theme.LocalCustomColors

@Composable
fun ActionButton(
    modifier: Modifier?,
    buttonAction: () -> Unit,
    buttonLabel: String,
) {
    val contentButtonDescription = stringResource(R.string.semantic_button, buttonLabel)

    ElevatedButton(
        onClick = buttonAction,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 2.dp,
            pressedElevation = 2.dp,
            focusedElevation = 2.dp,
            hoveredElevation = 2.dp,
            disabledElevation = 0.dp,
        ),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = LocalCustomColors.current.buttonBackground,
        ),
        modifier = modifier?.semantics {
            contentDescription = contentButtonDescription
            role = Role.Button
        } ?: Modifier
            .fillMaxWidth()
            .semantics {
                contentDescription = contentButtonDescription
                role = Role.Button
            },
    ) {
        Text(
            text = buttonLabel,
            style = MaterialTheme.typography.titleMedium,
            color = LocalCustomColors.current.appBarInfo,
        )
    }
}
