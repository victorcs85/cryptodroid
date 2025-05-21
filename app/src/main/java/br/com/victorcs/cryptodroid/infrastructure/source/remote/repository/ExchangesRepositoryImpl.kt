package br.com.victorcs.cryptodroid.infrastructure.source.remote.repository

import br.com.victorcs.core.extensions.safeApiCall
import br.com.victorcs.core.mapper.DomainMapper
import br.com.victorcs.cryptodroid.data.entity.ExchangeResponse
import br.com.victorcs.cryptodroid.data.entity.IconResponse
import br.com.victorcs.cryptodroid.domain.model.Exchange
import br.com.victorcs.cryptodroid.domain.model.Icon
import br.com.victorcs.cryptodroid.domain.repository.ExchangesIconsResponse
import br.com.victorcs.cryptodroid.domain.repository.ExchangesResponse
import br.com.victorcs.cryptodroid.domain.repository.IExchangesRepository
import br.com.victorcs.cryptodroid.infrastructure.source.remote.CoinAPI
import timber.log.Timber

internal const val REMOTE_DATA_SOURCE = ">>>>>>>>>>>>>>>>>>>>>>>>>>>> Remote data source"

class ExchangesRepositoryImpl(
    private val service: CoinAPI,
    private val mapper: DomainMapper<ExchangeResponse, Exchange>,
    private val iconMapper: DomainMapper<IconResponse, Icon>,
) : IExchangesRepository {

    override suspend fun getExchanges(): ExchangesResponse = safeApiCall {
        Timber.d(REMOTE_DATA_SOURCE)
        val response = service.getExchanges()
        mapper.toDomain(response)
    }

    override suspend fun getIcons(): ExchangesIconsResponse = safeApiCall {
        Timber.d(REMOTE_DATA_SOURCE)
        val response = service.getExchangesIcons()
        iconMapper.toDomain(response)
    }
}
