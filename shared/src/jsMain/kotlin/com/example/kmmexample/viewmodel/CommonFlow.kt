package com.example.kmmexample.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

actual open class CommonFlow<T> actual constructor(origin: Flow<T>) :
    Flow<T> {
    actual fun watch(block: (T) -> Unit): Cancellable {
        TODO("Not yet implemented")
    }
}

actual open class ViewModel actual constructor() {
    actual val scope: CoroutineScope
        get() = TODO("Not yet implemented")

    actual fun coroutine(block: suspend () -> Unit): Cancellable {
        TODO("Not yet implemented")
    }
}

actual interface Cancellable {
    actual fun cancel()
}

actual class CancellableImpl actual constructor(job: Job) :
    Cancellable {
    actual override fun cancel() {
    }
}