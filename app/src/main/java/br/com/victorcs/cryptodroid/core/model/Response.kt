package br.com.victorcs.cryptodroid.core.model

sealed class Response<out T> {
    data class Success<out T>(
        val data: T,
    ) : Response<T>()

    data class Error(
        val errorMessage: String,
        val errorType: ErrorType? = null
    ) : Response<Nothing>()
}

enum class ErrorType {
    NETWORK_ERROR,
    RATE_LIMIT_ERROR,
    GENERIC_ERROR
}
