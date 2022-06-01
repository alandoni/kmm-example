package com.compose.multiplatform.layouts

import androidx.compose.runtime.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.css.background
import org.jetbrains.compose.web.attributes.InputType

@Composable
actual fun EditText() =
    Input(
        type = InputType.Text,
        attrs = {
            onInput { event ->
                    viewModel.text.value = event.value
                }
            style {
                background("#00dd00")
            }
        }
    )