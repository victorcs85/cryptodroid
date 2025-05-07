package br.com.victorcs.cryptodroid.shared.test

import br.com.victorcs.core.model.Response
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
            metricId = emptyList(),
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
            metricId = emptyList(),
        ),
    )

    val mockSuccessExchangeResponse: Response<List<Exchange>> = Response.Success(mockExchangeList)

    val mockExchangeDetails = listOf(
        mockExchangeList.first(),
    )

    val mockSuccessExchangeDetailsResponse: Response<List<Exchange>> =
        Response.Success(mockExchangeDetails)

    val mockExchangeIcons = listOf(
        Icon(
            exchangeId = "LAKEBTC",
            url = "https://s3.eu-central-1.amazonaws.com/bbxt-static-icons/type-id/png_32/5503eb9673f9437988702f06cbd7072b.png",
            assetId = null,
        ),
        Icon(
            exchangeId = "APHELION",
            url = "https://s3.eu-central-1.amazonaws.com/bbxt-static-icons/type-id/png_32/566775b2321842faac5c156dfe81705a.png",
            assetId = null,
        ),
    )

    val mockSuccessExchangeIconsResponse: Response<List<Icon>> = Response.Success(
        mockExchangeIcons,
    )

    const val MOCK_DEFAULT_ERROR = "Ocorreu um erro ao buscar os dados!"

    const val MOCK_JSON_EXCHANGES = "[\n" +
        "  {\n" +
        "    \"exchange_id\": \"BINANCE\",\n" +
        "    \"website\": \"https://www.binance.com/\",\n" +
        "    \"name\": \"Binance\",\n" +
        "    \"data_quote_start\": \"2017-12-18T00:00:00.0000000Z\",\n" +
        "    \"data_quote_end\": \"2025-04-15T00:00:00.0000000Z\",\n" +
        "    \"data_orderbook_start\": \"2017-12-18T00:00:00.0000000Z\",\n" +
        "    \"data_orderbook_end\": \"2025-04-15T00:00:00.0000000Z\",\n" +
        "    \"data_trade_start\": \"2017-07-14T00:00:00.0000000Z\",\n" +
        "    \"data_trade_end\": \"2025-04-15T00:00:00.0000000Z\",\n" +
        "    \"data_symbols_count\": 2991,\n" +
        "    \"volume_1hrs_usd\": 0,\n" +
        "    \"volume_1day_usd\": 0,\n" +
        "    \"volume_1mth_usd\": 0,\n" +
        "    \"rank\": 2,\n" +
        "    \"integration_status\": \"INTEGRATED\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"exchange_id\": \"KRAKEN\",\n" +
        "    \"website\": \"https://www.kraken.com/\",\n" +
        "    \"name\": \"Kraken\",\n" +
        "    \"data_quote_start\": \"2014-07-31T00:00:00.0000000Z\",\n" +
        "    \"data_quote_end\": \"2025-04-14T00:00:00.0000000Z\",\n" +
        "    \"data_orderbook_start\": \"2014-07-31T13:05:46.0000000Z\",\n" +
        "    \"data_orderbook_end\": \"2025-04-15T00:00:00.0000000Z\",\n" +
        "    \"data_trade_start\": \"2013-10-22T00:00:00.0000000Z\",\n" +
        "    \"data_trade_end\": \"2025-02-24T00:00:00.0000000Z\",\n" +
        "    \"data_symbols_count\": 1258,\n" +
        "    \"volume_1hrs_usd\": 0,\n" +
        "    \"volume_1day_usd\": 0,\n" +
        "    \"volume_1mth_usd\": 0,\n" +
        "    \"rank\": 2,\n" +
        "    \"integration_status\": \"INTEGRATED\"\n" +
        "  }" +
        "]"

    const val MOCK_EMPTY_JSON_ARRAY = "[]"

    const val MOCK_JSON_ICONS = "[\n" +
        "  {\n" +
        "    \"exchange_id\": \"LAKEBTC\",\n" +
        "    \"url\": \"https://s3.eu-central-1.amazonaws.com/bbxt-static-icons/type-id/png_32/5503eb9673f9437988702f06cbd7072b.png\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"exchange_id\": \"APHELION\",\n" +
        "    \"url\": \"https://s3.eu-central-1.amazonaws.com/bbxt-static-icons/type-id/png_32/566775b2321842faac5c156dfe81705a.png\"\n" +
        "  }" +
        "]"
}
