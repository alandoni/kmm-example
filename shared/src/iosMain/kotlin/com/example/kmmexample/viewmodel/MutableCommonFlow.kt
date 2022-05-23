package com.example.kmmexample.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

actual open class MutableCommonFlow<T>
actual constructor(
    private val origin: MutableStateFlow<T>
): MutableStateFlow<T> by origin {

    actual fun collect(block: (T) -> Unit): Cancellable {
        val job = defaultScope.launch {
            origin.collect {
                block(it)
            }
        }

        return CancellableImpl(job)
    }
}