package br.com.victorcs.lightning.shared.test

import br.com.victorcs.core.model.Response
import br.com.victorcs.lightning.domain.model.City
import br.com.victorcs.lightning.domain.model.Country
import br.com.victorcs.lightning.domain.model.Node

object PresentationMockTest {

    val mockNodes = listOf(
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
            subdivision = null,
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
            subdivision = null,
        ),
    )

    val mockSuccessLightningResponse = Response.Success(
        mockNodes,
    )

    val lightningPublicKey = mockNodes.first().publicKey
}
