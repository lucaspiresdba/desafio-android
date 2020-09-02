package com.picpay.desafio.android.di

import com.picpay.desafio.android.viewmodel.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainActivityViewModel(get()) }
}