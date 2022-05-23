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
import platform.UIKit.UILabel
import platform.UIKit.UITextField
import kotlin.coroutines.CoroutineContext

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
