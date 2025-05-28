package br.com.victorcs.cryptodroid.di

import androidx.lifecycle.SavedStateHandle
import br.com.victorcs.core.constants.ICON_MAPPER
import br.com.victorcs.core.constants.LOCAL_JSON_NAMED
import br.com.victorcs.core.constants.REMOTE_NAMED
import br.com.victorcs.core.di.coreInterceptorModule
import br.com.victorcs.core.di.coreNetworkModule
import br.com.victorcs.core.di.coreServiceModule
import br.com.victorcs.core.mapper.DomainMapper
import br.com.victorcs.core.model.RetrofitParams
import br.com.victorcs.core.utils.IDispatchersProvider
import br.com.victorcs.core.utils.IDispatchersProviderImpl
import br.com.victorcs.cryptodroid.BuildConfig
import br.com.victorcs.cryptodroid.data.entity.ExchangeResponse
import br.com.victorcs.cryptodroid.data.entity.IconResponse
import br.com.victorcs.cryptodroid.data.mapper.ExchangeIconMapper
import br.com.victorcs.cryptodroid.data.mapper.ExchangeMapper
import br.com.victorcs.cryptodroid.domain.model.Exchange
import br.com.victorcs.cryptodroid.domain.model.Icon
import br.com.victorcs.cryptodroid.domain.repository.IExchangeDetailsRepository
import br.com.victorcs.cryptodroid.domain.repository.IExchangesRepository
import br.com.victorcs.cryptodroid.infrastructure.source.local.repository.ExchangeLocalProvider
import br.com.victorcs.cryptodroid.infrastructure.source.local.repository.IExchangeLocalProvider
import br.com.victorcs.cryptodroid.infrastructure.source.remote.CoinAPI
import br.com.victorcs.cryptodroid.infrastructure.source.remote.inteceptors.AppCoinAuth2HeaderInterceptor
import br.com.victorcs.cryptodroid.infrastructure.source.remote.repository.ExchangeDetailsRepositoryImpl
import br.com.victorcs.cryptodroid.infrastructure.source.remote.repository.ExchangesRepositoryImpl
import br.com.victorcs.cryptodroid.presentation.features.exchangedetails.ui.ExchangeDetailsViewModel
import br.com.victorcs.cryptodroid.presentation.features.exchanges.ui.ExchangesViewModel
import br.com.victorcs.cryptodroid.presentation.features.main.MainViewModel
import br.com.victorcs.lightning.di.lightningPresentationModule
import br.com.victorcs.lightning.di.lightningRemoteModule
import br.com.victorcs.lightning.di.networkLightningModule
import okhttp3.Interceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import br.com.victorcs.cryptodroid.infrastructure.source.local.repository.ExchangeDetailsRepositoryImpl as LocalExchangeDetailsRepositoryImpl
import br.com.victorcs.cryptodroid.infrastructure.source.local.repository.ExchangesRepositoryImpl as LocalExchangesRepositoryImpl

private const val APP_COIN_API_HEADERS = "AppCoinAPIHeaders"
private const val APP_COIN_RETROFIT_PARAMS = "AppCoinRetrofitParams"
private const val APP_RETROFIT = "AppRetrofit"
private const val LOCAL_PROVIDER = "LocalExchangeProvider"
private const val EXCHANGE_MAPPER = "ExchangeMapper"

class CoinInitialization : ModuleInitialization() {

    //region Network
    val appInterceptorModule = module {
        single<Interceptor>((named(APP_COIN_API_HEADERS))) { AppCoinAuth2HeaderInterceptor() }
    }
    private val networkModule = module {
        single(named(APP_COIN_RETROFIT_PARAMS)) {
            RetrofitParams(
                baseUrl = BuildConfig.API_URL,
                header = get<Interceptor>(named(APP_COIN_API_HEADERS)),
            )
        }

        single<Retrofit>(named(APP_RETROFIT)) {
            val params: RetrofitParams = get(named(APP_COIN_RETROFIT_PARAMS))
            get<Retrofit>(parameters = { parametersOf(params) } )
        }

        single<CoinAPI> { get<Retrofit>(named(APP_RETROFIT)).create(CoinAPI::class.java) }
    }
    //endregion

    //region Infrastructure Sources
    private val infrastructureSourceModule = module {
        single<IExchangeLocalProvider>(named(LOCAL_PROVIDER)) {
            ExchangeLocalProvider(
                context = androidContext(),
            )
        }
    }
    //endregion

    //region Repositories
    private val repositoriesModule = module {
        single<IExchangesRepository>(named(REMOTE_NAMED)) {
            ExchangesRepositoryImpl(
                service = get(named(APP_RETROFIT)),
                mapper = get(named(EXCHANGE_MAPPER)),
                iconMapper = get(named(ICON_MAPPER)),
            )
        }
        single<IExchangeDetailsRepository>(named(REMOTE_NAMED)) {
            ExchangeDetailsRepositoryImpl(
                service = get(named(APP_RETROFIT)),
                mapper = get(named(EXCHANGE_MAPPER)),
            )
        }

        single<IExchangesRepository>(named(LOCAL_JSON_NAMED)) {
            LocalExchangesRepositoryImpl(
                provider = get(named(LOCAL_PROVIDER)),
                mapper = get(named(EXCHANGE_MAPPER)),
                iconMapper = get(named(ICON_MAPPER)),
            )
        }
        single<IExchangeDetailsRepository>(named(LOCAL_JSON_NAMED)) {
            LocalExchangeDetailsRepositoryImpl(
                provider = get(named(LOCAL_PROVIDER)),
                mapper = get(named(EXCHANGE_MAPPER)),
            )
        }
    }
    //endregion

    //region Mappers
    private val mappersModule = module {
        single<DomainMapper<ExchangeResponse, Exchange>>(named(EXCHANGE_MAPPER)) { ExchangeMapper() }
        single<DomainMapper<IconResponse, Icon>>(named(ICON_MAPPER)) { ExchangeIconMapper() }
    }
    //endregion

    //region ViewModels
    private val viewModelsModule = module {
        val source = getSource()

        viewModel {
            ExchangesViewModel(
                repository = get(named(source)),
                dispatchers = get(),
            )
        }
        viewModel { (savedStateHandle: SavedStateHandle) ->
            ExchangeDetailsViewModel(
                repository = get(named(source)),
                dispatchers = get(),
                savedStateHandle = savedStateHandle,
            )
        }
        single {
            MainViewModel(
                dispatchers = get(),
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
        appInterceptorModule,
        infrastructureSourceModule,
        repositoriesModule,
        mappersModule,
        networkModule,
        coreNetworkModule,
        coreServiceModule,
        coreInterceptorModule,
        viewModelsModule,
        providerModule,
        lightningRemoteModule,
        lightningPresentationModule,
        networkLightningModule
    )

    private fun getSource(): String {
        if (BuildConfig.DEBUG) {
            return LOCAL_JSON_NAMED
        }

        return REMOTE_NAMED
    }
}
