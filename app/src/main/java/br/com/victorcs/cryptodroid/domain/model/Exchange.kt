package br.com.victorcs.cryptodroid.domain.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
data class Exchange(
    val exchangeId: String,
    val website: String,
    val name: String,
    val dataQuoteStart: String,
    val dataQuoteEnd: String,
    val dataOrderbookStart: String,
    val dataOrderbookEnd: String,
    val dataTradeStart: String,
    val dataTradeEnd: String,
    val dataSymbolsCount: String,
    val volume1HrsUsd: Double,
    val volume1DayUsd: Double,
    val volume1MthUsd: Double,
    val rank: Int,
    val integrationStatus: String,
    var icons: List<Icon>?,
    val metricId: List<String>,
) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Icon(
    val exchangeId: String,
    val assetId: String?,
    val url: String,
) : Parcelable
