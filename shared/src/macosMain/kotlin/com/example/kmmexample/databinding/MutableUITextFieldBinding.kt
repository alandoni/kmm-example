package com.example.kmmexample.databinding

import com.example.kmmexample.viewmodel.MutableCommonFlow
import platform.AppKit.NSTextField
import platform.AppKit.NSTextFieldDelegateProtocol
import platform.Foundation.NSNotification
import platform.darwin.NSObject

fun NSTextField.bindText(flow: MutableCommonFlow<String?>) {
    this.stringValue = flow.value ?: ""
    flow.collect {
        this.stringValue = it ?: ""
    }
    this.delegate = object : NSObject(), NSTextFieldDelegateProtocol {
        override fun controlTextDidChange(notification: NSNotification) {
            val textField = notification.`object` as? NSTextField
            flow.value = textField?.stringValue
        }
    }
}