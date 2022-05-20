package com.example.kmmexample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

actual interface Cancellable {
    actual fun cancel()
}

actual class CancellableImpl actual constructor(private val job: Job): Cancellable {
    actual override fun cancel() {
        job.cancel()
    }
}

actual open class ViewModel: ViewModel() {
    actual val scope = viewModelScope
    actual fun coroutine(block: suspend () -> Unit): Cancellable {
        return CancellableImpl(
            scope.launch {
                block()
            }
        )
    }
}

actual open class CommonFlow<T> actual constructor(private val origin: Flow<T>) : Flow<T> by origin{
    actual fun watch(block: (T) -> Unit): Cancellable {
        val job = Job()

        this.onEach {
            block(it)
        }.launchIn(CoroutineScope(Dispatchers.Main) + job)

        return CancellableImpl(job)
    }
}