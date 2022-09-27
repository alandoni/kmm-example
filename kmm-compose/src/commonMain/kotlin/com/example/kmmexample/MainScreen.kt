package com.example.kmmexample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.kmmexample.data.models.RocketLaunch
import com.example.kmmexample.viewmodel.MainViewModel
import com.example.kmmexample.viewmodel.MainViewModelState

@Composable
fun mainScreen() {
    val viewModel = remember { MainViewModel() }

    val text by viewModel.text.collectAsState()
    val state by viewModel.state.collectAsState(initial = MainViewModelState.Loading)

    LaunchedEffect(true) {
        print("Getting rocket launches")
        viewModel.getRocketLaunches()
    }

    Column(
        Modifier.fillMaxWidth()
    ) {
        BasicTextField(
            text,
            onValueChange = { s: String ->
                viewModel.text.value = s
            },
            modifier = Modifier.fillMaxWidth().background(Color.Green),
        )
        Text(text = text.length.toString())
        rocketsList(state)
    }
}

@Composable
fun rocketsList(state: MainViewModelState) {
    when (state) {
        is MainViewModelState.Loading ->
            Text("Loading")
        is MainViewModelState.Success -> {
            LazyColumn {
                items(state.value, key = { it.missionName }) { rocketLaunch ->
                    RocketLaunchRow(rocketLaunch)
                }
            }
        }
        is MainViewModelState.Error -> {
            Text(state.error.message ?: "Error")
        }
    }
}

@Composable
fun RocketLaunchRow(rocketLaunch: RocketLaunch) {
    Text(rocketLaunch.missionName)
}