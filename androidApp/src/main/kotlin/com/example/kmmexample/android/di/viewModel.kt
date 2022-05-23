package com.example.kmmexample.android.di

import com.example.kmmexample.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModel = module {
    viewModel { MainViewModel() }
}