package br.com.victorcs.cryptodroid.di

import androidx.lifecycle.SavedStateHandle
import br.com.victorcs.cryptodroid.core.constants.API_URL
import br.com.victorcs.cryptodroid.core.constants.ICON_MAPPER
import br.com.victorcs.cryptodroid.core.interceptor.ConnectivityInterceptor
import br.com.victorcs.cryptodroid.core.services.WifiService
import br.com.victorcs.cryptodroid.data.source.remote.CoinAPI
import br.com.victorcs.cryptodroid.data.source.remote.RetrofitConfig
import br.com.victorcs.cryptodroid.data.source.remote.entity.ExchangeResponse
import br.com.victorcs.cryptodroid.data.source.remote.entity.IconResponse
import br.com.victorcs.cryptodroid.data.source.remote.mapper.ExchangeIconMapper
import br.com.victorcs.cryptodroid.data.source.remote.mapper.ExchangeMapper
import br.com.victorcs.cryptodroid.data.source.remote.repository.ExchangeDetailsRepositoryImpl
import br.com.victorcs.cryptodroid.data.source.remote.repository.ExchangesRepositoryImpl
import br.com.victorcs.cryptodroid.domain.mapper.DomainMapper
import br.com.victorcs.cryptodroid.domain.model.Exchange
import br.com.victorcs.cryptodroid.domain.model.Icon
import br.com.victorcs.cryptodroid.domain.repository.IExchangeDetailsRepository
import br.com.victorcs.cryptodroid.domain.repository.IExchangesRepository
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

    //region Data Sources
    private val dataSourceModule = module {
        single { retrofitConfig(CoinAPI::class.java) }
    }
    //endregion

    //region Repositories
    private val repositoriesModule = module {
        single<IExchangesRepository>() {
            ExchangesRepositoryImpl(
                service = get(),
                mapper = get(),
                iconMapper = get(named(ICON_MAPPER))
            )
        }
        single<IExchangeDetailsRepository>() {
            ExchangeDetailsRepositoryImpl(
                service = get(),
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
                repository = get(),
                dispatchers = get()
            )
        }
        viewModel { (savedStateHandle: SavedStateHandle) ->
            ExchangeDetailsViewModel(
                repository = get(),
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
        dataSourceModule,
        repositoriesModule,
        mappersModule,
        networkModule,
        serviceModule,
        interceptorModule,
        viewModelsModule,
        providerModule
    )
}