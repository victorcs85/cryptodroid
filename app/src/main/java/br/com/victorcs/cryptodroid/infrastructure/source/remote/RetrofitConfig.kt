package br.com.victorcs.cryptodroid.infrastructure.source.remote

import android.content.Context
import br.com.victorcs.cryptodroid.BuildConfig
import br.com.victorcs.cryptodroid.core.interceptor.ConnectivityInterceptor
import br.com.victorcs.cryptodroid.core.services.WifiService
import br.com.victorcs.cryptodroid.infrastructure.source.MoshiBuilder
import br.com.victorcs.cryptodroid.infrastructure.source.remote.inteceptors.Auth2HeaderInterceptor
import br.com.victorcs.cryptodroid.infrastructure.source.remote.inteceptors.CacheControlInterceptor
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

    fun <T> create(service: Class<T>, baseUrl: String, wifiService: WifiService, context: Context): T {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(Auth2HeaderInterceptor())
            .addInterceptor(CacheControlInterceptor(wifiService))
            .connectTimeout(HUNDRED, TimeUnit.SECONDS)
            .readTimeout(HUNDRED, TimeUnit.SECONDS)
            .writeTimeout(ONE_HUNDRED_AND_TWENTY, TimeUnit.SECONDS)
            .cache(
                Cache(
                    File(context.cacheDir, HTTP_CACHE),
                    CACHE_MAX_SIZE
                )
            )
            .addInterceptor(ConnectivityInterceptor(wifiService))
            .addInterceptor(getHttpLogging())

        if (BuildConfig.DEBUG) {
            okHttpClient.addNetworkInterceptor(StethoInterceptor())
        }

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient.build())
            .addConverterFactory(MoshiConverterFactory.create(MoshiBuilder.create()))
            .build()
            .create(service) as T
    }

    private fun getHttpLogging(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else HttpLoggingInterceptor.Level.NONE
        )
}
