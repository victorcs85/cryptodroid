package br.com.victorcs.cryptodroid.data.mapper

import br.com.victorcs.cryptodroid.data.entity.IconResponse
import br.com.victorcs.cryptodroid.domain.mapper.DomainMapper
import br.com.victorcs.cryptodroid.domain.model.Icon

class ExchangeIconMapper : DomainMapper<IconResponse, Icon> {

    override fun toDomain(from: IconResponse): Icon = with(from) {
        Icon(
            exchangeId = exchangeId.orEmpty(),
            assetId = assetId.orEmpty(),
            url = url.orEmpty(),
        )
    }
}
