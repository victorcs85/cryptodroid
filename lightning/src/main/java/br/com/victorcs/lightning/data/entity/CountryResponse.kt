package br.com.victorcs.lightning.data.entity

import com.squareup.moshi.Json

data class CountryResponse(
    @Json(name = "de") val de: String?,
    @Json(name = "en") val en: String?,
    @Json(name = "es") val es: String?,
    @Json(name = "fr") val fr: String?,
    @Json(name = "ja") val ja: String?,
    @Json(name = "pt-BR") val ptBR: String?,
    @Json(name = "ru") val ru: String?,
    @Json(name = "zh-CN") val zhCN: String?
)
