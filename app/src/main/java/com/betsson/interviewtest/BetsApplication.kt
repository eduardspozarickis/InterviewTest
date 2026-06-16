package com.betsson.interviewtest

import android.app.Application
import com.betsson.interviewtest.core.di.dispatcherModule
import com.betsson.interviewtest.core.di.repositoryModule
import com.betsson.interviewtest.core.di.storeModule
import com.betsson.interviewtest.core.di.useCaseModule
import com.betsson.interviewtest.core.di.viewModelModule
import org.koin.core.context.GlobalContext.startKoin

class BetsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            modules(
                dispatcherModule,
                storeModule,
                repositoryModule,
                useCaseModule,
                viewModelModule,
            )
        }
    }
}