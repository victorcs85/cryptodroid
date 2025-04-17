package br.com.victorcs.cryptodroid.data.source.remote.repository

import br.com.victorcs.cryptodroid.core.extensions.safeApiCall
import br.com.victorcs.cryptodroid.core.model.Response
import br.com.victorcs.cryptodroid.data.source.remote.CoinAPI
import br.com.victorcs.cryptodroid.data.source.remote.entity.ExchangeResponse
import br.com.victorcs.cryptodroid.domain.mapper.DomainMapper
import br.com.victorcs.cryptodroid.domain.model.Exchange
import br.com.victorcs.cryptodroid.domain.repository.IExchangeDetailsRepository

class ExchangeDetailsRepositoryImpl(
    private val service: CoinAPI,
    private val mapper: DomainMapper<ExchangeResponse, Exchange>
): IExchangeDetailsRepository {
    override suspend fun getExchangeDetails(exchangeId: String): Response<List<Exchange>> = safeApiCall {
        val response = service.getExchange(exchangeId)
        mapper.toDomain(response)
    }
}