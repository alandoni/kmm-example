package com.example.kmmexample.databinding

import com.example.kmmexample.viewmodel.CommonFlow
import platform.UIKit.UILabel

fun UILabel.bindText(flow: CommonFlow<String?>) {
    flow.collect {
        this.text = it
    }
}