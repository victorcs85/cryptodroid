package br.com.victorcs.lightning.presentation.features.lightnings.features.ratings.ui

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.lifecycle.viewModelScope
import br.com.victorcs.core.base.BaseViewModel
import br.com.victorcs.core.constants.GENERIC_MESSAGE_ERROR
import br.com.victorcs.core.constants.STOP_TIMER_LIMIT
import br.com.victorcs.core.model.Response
import br.com.victorcs.core.utils.IDispatchersProvider
import br.com.victorcs.lightning.domain.model.Node
import br.com.victorcs.lightning.domain.repository.IRankingsConnectivityRepository
import br.com.victorcs.lightning.presentation.features.lightnings.features.ratings.command.LightningRatingsCommand
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class LightningRatingsViewModel(
    private val repository: IRankingsConnectivityRepository,
    dispatchers: IDispatchersProvider,
) : BaseViewModel(dispatchers) {
    private val _state = MutableStateFlow(LightningRatingsScreenState())
    val screenState: StateFlow<LightningRatingsScreenState> = _state.onStart {
        getRatings()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(STOP_TIMER_LIMIT),
        initialValue = LightningRatingsScreenState().copy(isLoading = true),
    )

    fun execute(command: LightningRatingsCommand) = when (command) {
        is LightningRatingsCommand.GetRatings -> getRatings()
    }

    private fun getRatings() {
        launch(
            block = {
                _state.value = _state.value.copy(
                    isLoading = true,
                )
                val ratings = repository.getRankingsConnectivity()

                when (ratings) {
                    is Response.Success -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            ratings = ratings.data
                        )
                    }

                    is Response.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            errorMessage = ratings.errorMessage
                        )
                    }
                }
            },
            errorBlock = { error ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = error.message ?: GENERIC_MESSAGE_ERROR
                )
                Unit
            }
        )
    }
}

@Stable
@Immutable
data class LightningRatingsScreenState(
    val isLoading: Boolean = false,
    val ratings: List<Node> = emptyList(),
    val errorMessage: String? = null,
)
