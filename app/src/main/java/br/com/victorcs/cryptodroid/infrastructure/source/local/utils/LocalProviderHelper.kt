package br.com.victorcs.cryptodroid.infrastructure.source.local.utils

import br.com.victorcs.cryptodroid.data.entity.ExchangeResponse
import br.com.victorcs.cryptodroid.data.entity.IconResponse
import br.com.victorcs.cryptodroid.infrastructure.source.MoshiBuilder
import com.squareup.moshi.Types

fun String.toExchangesResponse(): List<ExchangeResponse> = run {
    val type = Types.newParameterizedType(List::class.java, ExchangeResponse::class.java)
    val adapter = MoshiBuilder.create().adapter<List<ExchangeResponse>>(type)
    adapter.fromJson(this) as List<ExchangeResponse>
}

fun String.toIconResponse() = run {
    val type = Types.newParameterizedType(List::class.java, IconResponse::class.java)
    val adapter = MoshiBuilder.create().adapter<List<IconResponse>>(type)
    adapter.fromJson(this) as List<IconResponse>
}