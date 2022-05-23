package com.example.kmmexample.viewmodel

import kotlinx.coroutines.*

expect open class ViewModel() {
    val scope: CoroutineScope
    fun coroutine(block: suspend () -> Unit): Cancellable
}
