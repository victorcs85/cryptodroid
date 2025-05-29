package br.com.victorcs.lightning.shared.test

import br.com.victorcs.core.model.Response
import br.com.victorcs.lightning.data.entity.CountryResponse
import br.com.victorcs.lightning.data.entity.NodeResponse
import br.com.victorcs.lightning.domain.model.City
import br.com.victorcs.lightning.domain.model.Country
import br.com.victorcs.lightning.domain.model.Node

object DataMockTest {
    const val MOCK_DEFAULT_ERROR = "Ocorreu um erro ao buscar os dados!"

    val mockLightning = listOf(
        Node(
            publicKey = "03864ef025fde8fb587d989186ce6a4a186895ee44a926bfc370e2c366597a3f8f",
            alias = "ACINQ",
            channels = 2908,
            capacity = 36010516297.0,
            firstSeen = "1522941222",
            updatedAt = "1661274935",
            city = null,
            country = Country(null, "United States", null, null, null, "EUA", null, null),
            isoCode = null,
            subdivision = null
        ),
        Node(
            publicKey = "035e4ff418fc8b5554c5d9eea66396c227bd429a3251c8cbc711002ba215bfc226",
            alias = "WalletOfSatoshi.com",
            channels = 2772,
            capacity = 15464503162.0,
            firstSeen = "1601429940",
            updatedAt = "1661812116",
            city = City(null, "Vancouver", null, null, null, "Vancôver", null, null),
            country = Country(null, "Canada", null, null, null, "Canadá", null, null),
            isoCode = null,
            subdivision = null
        )
    )

    val mockSuccessLightning: Response<List<Node>> = Response.Success(mockLightning)

    val mockNodeResponse = listOf(
        NodeResponse(
            publicKey = "03864ef025fde8fb587d989186ce6a4a186895ee44a926bfc370e2c366597a3f8f",
            alias = "ACINQ",
            channels = 2351,
            capacity = 57159558378,
            firstSeen = 1522941222,
            updatedAt = 1747762422,
            city = null,
            country = CountryResponse(
                de = "Vereinigte Staaten",
                en = "United States",
                es = "Estados Unidos",
                fr = "États Unis",
                ja = "アメリカ",
                ptBR = "EUA",
                ru = "США",
                zhCN = "美国"
            ),
            isoCode = "US",
            subdivision = null
        ),
        NodeResponse(
            publicKey = "035e4ff418fc8b5554c5d9eea66396c227bd429a3251c8cbc711002ba215bfc226",
            alias = "WalletOfSatoshi.com",
            channels = 1233,
            capacity = 17501632500,
            firstSeen = 1601429940,
            updatedAt = 1747762369,
            city = null,
            country = CountryResponse(
                de = "Kanada",
                en = "Canada",
                es = "Canadá",
                fr = "Canada",
                ja = "カナダ",
                ptBR = "Canadá",
                ru = "Канада",
                zhCN = "加拿大"
            ),
            isoCode = "CA",
            subdivision = null
        )
    )
}