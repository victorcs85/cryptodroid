package br.com.victorcs.lightning.domain.repository

import br.com.victorcs.core.model.Response
import br.com.victorcs.lightning.domain.model.Node

typealias NodesResponse = Response<List<Node>>

interface IRankingsConnectivityRepository {
    suspend fun getRankingsConnectivity(): Response<List<Node>>
}
