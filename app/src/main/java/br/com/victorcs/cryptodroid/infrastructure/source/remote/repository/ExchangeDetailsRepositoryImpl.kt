package br.com.victorcs.cryptodroid.infrastructure.source.remote.repository

import br.com.victorcs.core.extensions.safeApiCall
import br.com.victorcs.core.model.Response
import br.com.victorcs.cryptodroid.data.entity.ExchangeResponse
import br.com.victorcs.cryptodroid.domain.mapper.DomainMapper
import br.com.victorcs.cryptodroid.domain.model.Exchange
import br.com.victorcs.cryptodroid.domain.repository.IExchangeDetailsRepository
import br.com.victorcs.cryptodroid.infrastructure.source.remote.CoinAPI
import timber.log.Timber

class ExchangeDetailsRepositoryImpl(
    private val service: CoinAPI,
    private val mapper: DomainMapper<ExchangeResponse, Exchange>,
) : IExchangeDetailsRepository {
    override suspend fun getExchangeDetails(exchangeId: String): Response<List<Exchange>> =
        safeApiCall {
            Timber.d(REMOTE_DATA_SOURCE)
            val response = service.getExchange(exchangeId)
            mapper.toDomain(response)
        }
}
