package br.com.victorcs.cryptodroid.data.source.remote.repository

import br.com.victorcs.cryptodroid.core.extensions.safeApiCall
import br.com.victorcs.cryptodroid.data.source.remote.CoinAPI
import br.com.victorcs.cryptodroid.data.source.remote.entity.ExchangeResponse
import br.com.victorcs.cryptodroid.data.source.remote.entity.IconResponse
import br.com.victorcs.cryptodroid.domain.mapper.DomainMapper
import br.com.victorcs.cryptodroid.domain.model.Exchange
import br.com.victorcs.cryptodroid.domain.model.Icon
import br.com.victorcs.cryptodroid.domain.repository.ExchangesIconsResponse
import br.com.victorcs.cryptodroid.domain.repository.ExchangesResponse
import br.com.victorcs.cryptodroid.domain.repository.IExchangesRepository

class ExchangesRepositoryImpl(
    private val service: CoinAPI,
    private val mapper: DomainMapper<ExchangeResponse, Exchange>,
    private val iconMapper: DomainMapper<IconResponse, Icon>
) : IExchangesRepository {

    override suspend fun getExchanges(): ExchangesResponse = safeApiCall {
        val response = service.getExchanges()
        mapper.toDomain(response)
    }

    override suspend fun getIcons(): ExchangesIconsResponse = safeApiCall {
        val response = service.getExchangesIcons()
        iconMapper.toDomain(response)
    }
}
