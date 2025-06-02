package br.com.victorcs.cryptodroid.presentation.features.exchangedetails.ui

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import br.com.victorcs.core.base.BaseViewModel
import br.com.victorcs.core.constants.EXCHANGE_ID
import br.com.victorcs.core.constants.GENERIC_MESSAGE_ERROR
import br.com.victorcs.core.constants.STOP_TIMER_LIMIT
import br.com.victorcs.core.model.Response
import br.com.victorcs.core.utils.IDispatchersProvider
import br.com.victorcs.cryptodroid.di.DataSourceType
import br.com.victorcs.cryptodroid.domain.model.Exchange
import br.com.victorcs.cryptodroid.domain.repository.IExchangeDetailsRepository
import br.com.victorcs.cryptodroid.presentation.features.exchangedetails.command.ExchangeDetailsCommand
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class ExchangeDetailsViewModel(
    private val repository: IExchangeDetailsRepository,
    private val savedStateHandle: SavedStateHandle,
    dispatchers: IDispatchersProvider,
    private val dataSourceType: DataSourceType
) : BaseViewModel(dispatchers) {

    private val _state = MutableStateFlow(ExchangeDetailsScreenState())
    val screenState: StateFlow<ExchangeDetailsScreenState> = _state
        .onStart {
            savedStateHandle.get<String>(EXCHANGE_ID)?.let { exchangeId ->
                if (_state.value.exchange == null) {
                    getExchangeDetails(exchangeId)
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMER_LIMIT),
            initialValue = ExchangeDetailsScreenState().copy(isLoading = true),
        )

    fun execute(command: ExchangeDetailsCommand) = when (command) {
        is ExchangeDetailsCommand.GetExchangeDetails -> getExchangeDetails(command.exchangeId)
    }

    private fun getExchangeDetails(exchangeId: String) {
        launch {
            _state.update { currentState ->
                currentState.copy(isLoading = true)
            }
            val exchangeResponse = repository.getExchangeDetails(exchangeId)

            if (exchangeResponse is Response.Success && exchangeResponse.data.isNotEmpty()) {
                val exchange = if(dataSourceType == DataSourceType.REMOTE_SOURCE)
                    exchangeResponse.data.first()
                else
                    exchangeResponse.data.firstOrNull { it.exchangeId == exchangeId }

                _state.update { currentState ->
                    currentState.copy(
                        exchange = exchange,
                        isLoading = false,
                    )
                }
                savedStateHandle[EXCHANGE_ID] = exchangeId
            } else {
                _state.update { currentState ->
                    currentState.copy(
                        errorMessage = GENERIC_MESSAGE_ERROR,
                    )
                }
            }
        }
    }
}

@Stable
@Immutable
data class ExchangeDetailsScreenState(
    val exchange: Exchange? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
