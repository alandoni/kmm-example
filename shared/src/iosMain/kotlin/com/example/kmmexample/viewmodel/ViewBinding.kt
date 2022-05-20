package com.example.kmmexample.viewmodel

import com.example.kmmexample.Image
import platform.UIKit.UIImageView
import platform.UIKit.UIView
import platform.UIKit.hidden

fun UIView.bindVisibility(flow: CommonFlow<Boolean>) {
    flow.watch {
        this.hidden = it
    }
}

fun UIView.bindEnable(flow: CommonFlow<Boolean>) {
    flow.watch {
        this.setUserInteractionEnabled(it)
    }
}

fun UIImageView.bindImage(flow: CommonFlow<Image?>) {
    flow.watch {
        this.image = it?.getImage()
    }
}