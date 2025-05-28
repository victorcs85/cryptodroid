package br.com.victorcs.core.remote

import android.content.Context
import br.com.victorcs.core.BuildConfig
import br.com.victorcs.core.interceptor.ConnectivityInterceptor
import br.com.victorcs.core.model.RetrofitParams
import br.com.victorcs.core.remote.inteceptors.CacheControlInterceptor
import br.com.victorcs.core.services.WifiService
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

private const val HTTP_CACHE = "http-cache"
private const val HUNDRED = 100L
private const val ONE_HUNDRED_AND_TWENTY = 120L
private const val CACHE_MAX_SIZE = 10L * 1024L * 1024L

object RetrofitConfig {

    fun createRetrofit(
        wifiService: WifiService,
        context: Context,
        params: RetrofitParams,
    ): Retrofit {
        val okHttpClient = OkHttpClient.Builder().also { builder ->
            params.header?.let {
                builder.addInterceptor(it)
            }
                builder.addInterceptor(CacheControlInterceptor(wifiService))
                .connectTimeout(HUNDRED, TimeUnit.SECONDS)
                .readTimeout(HUNDRED, TimeUnit.SECONDS)
                .writeTimeout(ONE_HUNDRED_AND_TWENTY, TimeUnit.SECONDS)
                .cache(
                    Cache(
                        File(context.cacheDir, HTTP_CACHE),
                        CACHE_MAX_SIZE,
                    ),
                )
                .addInterceptor(ConnectivityInterceptor(wifiService))
                .addInterceptor(getHttpLogging())
        }

        if (BuildConfig.DEBUG) {
            okHttpClient.addNetworkInterceptor(StethoInterceptor())
        }

        return Retrofit.Builder()
            .baseUrl(params.baseUrl)
            .client(okHttpClient.build())
            .addConverterFactory(MoshiConverterFactory.create(MoshiBuilder.create()))
            .build()
    }

    private fun getHttpLogging(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            },
        )
}
