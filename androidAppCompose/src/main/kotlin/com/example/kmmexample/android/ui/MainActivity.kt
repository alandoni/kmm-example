package com.example.kmmexample.android.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.kmmexample.android.ui.theme.KmmexampleTheme
import com.example.kmmexample.data.models.Links
import com.example.kmmexample.data.models.Rocket
import com.example.kmmexample.data.models.RocketLaunch
import com.example.kmmexample.viewmodel.MainViewModel
import com.example.kmmexample.viewmodel.MainViewModelState
import org.koin.androidx.viewmodel.ext.android.getViewModelFactory
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KmmexampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    List()
                }
            }
        }
    }
}

@Composable
fun List() {
    val viewModel = remember { MainViewModel() }
    val text by viewModel.text.collectAsState()
    val state by viewModel.state.collectAsState(initial = MainViewModelState.Loading)

    LaunchedEffect(true) {
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
            CircularProgressIndicator()
        is MainViewModelState.Success ->
            LazyColumn {
                items(state.value, key = { it.missionName }) { rocketLaunch ->
                    RocketLaunchRow(rocketLaunch)
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KmmexampleTheme {
        List()
    }
}