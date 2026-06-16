package com.betsson.interviewtest.core.di

import com.betsson.interviewtest.data.remote.RemoteStore
import com.betsson.interviewtest.domain.repository.BetsRepository
import com.betsson.interviewtest.domain.usecase.FetchBetsListUseCase
import com.betsson.interviewtest.presentation.home.HomeViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dispatcherModule = module {
    single { Dispatchers.IO }
}

val storeModule = module {
    single { RemoteStore() }
}

val repositoryModule = module {
    single { BetsRepository(get()) }
}

val useCaseModule = module {
    single { FetchBetsListUseCase(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
}