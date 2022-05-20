package com.example.kmmexample.viewmodel

import com.example.kmmexample.data.models.RocketLaunch
import com.example.kmmexample.data.repository.LaunchesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainViewModel: ViewModel(), KoinComponent {

    private val repository: LaunchesRepository by inject()

    private val _state =
        MutableStateFlow<MainViewModelState>(MainViewModelState.Loading)
    val state
        get() = _state.asCommonFlow()

    fun getRocketLaunches() = coroutine {
        _state.value = MainViewModelState.Loading
        try {
            _state.value = MainViewModelState.Success(repository.getLaunches())
        } catch (e: Exception) {
            _state.value = MainViewModelState.Error(e)
        }
    }
}

sealed class MainViewModelState {
    object Loading : MainViewModelState()
    data class Success(val value: List<RocketLaunch>) : MainViewModelState()
    data class Error(val error: Throwable) : MainViewModelState()
}