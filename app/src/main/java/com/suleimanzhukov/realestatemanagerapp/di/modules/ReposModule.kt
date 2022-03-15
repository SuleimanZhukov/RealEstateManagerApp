package com.suleimanzhukov.realestatemanagerapp.di.modules

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
class ReposModule() {

    @Provides
    @Singleton
    fun providesAgentRepository(context: Context): AgentRepository = AgentRepositoryImpl(context)

    @Provides
    @Singleton
    fun providesPropertyRepository(context: Context): PropertyRepository = PropertyRepositoryImpl(context)

    @Provides
    @Singleton
    fun providesMainViewModel(agentRepository: AgentRepository, propertyRepository: PropertyRepository): MainViewModel {
        return MainViewModel(agentRepository, propertyRepository)
    }

    @Provides
    @Singleton
    fun providesAuthViewModel(agentRepository: AgentRepository, propertyRepository: PropertyRepository): AuthViewModel {
        return AuthViewModel(agentRepository, propertyRepository)
    }
}