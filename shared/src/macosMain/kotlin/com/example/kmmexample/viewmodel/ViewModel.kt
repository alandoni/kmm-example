package com.example.kmmexample.viewmodel

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

actual open class ViewModel actual constructor() {
    actual val scope = MainScope()

    actual fun coroutine(block: suspend () -> Unit): Cancellable {
        return CancellableImpl(
            scope.launch {
                block()
            }
        )
    }
}