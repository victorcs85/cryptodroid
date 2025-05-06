package br.com.victorcs.core.interceptor

import br.com.victorcs.core.constants.TOO_MANY_REQUESTS
import br.com.victorcs.core.exceptions.RateLimitException
import br.com.victorcs.core.exceptions.WithoutNetworkException
import br.com.victorcs.core.services.WifiService
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptor(private val wifiService: WifiService) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (wifiService.isOnline().not()) {
            throw WithoutNetworkException()
        }

        val request = chain.request()
        val response = chain.proceed(request)

        if (response.code == TOO_MANY_REQUESTS) {
            throw RateLimitException()
        }

        return response
    }
}
