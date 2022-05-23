package com.example.kmmexample.viewmodel

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow

actual open class MutableCommonFlow<T>
actual constructor(
    origin: MutableStateFlow<T>
) : MutableStateFlow<T> by origin {
    actual fun collect(block: (T) -> Unit): Cancellable {
        return CancellableImpl(Job())
    }
}