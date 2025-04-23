package br.com.victorcs.cryptodroid.domain.repository

import br.com.victorcs.cryptodroid.core.model.Response
import br.com.victorcs.cryptodroid.domain.model.Exchange
import br.com.victorcs.cryptodroid.domain.model.Icon

typealias ExchangesResponse = Response<List<Exchange>>
typealias ExchangesIconsResponse = Response<List<Icon>>

interface IExchangesRepository {
    suspend fun getExchanges(): ExchangesResponse
    suspend fun getIcons(): ExchangesIconsResponse
}
