package com.example.kmmexample.viewmodel

import kotlinx.coroutines.Job
import kotlin.js.JsExport

interface Cancellable {
    fun cancel()
}

class CancellableImpl(private val job: Job): Cancellable {
    override fun cancel() {
        job.cancel()
    }
}