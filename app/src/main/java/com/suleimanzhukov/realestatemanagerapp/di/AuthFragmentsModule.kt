package com.suleimanzhukov.realestatemanagerapp.di

import com.suleimanzhukov.realestatemanagerapp.framework.ui.auth.AccountAgentFragment
import com.suleimanzhukov.realestatemanagerapp.framework.ui.auth.AuthFragment
import com.suleimanzhukov.realestatemanagerapp.framework.ui.auth.SignUpFragment
import com.suleimanzhukov.realestatemanagerapp.framework.ui.main.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AuthFragmentsModule {

    @ContributesAndroidInjector
    abstract fun contributeAccountFragmentsInjector() : AccountAgentFragment

    @ContributesAndroidInjector
    abstract fun contributeAuthFragmentsInjector() : AuthFragment

    @ContributesAndroidInjector
    abstract fun contributeSignUpFragmentsInjector() : SignUpFragment

    @ContributesAndroidInjector
    abstract fun contributeMainFragmentsInjector() : MainFragment
}