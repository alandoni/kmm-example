package com.example.kmmexample.viewmodel

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

actual open class CommonFlow<T>
actual constructor(origin: Flow<T>) : Flow<T> by origin {
    actual fun collect(block: (T) -> Unit): Cancellable {
        return CancellableImpl(Job())
    }
}