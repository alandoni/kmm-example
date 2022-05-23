package com.example.kmmexample.viewmodel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

expect open class MutableCommonFlow<T>
constructor(origin: MutableStateFlow<T>): MutableStateFlow<T> {
    fun collect(block: (T) -> Unit): Cancellable
}

fun <T> MutableStateFlow<T>.asMutableCommonFlow(): MutableCommonFlow<T> {
    return MutableCommonFlow(this)
}

fun <I, T> MutableCommonFlow<T>.map(block: (T) -> I): CommonFlow<I> {
    return (this as MutableStateFlow<T>).map(block).asCommonFlow()
}

fun <T> MutableCommonFlow<T>.asCommonFlow(): CommonFlow<T> {
    return (this as Flow<T>).asCommonFlow()
}