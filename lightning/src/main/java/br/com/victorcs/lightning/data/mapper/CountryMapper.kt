package br.com.victorcs.lightning.data.mapper

import br.com.victorcs.core.mapper.DomainMapper
import br.com.victorcs.lightning.data.entity.CountryResponse
import br.com.victorcs.lightning.domain.model.Country

class CountryMapper : DomainMapper<CountryResponse, Country> {

    override fun toDomain(from: CountryResponse) = Country(
        de = from.de.orEmpty(),
        en = from.en.orEmpty(),
        es = from.es.orEmpty(),
        fr = from.fr.orEmpty(),
        ja = from.ja.orEmpty(),
        ptBR = from.ptBR.orEmpty(),
        ru = from.ru.orEmpty(),
        zhCN = from.zhCN.orEmpty(),
    )

}
