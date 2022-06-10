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
import com.example.kmmexample.data.models.Links
import com.example.kmmexample.data.models.Rocket
import com.example.kmmexample.data.models.RocketLaunch
//import com.example.kmmexample.di.KoinInit
//import com.example.kmmexample.viewmodel.MainViewModel
//import com.example.kmmexample.viewmodel.MainViewModelState

@Composable
fun mainScreen() {
    //KoinInit.start()

    list()
//}
}

@Composable
fun list() {
    //val viewModel = remember { MainViewModel() }

    //val text by viewModel.text.collectAsState()
    //val state by viewModel.state.collectAsState(initial = MainViewModelState.Loading)

    LaunchedEffect(true) {
        //viewModel.getRocketLaunches()
    }

    var text = remember { "text" }

    Column(
        Modifier.fillMaxSize()
    ) {
        // BasicTextField still depends on Scroll, which is not implemented yet
        // for Web/iOS/MacOs
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
//            state
            listOf(
                RocketLaunch(
                    1,
                    "A",
                    2020,
                    "",
                    Rocket(
                        "1",
                        "Rocket 1",
                        ""
                    ),
                    "",
                    true,
                    Links("", "")
                ),
                RocketLaunch(
                    1,
                    "B",
                    2021,
                    "",
                    Rocket(
                        "2",
                        "Rocket 2",
                        ""
                    ),
                    "",
                    true,
                    Links("", "")
                )
            )
        )
    }
}

/*@Composable
fun rocketsList(
    state: MainViewModelState
) {

    when (state) {
        is MainViewModelState.Loading ->
            Text("Loading")
        is MainViewModelState.Success -> {
            rocketsList(state.value)
        }
        is MainViewModelState.Error -> {
            Text(state.error.message ?: "Error")
        }
    }
}*/

@Composable
fun rocketsList(list: List<RocketLaunch>) {
    Column {
//                items(value, key = { it }) {
//                    rocketLaunchRow(
//                        it
//                    )
//                }
        list.forEach {
            rocketLaunchRow(it)
        }
    }
}

@Composable
fun rocketLaunchRow(
    rocketLaunch: RocketLaunch
) {
    Text(
        rocketLaunch.missionName
    )
}
