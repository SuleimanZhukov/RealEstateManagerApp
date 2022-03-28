package com.suleimanzhukov.realestatemanagerapp.di.modules

import android.content.Context
import com.suleimanzhukov.realestatemanagerapp.framework.ui.auth.AuthViewModel
import com.suleimanzhukov.realestatemanagerapp.framework.ui.main.MainViewModel
import com.suleimanzhukov.realestatemanagerapp.model.repository.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ReposModule {

    @Provides
    @Singleton
    fun providesAgentRepository(context: Context): AgentRepository = AgentRepositoryImpl(context)

    @Provides
    @Singleton
    fun providesPropertyRepository(context: Context): PropertyRepository = PropertyRepositoryImpl(context)

    @Provides
    @Singleton
    fun providesPictureRepository(context: Context): PictureRepository = PictureRepositoryImpl(context)

    @Provides
    @Singleton
    fun providesMainViewModel(
        agentRepository: AgentRepository,
        propertyRepository: PropertyRepository,
        pictureRepository: PictureRepository
    ): MainViewModel {
        return MainViewModel(agentRepository, propertyRepository, pictureRepository)
    }

    @Provides
    @Singleton
    fun providesAuthViewModel(
        agentRepository: AgentRepository,
        propertyRepository: PropertyRepository,
        pictureRepository: PictureRepository
    ): AuthViewModel {
        return AuthViewModel(agentRepository, propertyRepository, pictureRepository)
    }
}