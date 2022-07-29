package com.ionelchis.geoguess.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<INTENT : Intent, STATE : State, EFFECT : Effect> : ViewModel() {

    private val initialState: STATE by lazy { createInitialState() }
    protected abstract fun createInitialState(): STATE

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = _state.asStateFlow()

    private val _effect = Channel<EFFECT>()
    val effect = _effect.receiveAsFlow()

    private val currentState: STATE
        get() = state.value

    fun dispatchIntent(intent: INTENT) = handleIntent(intent)

    protected fun launch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch { block() }
    }

    protected fun setState(reduce: STATE.() -> STATE) {
        _state.value = currentState.reduce()
    }

    protected fun setState(newState: STATE) {
        _state.value = newState
    }

    protected fun setEffect(newEffect: EFFECT) {
        launch { _effect.send(newEffect) }
    }

    protected abstract fun handleIntent(intent: INTENT)
}