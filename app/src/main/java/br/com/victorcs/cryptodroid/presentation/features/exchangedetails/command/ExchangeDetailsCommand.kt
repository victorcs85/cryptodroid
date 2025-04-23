package br.com.victorcs.cryptodroid.presentation.features.exchangedetails.command

sealed class ExchangeDetailsCommand {
    class GetExchangeDetails(val exchangeId: String) : ExchangeDetailsCommand()
}
