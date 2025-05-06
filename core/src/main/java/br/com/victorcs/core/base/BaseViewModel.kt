package br.com.victorcs.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.victorcs.core.utils.IDispatchersProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import timber.log.Timber

abstract class BaseViewModel(private val dispatchersProvider: IDispatchersProvider) : ViewModel(), KoinComponent {

    protected fun launch(
        coroutineDispatcher: CoroutineDispatcher = dispatchersProvider.io,
        errorBlock: ((Throwable) -> Unit?)? = null,
        block: suspend CoroutineScope.() -> Unit,
    ) = viewModelScope.launch(coroutineDispatcher) {
        runCatching {
            block()
        }.onFailure { error ->
            errorBlock?.invoke(error)
            Timber.e(error)
        }
    }
}
