package com.suleimanzhukov.realestatemanagerapp

import android.app.Application
import com.suleimanzhukov.realestatemanagerapp.di.DaggerRealEstateComponent
import com.suleimanzhukov.realestatemanagerapp.di.modules.ContextModule

class RealEstateApplication : Application() {

    val appComponent by lazy {
        DaggerRealEstateComponent
            .builder()
            .contextModule(ContextModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        _instance = this
    }

    companion object {
        private var _instance: RealEstateApplication? = null
        val instance get() = _instance!!
    }

}