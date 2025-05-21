package br.com.victorcs.lightning.data.entity

import com.squareup.moshi.Json

data class NodeResponse(
    @Json(name = "publicKey") val publicKey: String?,
    @Json(name = "alias") val alias: String?,
    @Json(name = "channels") val channels: Int?,
    @Json(name = "capacity") val capacity: Long?,
    @Json(name = "firstSeen") val firstSeen: Long?,
    @Json(name = "updatedAt") val updatedAt: Long?,
    @Json(name = "city") val city: CityResponse?,
    @Json(name = "country") val country: CountryResponse?,
    @Json(name = "iso_code") val isoCode: String?,
    @Json(name = "subdivision") val subdivision: String?
)
