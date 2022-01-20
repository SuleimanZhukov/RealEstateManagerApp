package com.suleimanzhukov.realestatemanagerapp.di

import android.content.Context
import androidx.core.content.contentValuesOf
import com.suleimanzhukov.realestatemanagerapp.framework.ui.auth.AuthViewModel
import com.suleimanzhukov.realestatemanagerapp.framework.ui.main.MainViewModel
import com.suleimanzhukov.realestatemanagerapp.model.repository.AgentRepository
import com.suleimanzhukov.realestatemanagerapp.model.repository.AgentRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class AppModule() {

    @Singleton
    @Provides
    fun getMainViewModel(): MainViewModel = MainViewModel()
}