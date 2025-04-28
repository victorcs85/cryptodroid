package br.com.victorcs.cryptodroid.infrastructure.source.remote.inteceptors

import br.com.victorcs.cryptodroid.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

private const val HEADER_TOKEN = "X-CoinAPI-Key"
private const val HEADER_ACCEPT = "Accept"
private const val JSON_APPLICATION = "application/json"

class Auth2HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
            .header(HEADER_TOKEN, BuildConfig.TOKEN_KEY)
            .header(HEADER_ACCEPT, JSON_APPLICATION)

        return chain.proceed(builder.build())
    }
}
