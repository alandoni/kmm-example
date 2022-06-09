package com.example.kmmexample

import platform.AppKit.NSImage

actual class Image(private val image: NSImage) {
    fun getImage(): NSImage {
        return image
    }
}