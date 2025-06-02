package br.com.victorcs.lightning.data.mapper

import br.com.victorcs.core.mapper.DomainMapper
import br.com.victorcs.lightning.data.entity.CityResponse
import br.com.victorcs.lightning.domain.model.City

class CityMapper : DomainMapper<CityResponse, City> {

    override fun toDomain(from: CityResponse) = City(
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
