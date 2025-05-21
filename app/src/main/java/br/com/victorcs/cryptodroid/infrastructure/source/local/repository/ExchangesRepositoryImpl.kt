package br.com.victorcs.cryptodroid.infrastructure.source.local.repository

import br.com.victorcs.core.mapper.DomainMapper
import br.com.victorcs.core.model.Response
import br.com.victorcs.cryptodroid.data.entity.ExchangeResponse
import br.com.victorcs.cryptodroid.data.entity.IconResponse
import br.com.victorcs.cryptodroid.domain.model.Exchange
import br.com.victorcs.cryptodroid.domain.model.Icon
import br.com.victorcs.cryptodroid.domain.repository.ExchangesIconsResponse
import br.com.victorcs.cryptodroid.domain.repository.ExchangesResponse
import br.com.victorcs.cryptodroid.domain.repository.IExchangesRepository
import br.com.victorcs.cryptodroid.infrastructure.source.local.utils.toExchangesResponse
import br.com.victorcs.cryptodroid.infrastructure.source.local.utils.toIconResponse
import timber.log.Timber

class ExchangesRepositoryImpl(
    private val provider: IExchangeLocalProvider,
    private val mapper: DomainMapper<ExchangeResponse, Exchange>,
    private val iconMapper: DomainMapper<IconResponse, Icon>,
) : IExchangesRepository {

    override suspend fun getExchanges(): ExchangesResponse {
        Timber.d(JSON_LOCAL_DATA_LOADED)
        val response: List<ExchangeResponse> =
            provider.loadJSONFile(ExchangeLocalProviderType.EXCHANGES).toExchangesResponse()
        return Response.Success(mapper.toDomain(response))
    }

    override suspend fun getIcons(): ExchangesIconsResponse {
        Timber.d(JSON_LOCAL_DATA_LOADED)
        val response: List<IconResponse> =
            provider.loadJSONFile(ExchangeLocalProviderType.ICONS).toIconResponse()
        return Response.Success(iconMapper.toDomain(response))
    }
}
