package com.example.kmmexample.viewmodel

import platform.UIKit.UIAction
import platform.UIKit.UITextField

fun UITextField.bindText(flow: MutableCommonFlow<String?>) {
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