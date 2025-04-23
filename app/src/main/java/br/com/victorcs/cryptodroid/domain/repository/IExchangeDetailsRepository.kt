package br.com.victorcs.cryptodroid.domain.repository

import br.com.victorcs.cryptodroid.core.model.Response
import br.com.victorcs.cryptodroid.domain.model.Exchange

interface IExchangeDetailsRepository {
    suspend fun getExchangeDetails(exchangeId: String): Response<List<Exchange>>
}
