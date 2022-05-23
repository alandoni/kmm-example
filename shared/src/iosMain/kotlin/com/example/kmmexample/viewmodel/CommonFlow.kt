package com.example.kmmexample.viewmodel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

actual open class CommonFlow<T>
actual constructor(private val origin: Flow<T>) : Flow<T> by origin {

    actual fun collect(block: (T) -> Unit): Cancellable {
        val job = defaultScope.launch {
            origin.collect {
                block(it)
            }
        }

        return CancellableImpl(job)
    }
}