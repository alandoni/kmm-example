package com.example.kmmexample.ios

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Application
import com.example.kmmexample.di.KoinInit
import com.example.kmmexample.mainScreen
import kotlinx.cinterop.*
import platform.Foundation.NSStringFromClass
import platform.UIKit.*

fun main(args: Array<String>) {
    KoinInit.start()

    memScoped {
        val argc = args.size + 1
        val argv = (arrayOf("skikoApp") + args).map { it.cstr.ptr }.toCValues()
        autoreleasepool {
            UIApplicationMain(argc, argv, null, NSStringFromClass(SkikoAppDelegate))
        }
    }
}

class SkikoAppDelegate @ObjCObjectBase.OverrideInit constructor() : UIResponder(), UIApplicationDelegateProtocol {
    companion object : UIResponderMeta(), UIApplicationDelegateProtocolMeta

    private var _window: UIWindow? = null
    override fun window() = _window
    override fun setWindow(window: UIWindow?) {
        _window = window
    }

    override fun application(
        application: UIApplication,
        didFinishLaunchingWithOptions: Map<Any?, *>?
    ): Boolean {
        window = UIWindow(frame = UIScreen.mainScreen.bounds)
        window?.rootViewController = Application("KmmExample") {
            Column {
                // To skip upper part of screen.
                Box(modifier = Modifier.height(48.dp))
                mainScreen()
            }
        }
        window?.makeKeyAndVisible()
        return true
    }
}
