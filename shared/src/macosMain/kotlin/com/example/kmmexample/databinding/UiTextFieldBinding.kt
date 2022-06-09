package com.example.kmmexample.databinding

import com.example.kmmexample.viewmodel.CommonFlow
import platform.AppKit.NSTextField

fun NSTextField.bindText(flow: CommonFlow<String?>) {
    flow.collect {
        this.stringValue = it ?: ""
    }
}
