package com.suleimanzhukov.realestatemanagerapp.di

import com.suleimanzhukov.realestatemanagerapp.RealEstateApplication
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        AuthFragmentsModule::class
    ]
)
interface RealEstateComponent : AndroidInjector<RealEstateApplication> {
    override fun inject(application: RealEstateApplication)
}