package com.example.kmmexample.databinding

import com.example.kmmexample.viewmodel.CommonFlow
import platform.AppKit.NSTextView

fun NSTextView.bindText(flow: CommonFlow<String?>) {
    flow.collect {
        this.string = it ?: ""
    }
}