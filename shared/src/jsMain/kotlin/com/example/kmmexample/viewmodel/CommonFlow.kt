package com.example.kmmexample.viewmodel

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

actual open class CommonFlow<T>
actual constructor(private val origin: Flow<T>): Flow<T> by origin {

    actual fun collect(block: (T) -> Unit): Cancellable {
        val job = MainScope().launch {
            origin.collect {
                block(it)
            }
        }
        return CancellableImpl(job)
    }
}

actual open class MutableCommonFlow<T>
actual constructor(
    private val origin: MutableStateFlow<T>
): MutableStateFlow<T> by origin {
    actual fun collect(block: (T) -> Unit): Cancellable {
        val job = MainScope().launch {
            origin.collect {
                block(it)
            }
        }
        return CancellableImpl(job)
    }
}