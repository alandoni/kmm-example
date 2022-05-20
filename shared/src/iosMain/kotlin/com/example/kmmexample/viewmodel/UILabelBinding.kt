package com.example.kmmexample.viewmodel

import platform.UIKit.UILabel

fun UILabel.bindText(flow: CommonFlow<String?>) {
    flow.watch {
        this.text = it
    }
}