package com.example.kmmexample.viewmodel

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import platform.Foundation.NSRunLoop
import platform.Foundation.performBlock
import kotlin.coroutines.CoroutineContext

actual interface Cancellable {
    actual fun cancel()
}

actual class CancellableImpl actual constructor(private val job: Job): Cancellable {
    actual override fun cancel() {
        job.cancel()
    }
}

actual open class ViewModel {
    actual val scope = defaultScope

    actual fun coroutine(block: suspend () -> Unit): Cancellable {
        return CancellableImpl(
            scope.launch {
                block()
            }
        )
    }
}

val defaultScope: CoroutineScope = object : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = SupervisorJob() + NSLooperDispatcher
}

object NSLooperDispatcher: CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        NSRunLoop.mainRunLoop.performBlock {
            block.run()
        }
    }
}

actual open class CommonFlow<T> actual constructor(private val origin: Flow<T>) : Flow<T> by origin{

    actual fun watch(block: (T) -> Unit): Cancellable {
        val job = defaultScope.launch {
            collect {
                latestValue = it
                block(it)
            }
        }

        return CancellableImpl(job)
    }

    var latestValue: T? = null
}