package com.example.kmmexample.databinding

import com.example.kmmexample.viewmodel.CommonFlow
import platform.UIKit.UITextField

fun UITextField.bindText(flow: CommonFlow<String?>) {
    flow.collect {
        this.text = it
    }
}
