package com.suleimanzhukov.realestatemanagerapp

import android.app.AppComponentFactory
import android.app.Application
import com.suleimanzhukov.realestatemanagerapp.di.RealEstateComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class RealEstateApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var mainInject: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        
    }

    override fun androidInjector(): AndroidInjector<Any> = mainInject
}