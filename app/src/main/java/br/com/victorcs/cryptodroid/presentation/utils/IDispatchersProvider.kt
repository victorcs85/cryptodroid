package br.com.victorcs.cryptodroid.presentation.utils

import kotlinx.coroutines.CoroutineDispatcher

interface IDispatchersProvider {
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
    val default: CoroutineDispatcher
}
