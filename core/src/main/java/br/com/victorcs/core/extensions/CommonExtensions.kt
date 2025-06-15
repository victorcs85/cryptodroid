package br.com.victorcs.core.extensions

import br.com.victorcs.core.constants.GENERIC_MESSAGE_ERROR
import br.com.victorcs.core.constants.NETWORK_MESSAGE_ERROR
import br.com.victorcs.core.exceptions.RateLimitException
import br.com.victorcs.core.exceptions.WithoutNetworkException
import br.com.victorcs.core.model.ErrorType
import br.com.victorcs.core.model.Response
import timber.log.Timber
import java.io.IOException

fun Boolean?.orFalse() = this == true
fun Int?.orZero() = this ?: 0
fun Long?.orZero() = this ?: 0L

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Response<T> {
    return try {
        Response.Success(apiCall.invoke())
    } catch (e: Exception) {
        Timber.e(e)
        when (e) {
            is RateLimitException ->
                Response.Error(e.message ?: GENERIC_MESSAGE_ERROR, ErrorType.RATE_LIMIT_ERROR)

            is WithoutNetworkException, is IOException ->
                Response.Error(e.message ?: NETWORK_MESSAGE_ERROR, ErrorType.NETWORK_ERROR)

            else ->
                Response.Error(GENERIC_MESSAGE_ERROR, ErrorType.GENERIC_ERROR)
        }
    }
}
