package com.example.kmmexample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

actual open class ViewModel: ViewModel() {
    actual val scope = viewModelScope
    actual fun coroutine(block: suspend () -> Unit): Cancellable {
        return CancellableImpl(
            scope.launch {
                block()
            }
        )
    }
}
