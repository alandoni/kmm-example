package com.example.kmmexample.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

actual interface Cancellable {
    actual fun cancel()
}

actual class CancellableImpl actual constructor(private val job: Job): Cancellable {
    actual override fun cancel() {
        job.cancel()
    }
}

actual open class ViewModel {
    actual val scope = CoroutineScope(Dispatchers.Main)
    actual fun coroutine(block: suspend () -> Unit): Cancellable {
        return CancellableImpl(Job())
    }
}

actual open class CommonFlow<T> actual constructor(origin: Flow<T>) : Flow<T> by origin {
    actual fun watch(block: (T) -> Unit): Cancellable {
        return CancellableImpl(Job())
    }
}