package com.compose.multiplatform.layouts

import androidx.compose.runtime.*

@Composable
actual fun Column(content: @Composable (ColumnScope.() -> kotlin.Unit)) =
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        Div {
            val placeables = measurables.map {
                measurable.measure(constraints)
            }
        }
    }