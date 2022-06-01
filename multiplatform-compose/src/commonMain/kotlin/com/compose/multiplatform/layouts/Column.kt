package com.compose.multiplatform.layouts

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.ColumnScope

@Composable
expect fun Column(modifier: androidx.compose.ui.Modifier,
                  content: @Composable (ColumnScope.() -> kotlin.Unit))