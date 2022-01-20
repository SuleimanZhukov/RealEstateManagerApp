package com.suleimanzhukov.realestatemanagerapp.di

import com.suleimanzhukov.realestatemanagerapp.framework.ui.main.MainFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class
    ]
)
interface RealEstateComponent {

    fun inject(mainFragment: MainFragment)
}