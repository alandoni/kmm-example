package com.example.kmmexample.databinding

import com.example.kmmexample.Image
import com.example.kmmexample.viewmodel.CommonFlow
import platform.UIKit.UIImageView
import platform.UIKit.UIView
import platform.UIKit.hidden

fun UIView.bindVisibility(flow: CommonFlow<Boolean>) {
    flow.collect {
        this.hidden = it
    }
}

fun UIView.bindEnable(flow: CommonFlow<Boolean>) {
    flow.collect {
        this.setUserInteractionEnabled(it)
    }
}

fun UIImageView.bindImage(flow: CommonFlow<Image?>) {
    flow.collect {
        this.image = it?.getImage()
    }
}