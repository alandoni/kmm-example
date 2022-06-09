package com.example.kmmexample.common

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.kmmexample.data.models.RocketLaunch
import com.example.kmmexample.di.KoinInit
import com.example.kmmexample.viewmodel.MainViewModel
import com.example.kmmexample.viewmodel.MainViewModelState

@Composable
fun mainScreen() {
    KoinInit.start()

    list()
//}
}

@Composable
fun list() {
    val viewModel = remember { MainViewModel() }

    val text by viewModel.text.collectAsState()
    val state by viewModel.state.collectAsState(initial = MainViewModelState.Loading)

    LaunchedEffect(true) {
        viewModel.getRocketLaunches()
    }

    Column(
        Modifier.fillMaxSize()
    ) {
//        BasicTextField(
//            text,
//            onValueChange = { s: String ->
////                viewModel.text.value = s
//                text = s
//            },
//            modifier = Modifier.fillMaxWidth().background(Color.Green),
//        )
        Text(
            text = text.length.toString()
        )
        rocketsList(
            state
        )
    }
}

@Composable
fun rocketsList(
    state: MainViewModelState
) {

    when (state) {
        is MainViewModelState.Loading ->
            Text("Loading")
        is MainViewModelState.Success -> {
            Column {
//                items(value, key = { it }) {
//                    rocketLaunchRow(
//                        it
//                    )
//                }
                state.value.forEach {
                    rocketLaunchRow(it)
                }
            }
        }
        is MainViewModelState.Error -> {
            Text(state.error.message ?: "Error")
        }
    }
}

    //
@Composable
fun rocketLaunchRow(
    rocketLaunch: RocketLaunch
) {
    Text(
        rocketLaunch.missionName
    )
}
