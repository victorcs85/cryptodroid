package br.com.victorcs.cryptodroid.presentation.views

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import br.com.victorcs.core.constants.ONE
import br.com.victorcs.core.theme.LocalCustomColors
import br.com.victorcs.cryptodroid.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExchangeTopAppBar(title: String, onBackPressed: (() -> Unit)? = null) {

    TopAppBar(
        title = {
            Text(
                text = title,
                maxLines = ONE,
                overflow = TextOverflow.Ellipsis,
                color = LocalCustomColors.current.appBarInfo,
                modifier = Modifier.semantics {
                    text = AnnotatedString(title)
                },
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = LocalCustomColors.current.appBarBackground,
        ),
        navigationIcon = {
            onBackPressed?.let {
                IconButton(
                    onClick = it,
                    modifier = Modifier.semantics {
                        role = Role.Button
                    },
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button),
                        tint = Color.White,
                    )
                }
            }
        },
    )
}

@Preview
@Composable
fun ExchangeTopAppBarPreview() {
    ExchangeTopAppBar(title = "Exchange Details")
}
