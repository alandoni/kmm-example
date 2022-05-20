package com.example.kmmexample.viewmodel

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import platform.Foundation.NSRunLoop
import platform.Foundation.performBlock
import platform.UIKit.UIAction
import platform.UIKit.UITextField
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

actual open class CommonFlow<T>
actual constructor(private val origin: Flow<T>) : Flow<T> by origin {

    actual fun watch(block: (T) -> Unit): Cancellable {
        val job = defaultScope.launch {
            collect {
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

    actual fun watch(block: (T) -> Unit): Cancellable {
        val job = defaultScope.launch {
            collect {
                block(it)
            }
        }

        return CancellableImpl(job)
    }
}

fun UITextField.bind(flow: MutableCommonFlow<String?>) {
    flow.watch {
        this.text = it
    }
    this.addAction(
        UIAction.actionWithHandler {
            flow.value = this.text
        },
        1 shl 17 //editingChanged
    )
}