package br.com.victorcs.lightning.di

import br.com.victorcs.core.mapper.DomainMapper
import br.com.victorcs.core.model.RetrofitParams
import br.com.victorcs.lightning.BuildConfig
import br.com.victorcs.lightning.data.entity.NodeResponse
import br.com.victorcs.lightning.data.mapper.NodeMapper
import br.com.victorcs.lightning.domain.model.Node
import br.com.victorcs.lightning.domain.repository.IRankingsConnectivityRepository
import br.com.victorcs.lightning.infrastructure.source.remote.LightningAPI
import br.com.victorcs.lightning.infrastructure.source.remote.repository.RankingsConnectivityRepositoryImpl
import br.com.victorcs.lightning.presentation.features.lightnings.features.ratings.ui.LightningRatingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

private const val LIGHTNING_RETROFIT_PARAMS = "LightningRetrofitParams"
private const val LIGHTNING_RETROFIT = "LightningRetrofit"
private const val LIGHTNING_API = "LightningApi"
private const val NODE_MAPPER = "NodeMapper"

val lightningRemoteModule = module {
    single<IRankingsConnectivityRepository> {
        RankingsConnectivityRepositoryImpl(
            lightningAPI = get(named(LIGHTNING_API)),
            mapper = get(named(NODE_MAPPER))
        )
    }

    single<DomainMapper<NodeResponse, Node>>(named(NODE_MAPPER)) {
        NodeMapper()
    }
}

val lightningPresentationModule = module {
    viewModel {
        LightningRatingsViewModel(
            repository = get(),
            dispatchers = get()
        )
    }
}

//region Network}
val networkLightningModule = module {
    single(named(LIGHTNING_RETROFIT_PARAMS)) {
        RetrofitParams(
            baseUrl = BuildConfig.API_URL,
            header = null,
        )
    }

    single<Retrofit>(named(LIGHTNING_RETROFIT)) {
        val params: RetrofitParams = get(named(LIGHTNING_RETROFIT_PARAMS))
        get<Retrofit>(parameters = { parametersOf(params) })
    }

    single<LightningAPI>(named(LIGHTNING_API)) {
        get<Retrofit>(named(LIGHTNING_RETROFIT)).create(LightningAPI::class.java)
    }
}
//endregion
