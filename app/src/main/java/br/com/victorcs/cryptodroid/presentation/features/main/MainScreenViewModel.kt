package br.com.victorcs.cryptodroid.presentation.features.main

import br.com.victorcs.core.base.BaseViewModel
import br.com.victorcs.core.constants.EMPTY
import br.com.victorcs.core.utils.IDispatchersProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainScreenViewModel(dispatchers: IDispatchersProvider,
) : BaseViewModel(dispatchers) {
    private val _titleAppBar = MutableStateFlow<String>(EMPTY)
    val titleAppBar: StateFlow<String> = _titleAppBar.asStateFlow()

    fun setTitleAppBar(title: String) {
        _titleAppBar.value = title
    }
}
