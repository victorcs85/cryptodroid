package br.com.victorcs.cryptodroid.shared.test

import br.com.victorcs.cryptodroid.core.model.Response
import br.com.victorcs.cryptodroid.domain.model.Exchange
import br.com.victorcs.cryptodroid.domain.model.Icon

object DataMockTest {
    val mockExchangeList = listOf(
        Exchange(
            exchangeId = "BINANCE",
            website = "https://www.binance.com/",
            name = "Binance",
            dataQuoteStart = "2017-12-18",
            dataQuoteEnd = "2025-03-21",
            dataOrderbookStart = "2017-12-18",
            dataOrderbookEnd = "2025-03-21",
            dataTradeStart = "2017-07-14",
            dataTradeEnd = "2025-02-24",
            dataSymbolsCount = "2991",
            volume1HrsUsd = 0.0,
            volume1DayUsd = 0.0,
            volume1MthUsd = 0.0,
            rank = 2,
            integrationStatus = "INTEGRATED",
            icons = null,
            metricId = emptyList()
        ),
        Exchange(
            exchangeId = "KRAKEN",
            website = "https://www.kraken.com/",
            name = "Kraken",
            dataQuoteStart = "2014-07-31",
            dataQuoteEnd = "2025-03-18",
            dataOrderbookStart = "2014-07-31",
            dataOrderbookEnd = "2025-03-21",
            dataTradeStart = "2013-10-22",
            dataTradeEnd = "2025-02-24",
            dataSymbolsCount = "1208",
            volume1HrsUsd = 0.0,
            volume1DayUsd = 0.0,
            volume1MthUsd = 0.0,
            rank = 2,
            integrationStatus = "INTEGRATED",
            icons = null,
            metricId = emptyList()
        )
    )

    val mockSuccessExchangeResponse: Response<List<Exchange>> = Response.Success(mockExchangeList)

    val mockExchangeDetails = listOf(
        mockExchangeList.first()
    )

    val mockSuccessExchangeDetailsResponse: Response<List<Exchange>> = Response.Success(mockExchangeDetails)

    private val mockExchangeIcons = listOf(
        Icon(
            exchangeId = "LAKEBTC",
            url = "https://s3.eu-central-1.amazonaws.com/bbxt-static-icons/type-id/png_32/5503eb9673f9437988702f06cbd7072b.png",
            assetId = null
        ),
        Icon(
            exchangeId = "APHELION",
            url = "https://s3.eu-central-1.amazonaws.com/bbxt-static-icons/type-id/png_32/566775b2321842faac5c156dfe81705a.png",
            assetId = null
        )
    )

    val mockSuccessExchangeIconsResponse: Response<List<Icon>> = Response.Success(
        mockExchangeIcons
    )

    const val DEFAULT_ERROR_MOCK = "Ocorreu um erro ao buscar os dados!"
}