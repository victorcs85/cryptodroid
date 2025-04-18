package br.com.victorcs.cryptodroid.core.extensions

import br.com.victorcs.cryptodroid.core.constants.GENERIC_MESSAGE_ERROR
import br.com.victorcs.cryptodroid.core.exceptions.RateLimitException
import br.com.victorcs.cryptodroid.core.exceptions.WithoutNetworkException
import br.com.victorcs.cryptodroid.core.model.ErrorType
import br.com.victorcs.cryptodroid.core.model.Response
import timber.log.Timber

fun Boolean?.orFalse() = this ?: false

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Response<T> {
    return try {
        Response.Success(apiCall.invoke())
    } catch (e: RateLimitException) {
        Timber.e(e)
        Response.Error(e.message ?: GENERIC_MESSAGE_ERROR, ErrorType.RATE_LIMIT_ERROR)
    } catch (e: WithoutNetworkException) {
        Timber.e(e)
        Response.Error(e.message ?: GENERIC_MESSAGE_ERROR, ErrorType.NETWORK_ERROR)
    } catch (e: Exception) {
        Timber.e(e)
        Response.Error(GENERIC_MESSAGE_ERROR, ErrorType.GENERIC_ERROR)
    }
}