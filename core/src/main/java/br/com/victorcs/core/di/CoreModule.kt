package br.com.victorcs.core.di

import br.com.victorcs.core.interceptor.ConnectivityInterceptor
import br.com.victorcs.core.model.RetrofitParams
import br.com.victorcs.core.remote.RetrofitConfig
import br.com.victorcs.core.services.WifiService
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

//region Network
val coreNetworkModule = module {
    single { (params: RetrofitParams) ->
        RetrofitConfig.createRetrofit(
            wifiService = get(),
            context = androidContext(),
            params = params,
        )
    }
}

val coreServiceModule = module {
    single { WifiService(androidContext()) }
}

val coreInterceptorModule = module {
    single { ConnectivityInterceptor(get()) }
}
//endregion
