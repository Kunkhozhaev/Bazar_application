package com.example.bazar.di

import com.example.bazar.data.helper.ProductHelper
import com.example.bazar.ui.main.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { ProductHelper(get())}
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}