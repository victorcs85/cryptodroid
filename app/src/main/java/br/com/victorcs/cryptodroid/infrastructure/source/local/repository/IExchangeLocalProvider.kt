package br.com.victorcs.cryptodroid.infrastructure.source.local.repository

interface IExchangeLocalProvider {
    fun loadJSONFile(file: ExchangeLocalProviderType): String
}
