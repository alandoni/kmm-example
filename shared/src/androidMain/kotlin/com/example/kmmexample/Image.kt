package com.example.kmmexample

import android.graphics.Bitmap

actual class Image(private val bitmap: Bitmap) {
    fun getImage(): Bitmap {
        return bitmap
    }
}