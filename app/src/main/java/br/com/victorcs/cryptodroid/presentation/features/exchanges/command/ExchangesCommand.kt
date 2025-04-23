package br.com.victorcs.cryptodroid.presentation.features.exchanges.command

sealed class ExchangesCommand {
    object FetchExchanges : ExchangesCommand()
    object RefreshExchanges : ExchangesCommand()
}
