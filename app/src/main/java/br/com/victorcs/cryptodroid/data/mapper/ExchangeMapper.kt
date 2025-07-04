package br.com.victorcs.cryptodroid.data.mapper

import br.com.victorcs.core.constants.ZERO
import br.com.victorcs.core.constants.ZERO_POINT_ZERO
import br.com.victorcs.core.extensions.toFormatedDate
import br.com.victorcs.core.mapper.DomainMapper
import br.com.victorcs.cryptodroid.data.entity.ExchangeResponse
import br.com.victorcs.cryptodroid.domain.model.Exchange
import br.com.victorcs.cryptodroid.domain.model.Icon

class ExchangeMapper : DomainMapper<ExchangeResponse, Exchange> {

    override fun toDomain(from: ExchangeResponse): Exchange = with(from) {
        Exchange(
            exchangeId = exchangeId.orEmpty(),
            website = website.orEmpty(),
            name = name.orEmpty(),
            dataQuoteStart = dataQuoteStart?.toFormatedDate().orEmpty(),
            dataQuoteEnd = dataQuoteEnd?.toFormatedDate().orEmpty(),
            dataOrderbookStart = dataOrderbookStart?.toFormatedDate().orEmpty(),
            dataOrderbookEnd = dataOrderbookEnd?.toFormatedDate().orEmpty(),
            dataTradeStart = dataTradeStart?.toFormatedDate().orEmpty(),
            dataTradeEnd = dataTradeEnd?.toFormatedDate().orEmpty(),
            dataSymbolsCount = dataSymbolsCount?.toString().orEmpty(),
            volume1HrsUsd = volume1HrsUsd ?: ZERO_POINT_ZERO,
            volume1DayUsd = volume1DayUsd ?: ZERO_POINT_ZERO,
            volume1MthUsd = volume1MthUsd ?: ZERO_POINT_ZERO,
            rank = rank ?: ZERO,
            integrationStatus = integrationStatus.orEmpty(),
            icons = icons?.map {
                Icon(
                    exchangeId = it.exchangeId.orEmpty(),
                    assetId = it.assetId.orEmpty(),
                    url = it.url.orEmpty(),
                )
            },
            metricId = metricId.orEmpty(),
        )
    }
}
