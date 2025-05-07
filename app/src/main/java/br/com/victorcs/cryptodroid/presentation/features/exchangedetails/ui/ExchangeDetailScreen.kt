package br.com.victorcs.cryptodroid.presentation.features.exchangedetails.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.victorcs.core.constants.EXCHANGE_ID
import br.com.victorcs.core.theme.LocalCustomColors
import br.com.victorcs.cryptodroid.R
import br.com.victorcs.cryptodroid.domain.model.Exchange
import br.com.victorcs.cryptodroid.presentation.features.exchangedetails.command.ExchangeDetailsCommand
import br.com.victorcs.cryptodroid.presentation.views.ExchangeTopAppBar
import br.com.victorcs.cryptodroid.presentation.views.LoadingView
import br.com.victorcs.cryptodroid.presentation.views.ShowErrorMessage

@Composable
fun ExchangeDetailScreen(
    navController: NavController,
    state: ExchangeDetailsScreenState,
    execute: (ExchangeDetailsCommand) -> Unit,
) {
    val exchange = state.exchange
    val exchangeId = rememberSaveable() {
        navController.previousBackStackEntry?.arguments?.getString(EXCHANGE_ID).orEmpty()
    }

    Scaffold(
        topBar = {
            ExchangeTopAppBar(
                title = exchange?.name ?: stringResource(R.string.exchange_details_title),
                onBackPressed = { navController.popBackStack() },
            )
        },
    ) { contentPadding ->
        when {
            state.isLoading -> LoadingView()
            state.errorMessage != null -> ShowErrorMessage(
                state.errorMessage,
                buttonLabel = stringResource(R.string.reload),
                buttonAction = {
                    execute(
                        ExchangeDetailsCommand.GetExchangeDetails(
                            exchangeId,
                        ),
                    )
                },
                modifier = null,
            )

            exchange != null -> DetailsContent(contentPadding, exchange)
        }
    }
}

@Composable
private fun DetailsContent(contentPadding: PaddingValues, exchange: Exchange) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState()),
        ) {
            val exchangeDetailsLabel = stringResource(R.string.exchange_details)
            Text(
                text = exchangeDetailsLabel,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = LocalCustomColors.current.exchangeDetailsTitle,
                modifier = Modifier.padding(bottom = 16.dp).semantics {
                    heading()
                    contentDescription = exchangeDetailsLabel
                },
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.7f))
                    .padding(16.dp),
            ) {
                DynamicDetails(exchange)
            }
        }
    }
}

@Composable private fun DynamicDetails(exchange: Exchange) {
    Column {
        listOf(
            R.string.website to exchange.website,
            R.string.data_quote_start to exchange.dataQuoteStart,
            R.string.data_quote_end to exchange.dataQuoteEnd,
            R.string.volume_one_hour to "${exchange.volume1HrsUsd} USD",
            R.string.volume_one_day to "${exchange.volume1DayUsd} USD",
            R.string.volume_one_month to "${exchange.volume1MthUsd} USD",
            R.string.rank to exchange.rank.toString(),
            R.string.integration_status to exchange.integrationStatus,
        ).forEach { (resId, value) ->
            if (value.isNotEmpty()) {
                val detailLabel = stringResource(resId)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .semantics {
                            contentDescription = "$detailLabel: $value"
                        },
                ) {
                    Column {
                        Text(
                            text = detailLabel,
                            fontWeight = FontWeight.Bold,
                            color = LocalCustomColors.current.exchangeInfo,
                        )
                        Text(
                            text = value,
                            style = MaterialTheme.typography.bodyMedium,
                            color = LocalCustomColors.current.exchangeVolume,
                        )
                    }
                }
            }
        }
    }
}
