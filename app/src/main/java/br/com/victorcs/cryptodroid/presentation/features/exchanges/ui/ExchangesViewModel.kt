package br.com.victorcs.cryptodroid.presentation.features.exchanges.ui

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.lifecycle.viewModelScope
import br.com.victorcs.core.base.BaseViewModel
import br.com.victorcs.core.constants.GENERIC_MESSAGE_ERROR
import br.com.victorcs.core.constants.STOP_TIMER_LIMIT
import br.com.victorcs.core.model.Response
import br.com.victorcs.core.utils.IDispatchersProvider
import br.com.victorcs.cryptodroid.domain.model.Exchange
import br.com.victorcs.cryptodroid.domain.repository.IExchangesRepository
import br.com.victorcs.cryptodroid.presentation.features.exchanges.command.ExchangesCommand
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class ExchangesViewModel(
    private val repository: IExchangesRepository,
    dispatchers: IDispatchersProvider,
) : BaseViewModel(dispatchers) {

    private val _state = MutableStateFlow(ExchangesScreenState())

    val screenState: StateFlow<ExchangesScreenState> = _state
        .onStart {
            fetchExchanges()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMER_LIMIT),
            initialValue = ExchangesScreenState().copy(isLoading = true),
        )

    fun execute(command: ExchangesCommand) = when (command) {
        is ExchangesCommand.FetchExchanges -> fetchExchanges()
        is ExchangesCommand.RefreshExchanges -> refreshExchanges()
    }

    private fun fetchExchanges() {
        launch(
            block = {
                _state.value = _state.value.copy(
                    isLoading = true,
                )
                val exchanges = repository.getExchanges()
                val icons = repository.getIcons()

                when {
                    exchanges is Response.Success && icons is Response.Success -> {
                        exchanges.data.forEach { exchange ->
                            exchange.icons = icons.data.filter { icon ->
                                icon.exchangeId == exchange.exchangeId
                            }
                        }
                        _state.value = _state.value.copy(
                            isLoading = false,
                            exchanges = exchanges.data,
                            errorMessage = null,
                        )
                    }

                    exchanges is Response.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            errorMessage = exchanges.errorMessage,
                        )
                    }

                    icons is Response.Error && exchanges is Response.Success -> {
                        _state.value = _state.value.copy(
                            exchanges = exchanges.data,
                            isLoading = false,
                        )
                    }

                    icons is Response.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            errorMessage = icons.errorMessage,
                        )
                    }
                }
            },
            errorBlock = { error ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = error.message ?: GENERIC_MESSAGE_ERROR,
                )
                Unit
            },
        )
    }

    private fun refreshExchanges() {
        _state.value = _state.value.copy(
            isRefreshing = true,
        )
        fetchExchanges()
    }
}

@Stable
@Immutable
data class ExchangesScreenState(
    val exchanges: List<Exchange>? = null,
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
