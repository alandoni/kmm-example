package com.example.kmmexample.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

actual open class ViewModel {
    actual val scope = CoroutineScope(Dispatchers.Main)
    actual fun coroutine(block: suspend () -> Unit): Cancellable {
        return CancellableImpl(Job())
    }
}
