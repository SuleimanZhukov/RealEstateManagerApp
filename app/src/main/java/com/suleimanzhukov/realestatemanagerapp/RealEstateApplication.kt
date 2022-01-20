package com.suleimanzhukov.realestatemanagerapp

import android.app.AppComponentFactory
import android.app.Application
import android.content.res.Configuration
import com.suleimanzhukov.realestatemanagerapp.di.AppModule
import com.suleimanzhukov.realestatemanagerapp.di.DaggerRealEstateComponent
import com.suleimanzhukov.realestatemanagerapp.di.RealEstateComponent
import dagger.android.*
import javax.inject.Inject

class RealEstateApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var mainInject: DispatchingAndroidInjector<Any>

    /*override fun onCreate() {
        super.onCreate()
        DaggerRealEstateComponent.builder()
            .build()
            .inject(this)
    }*/

    override fun androidInjector(): AndroidInjector<Any> = mainInject
}