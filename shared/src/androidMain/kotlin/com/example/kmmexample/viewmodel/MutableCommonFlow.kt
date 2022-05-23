package com.example.kmmexample.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.plus

actual open class MutableCommonFlow<T>
actual constructor(
    private val origin: MutableStateFlow<T>
): MutableStateFlow<T> by origin {
    actual fun collect(block: (T) -> Unit): Cancellable {
        val job = Job()

        this.onEach {
            block(it)
        }.launchIn(CoroutineScope(Dispatchers.Main) + job)

        return CancellableImpl(job)
    }
}