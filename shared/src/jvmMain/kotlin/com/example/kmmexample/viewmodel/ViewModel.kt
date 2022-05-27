package com.example.kmmexample.viewmodel

import kotlinx.coroutines.*

actual open class ViewModel {
    actual val scope = CoroutineScope(Job() + Dispatchers.Default)
    actual fun coroutine(block: suspend () -> Unit): Cancellable {
        return CancellableImpl(
            scope.launch {
                block()
            }
        )
    }
}

