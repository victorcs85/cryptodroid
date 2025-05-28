package br.com.victorcs.lightning.infrastructure.source.remote

import br.com.victorcs.lightning.data.entity.NodeResponse
import retrofit2.http.GET

interface LightningAPI {
    @GET("v1/lightning/nodes/rankings/connectivity")
    suspend fun getRankingsConnectivity(): List<NodeResponse>
}
