package com.example.kmmexample.viewmodel

import platform.UIKit.UITextField

fun UITextField.bindText(flow: CommonFlow<String?>) {
    flow.watch {
        this.text = it
    }
}
