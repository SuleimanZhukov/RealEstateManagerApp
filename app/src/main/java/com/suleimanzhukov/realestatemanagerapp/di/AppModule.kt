package com.suleimanzhukov.realestatemanagerapp.di

import android.content.Context
import com.suleimanzhukov.realestatemanagerapp.framework.ui.auth.AuthViewModel
import com.suleimanzhukov.realestatemanagerapp.framework.ui.main.MainViewModel
import com.suleimanzhukov.realestatemanagerapp.model.repository.AgentRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class AppModule(
    private val context: Context
) {

    @Singleton
    @Provides
    fun provideAuthViewModel(): AuthViewModel = AuthViewModel(AgentRepositoryImpl(context))

    @Singleton
    @Provides
    fun provideMainViewModel(): MainViewModel = MainViewModel(AgentRepositoryImpl(context))
}