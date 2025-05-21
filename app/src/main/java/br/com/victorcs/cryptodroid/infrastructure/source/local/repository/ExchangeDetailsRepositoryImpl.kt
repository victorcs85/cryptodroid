package br.com.victorcs.cryptodroid.infrastructure.source.local.repository

import br.com.victorcs.core.mapper.DomainMapper
import br.com.victorcs.core.model.Response
import br.com.victorcs.cryptodroid.data.entity.ExchangeResponse
import br.com.victorcs.cryptodroid.domain.model.Exchange
import br.com.victorcs.cryptodroid.domain.repository.IExchangeDetailsRepository
import br.com.victorcs.cryptodroid.infrastructure.source.local.utils.toExchangesResponse
import timber.log.Timber

internal const val JSON_LOCAL_DATA_LOADED = ">>>>>>>>>>>>>>>>>>>>>>>>>>>> Json Local Data Loaded"

class ExchangeDetailsRepositoryImpl(
    private val provider: IExchangeLocalProvider,
    private val mapper: DomainMapper<ExchangeResponse, Exchange>,
) : IExchangeDetailsRepository {

    override suspend fun getExchangeDetails(exchangeId: String): Response<List<Exchange>> {
        Timber.d(JSON_LOCAL_DATA_LOADED)
        val response: List<ExchangeResponse> =
            provider.loadJSONFile(ExchangeLocalProviderType.EXCHANGES).toExchangesResponse()
        return Response.Success(mapper.toDomain(response))
    }
}
