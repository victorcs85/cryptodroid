package br.com.victorcs.cryptodroid.infrastructure.source.local.repository

import br.com.victorcs.cryptodroid.core.model.Response
import br.com.victorcs.cryptodroid.data.entity.ExchangeResponse
import br.com.victorcs.cryptodroid.domain.mapper.DomainMapper
import br.com.victorcs.cryptodroid.domain.model.Exchange
import br.com.victorcs.cryptodroid.domain.repository.IExchangeDetailsRepository
import br.com.victorcs.cryptodroid.infrastructure.source.local.utils.toExchangesResponse

class ExchangeDetailsRepositoryImpl(
    private val provider: IExchangeLocalProvider,
    private val mapper: DomainMapper<ExchangeResponse, Exchange>
) : IExchangeDetailsRepository {

    override suspend fun getExchangeDetails(exchangeId: String): Response<List<Exchange>> {
        val response: List<ExchangeResponse> =
            provider.loadJSONFile(ExchangeLocalProviderType.EXCHANGES).toExchangesResponse()
        return Response.Success(mapper.toDomain(response))
    }
}