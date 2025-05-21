package br.com.victorcs.lightning.data.mapper

import br.com.victorcs.core.extensions.orZero
import br.com.victorcs.core.mapper.DomainMapper
import br.com.victorcs.lightning.data.entity.NodeResponse
import br.com.victorcs.lightning.domain.model.Node

class NodeMapper : DomainMapper<NodeResponse, Node> {
    override fun toDomain(from: NodeResponse) = Node(
        publicKey = from.publicKey.orEmpty(),
        alias = from.alias.orEmpty(),
        channels = from.channels.orZero(),
        capacity = from.capacity.orZero(),
        firstSeen = from.firstSeen.orZero(),
        updatedAt = from.updatedAt.orZero(),
        city = from.city?.let {
            CityMapper().toDomain(it)
        },
        country = from.country?.let {
            CountryMapper().toDomain(it)
        },
        isoCode = from.isoCode.orEmpty(),
        subdivision = from.subdivision.orEmpty()
    )
}
