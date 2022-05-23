package com.example.kmmexample.viewmodel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

expect open class CommonFlow<T> constructor(origin: Flow<T>) : Flow<T> {
    fun collect(block: (T) -> Unit): Cancellable
}

fun <T> Flow<T>.asCommonFlow(): CommonFlow<T> = CommonFlow(this)

fun <I, T> CommonFlow<T>.map(block: (T) -> I): CommonFlow<I> {
    return (this as Flow<T>).map(block).asCommonFlow()
}