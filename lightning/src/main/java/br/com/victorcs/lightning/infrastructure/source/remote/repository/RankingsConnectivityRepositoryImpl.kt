package br.com.victorcs.lightning.infrastructure.source.remote.repository

import br.com.victorcs.core.extensions.safeApiCall
import br.com.victorcs.core.mapper.DomainMapper
import br.com.victorcs.core.model.Response
import br.com.victorcs.lightning.data.entity.NodeResponse
import br.com.victorcs.lightning.domain.model.Node
import br.com.victorcs.lightning.domain.repository.IRankingsConnectivityRepository
import br.com.victorcs.lightning.infrastructure.source.remote.LightningAPI

class RankingsConnectivityRepositoryImpl(
    private val lightningAPI: LightningAPI,
    private val mapper: DomainMapper<NodeResponse, Node>
) : IRankingsConnectivityRepository {
    override suspend fun getRankingsConnectivity(): Response<List<Node>> =
        safeApiCall {
            val response = lightningAPI.getRankingsConnectivity()
            mapper.toDomain(response)
        }
}
