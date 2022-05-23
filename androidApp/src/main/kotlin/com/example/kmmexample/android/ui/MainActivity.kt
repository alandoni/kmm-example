package com.example.kmmexample.android.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kmmexample.android.R
import com.example.kmmexample.android.databinding.ActivityMainBinding
import com.example.kmmexample.viewmodel.MainViewModel
import com.example.kmmexample.viewmodel.MainViewModelState
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setContentView(binding.root)

        val adapter = RocketLaunchesAdapter()
        binding.list.also {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = adapter
        }

        viewModel.state.observe(this) { state ->
            when (state) {
                is MainViewModelState.Loading -> {
                    binding.progress.visibility = View.VISIBLE
                    binding.error.visibility = View.GONE
                }
                is MainViewModelState.Error -> {
                    binding.error.visibility = View.VISIBLE
                    binding.progress.visibility = View.GONE
                }
                is MainViewModelState.Success -> {
                    adapter.items = state.value
                    binding.error.visibility = View.GONE
                    binding.progress.visibility = View.GONE
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

