package br.com.victorcs.core.model

import okhttp3.Interceptor

data class RetrofitParams(
    val baseUrl: String,
    val header: Interceptor,
)
