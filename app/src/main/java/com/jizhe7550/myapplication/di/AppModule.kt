package com.jizhe7550.myapplication.di

import com.jizhe7550.myapplication.repository.CharmsRepository
import com.jizhe7550.myapplication.ui.pickcharm.PickCharmsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { PickCharmsViewModel(get()) }
}

val repositoryModule = module {
    single { CharmsRepository() }
}

val appModule = listOf(viewModelModule, repositoryModule)