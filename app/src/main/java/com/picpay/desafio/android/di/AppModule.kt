package com.picpay.desafio.android.di

import com.picpay.desafio.android.viewmodel.ContactViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { ContactViewModel(get()) }
}