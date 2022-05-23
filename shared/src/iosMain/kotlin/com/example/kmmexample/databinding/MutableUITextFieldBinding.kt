package com.example.kmmexample.databinding

import com.example.kmmexample.viewmodel.MutableCommonFlow
import platform.UIKit.UIAction
import platform.UIKit.UITextField

fun UITextField.bindText(flow: MutableCommonFlow<String?>) {
    this.text = flow.value
    flow.collect {
        this.text = it
    }
    this.addAction(
        UIAction.actionWithHandler {
            flow.value = this.text
        },
        1 shl 17 //editingChanged
    )
}