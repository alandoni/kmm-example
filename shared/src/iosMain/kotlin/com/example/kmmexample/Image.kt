package com.example.kmmexample

import platform.UIKit.UIImage

actual class Image(private val image: UIImage) {
    fun getImage(): UIImage {
        return image
    }
}