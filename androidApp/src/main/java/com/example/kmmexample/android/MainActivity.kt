package com.example.kmmexample.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kmmexample.viewmodel.MainViewModel
import com.example.kmmexample.viewmodel.MainViewModelState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = RocketLaunchesAdapter()
        findViewById<RecyclerView>(R.id.list).also {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = adapter
        }

        val progress = findViewById<View>(R.id.progress)
        val error = findViewById<TextView>(R.id.error)
        viewModel.state.observe(this) { state ->
            when (state) {
                is MainViewModelState.Loading -> {
                    progress.visibility = View.VISIBLE
                    error.visibility = View.GONE
                }
                is MainViewModelState.Error -> {
                    error.visibility = View.VISIBLE
                    progress.visibility = View.GONE
                }
                is MainViewModelState.Success -> {
                    adapter.items = state.value
                    error.visibility = View.VISIBLE
                    progress.visibility = View.GONE
                }
            }
        }

        viewModel.getRocketLaunches()
    }
}

fun <T> Flow<T>.observe(
    lifecycleOwner: LifecycleOwner,
    block: suspend (it: T) -> Unit
) {
    lifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
        this@observe.collect {
            block(it)
        }
    }
}

