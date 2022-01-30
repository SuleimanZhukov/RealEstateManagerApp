package com.suleimanzhukov.realestatemanagerapp.di

import android.content.Context
import androidx.core.content.contentValuesOf
import com.suleimanzhukov.realestatemanagerapp.framework.ui.auth.AuthViewModel
import com.suleimanzhukov.realestatemanagerapp.framework.ui.main.MainViewModel
import com.suleimanzhukov.realestatemanagerapp.model.repository.AgentRepository
import com.suleimanzhukov.realestatemanagerapp.model.repository.AgentRepositoryImpl
import com.suleimanzhukov.realestatemanagerapp.model.repository.PropertyRepository
import com.suleimanzhukov.realestatemanagerapp.model.repository.PropertyRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class AppModule() {

    @Singleton
    @Provides
    fun providesAgentRepositoryImpl(): AgentRepository = AgentRepositoryImpl()

    @Singleton
    @Provides
    fun providesPropertyRepositoryImpl(): PropertyRepository = PropertyRepositoryImpl()

    @Singleton
    @Provides
    fun providesMainViewModel(): MainViewModel = MainViewModel(providesAgentRepositoryImpl(), providesPropertyRepositoryImpl())

    @Singleton
    @Provides
    fun providesAuthViewModel(): AuthViewModel = AuthViewModel(providesAgentRepositoryImpl(), providesPropertyRepositoryImpl())
}