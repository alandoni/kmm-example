package com.example.kmmexample.databinding

import com.example.kmmexample.Image
import com.example.kmmexample.viewmodel.CommonFlow
import platform.AppKit.NSImage
import platform.AppKit.NSView

fun NSView.bindVisibility(flow: CommonFlow<Boolean>) {
    flow.collect {
        this.hidden = it
    }
}

//fun NSView.bindEnable(flow: CommonFlow<Boolean>) {
//    flow.collect {
//        this.hitTest(it)
//    }
//}
//
//fun NSImage.bindImage(flow: CommonFlow<Image?>) {
//    flow.collect {
//        this.image = it?.getImage()
//    }
//}