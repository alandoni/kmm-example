package com.example.kmmexample.viewmodel

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

expect interface Cancellable {
    fun cancel()
}

expect class CancellableImpl(job: Job): Cancellable {
    override fun cancel()
}

expect open class ViewModel() {
    val scope: CoroutineScope
    fun coroutine(block: suspend () -> Unit): Cancellable
}

fun <T> Flow<T>.asCommonFlow(): CommonFlow<T> = CommonFlow(this)

fun <T> MutableStateFlow<T>.asMutableCommonFlow(): MutableCommonFlow<T> = MutableCommonFlow(this)

expect open class CommonFlow<T> constructor(origin: Flow<T>) : Flow<T> {
    fun watch(block: (T) -> Unit): Cancellable
}

fun <I, T> CommonFlow<T>.map(block: (T) -> I): CommonFlow<I> {
    return (this as Flow<T>).map(block).asCommonFlow()
}

fun <I, T> MutableCommonFlow<T>.map(block: (T) -> I): CommonFlow<I> {
    return (this as MutableStateFlow<T>).map(block).asCommonFlow()
}

fun <T> MutableCommonFlow<T>.asCommonFlow(): CommonFlow<T> {
    return (this as Flow<T>).asCommonFlow()
}

expect open class MutableCommonFlow<T>
    constructor(
        origin: MutableStateFlow<T>
    ): MutableStateFlow<T> {

    fun watch(block: (T) -> Unit): Cancellable
}
