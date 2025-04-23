package br.com.victorcs.cryptodroid.di

import androidx.lifecycle.SavedStateHandle
import br.com.victorcs.cryptodroid.core.constants.API_URL
import br.com.victorcs.cryptodroid.core.constants.ICON_MAPPER
import br.com.victorcs.cryptodroid.core.constants.LOCAL_NAMED
import br.com.victorcs.cryptodroid.core.constants.REMOTE_NAMED
import br.com.victorcs.cryptodroid.core.interceptor.ConnectivityInterceptor
import br.com.victorcs.cryptodroid.core.services.WifiService
import br.com.victorcs.cryptodroid.data.entity.ExchangeResponse
import br.com.victorcs.cryptodroid.data.entity.IconResponse
import br.com.victorcs.cryptodroid.data.mapper.ExchangeIconMapper
import br.com.victorcs.cryptodroid.data.mapper.ExchangeMapper
import br.com.victorcs.cryptodroid.domain.mapper.DomainMapper
import br.com.victorcs.cryptodroid.domain.model.Exchange
import br.com.victorcs.cryptodroid.domain.model.Icon
import br.com.victorcs.cryptodroid.domain.repository.IExchangeDetailsRepository
import br.com.victorcs.cryptodroid.domain.repository.IExchangesRepository
import br.com.victorcs.cryptodroid.infrastructure.source.local.repository.ExchangeLocalProvider
import br.com.victorcs.cryptodroid.infrastructure.source.local.repository.IExchangeLocalProvider
import br.com.victorcs.cryptodroid.infrastructure.source.remote.CoinAPI
import br.com.victorcs.cryptodroid.infrastructure.source.remote.RetrofitConfig
import br.com.victorcs.cryptodroid.infrastructure.source.remote.repository.ExchangeDetailsRepositoryImpl
import br.com.victorcs.cryptodroid.infrastructure.source.remote.repository.ExchangesRepositoryImpl
import br.com.victorcs.cryptodroid.presentation.features.exchangedetails.ui.ExchangeDetailsViewModel
import br.com.victorcs.cryptodroid.presentation.features.exchanges.ui.ExchangesViewModel
import br.com.victorcs.cryptodroid.presentation.utils.IDispatchersProvider
import br.com.victorcs.cryptodroid.presentation.utils.IDispatchersProviderImpl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import br.com.victorcs.cryptodroid.infrastructure.source.local.repository.ExchangeDetailsRepositoryImpl as LocalExchangeDetailsRepositoryImpl
import br.com.victorcs.cryptodroid.infrastructure.source.local.repository.ExchangesRepositoryImpl as LocalExchangesRepositoryImpl

class CoinInitialization : ModuleInitialization() {

    //region Network
    private fun <T> Scope.retrofitConfig(service: Class<T>) = RetrofitConfig.create(
        service,
        API_URL,
        get()
    )

    private val networkModule = module {
        single { OkHttpClient.Builder().addInterceptor(get<Interceptor>()).build() }
        single {
            Retrofit.Builder().client(get()).addConverterFactory(MoshiConverterFactory.create())
                .build()
        }
    }

    private val serviceModule = module {
        single { WifiService(androidContext()) }
    }

    private val interceptorModule = module {
        single { ConnectivityInterceptor(get()) }
    }
    //endregion

    //region Infrastructure Sources
    private val infrastructureSourceModule = module {
        single { retrofitConfig(CoinAPI::class.java) }

        single<IExchangeLocalProvider> {
            ExchangeLocalProvider(
                context = androidContext()
            )
        }
    }
    //endregion

    //region Repositories
    private val repositoriesModule = module {
        single<IExchangesRepository>(named(REMOTE_NAMED)) {
            ExchangesRepositoryImpl(
                service = get(),
                mapper = get(),
                iconMapper = get(named(ICON_MAPPER))
            )
        }
        single<IExchangeDetailsRepository>(named(REMOTE_NAMED)) {
            ExchangeDetailsRepositoryImpl(
                service = get(),
                mapper = get()
            )
        }

        single<IExchangesRepository>(named(LOCAL_NAMED)) {
            LocalExchangesRepositoryImpl(
                provider = get(),
                mapper = get(),
                iconMapper = get(named(ICON_MAPPER))
            )
        }
        single<IExchangeDetailsRepository>(named(LOCAL_NAMED)) {
            LocalExchangeDetailsRepositoryImpl(
                provider = get(),
                mapper = get()
            )
        }
    }
    //endregion

    //region Mappers
    private val mappersModule = module {
        single<DomainMapper<ExchangeResponse, Exchange>> { ExchangeMapper() }
        single<DomainMapper<IconResponse, Icon>>(named(ICON_MAPPER)) { ExchangeIconMapper() }
    }
    //endregion

    //region ViewModels
    private val viewModelsModule = module {
        viewModel {
            ExchangesViewModel(
                repository = get(named(LOCAL_NAMED)),
                dispatchers = get()
            )
        }
        viewModel { (savedStateHandle: SavedStateHandle) ->
            ExchangeDetailsViewModel(
                repository = get(named(LOCAL_NAMED)),
                dispatchers = get(),
                savedStateHandle = savedStateHandle
            )
        }
    }
    //endregion

    //region Provider
    private val providerModule = module {
        single<IDispatchersProvider> { IDispatchersProviderImpl() }
    }
    //endregion

    override fun init(): List<Module> = listOf(
        infrastructureSourceModule,
        repositoriesModule,
        mappersModule,
        networkModule,
        serviceModule,
        interceptorModule,
        viewModelsModule,
        providerModule
    )
}
