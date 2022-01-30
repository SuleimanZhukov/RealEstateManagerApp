package com.suleimanzhukov.realestatemanagerapp.di

import com.suleimanzhukov.realestatemanagerapp.framework.ui.auth.AccountAgentFragment
import com.suleimanzhukov.realestatemanagerapp.framework.ui.auth.AuthFragment
import com.suleimanzhukov.realestatemanagerapp.framework.ui.auth.PublishFragment
import com.suleimanzhukov.realestatemanagerapp.framework.ui.auth.SignUpFragment
import com.suleimanzhukov.realestatemanagerapp.framework.ui.main.MainFragment
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class
    ]
)
interface RealEstateComponent {

    fun getForMainFragment(mainFragment: MainFragment)
    fun getForAuthFragment(authFragment: AuthFragment)
    fun getForSignUpFragment(signUpFragment: SignUpFragment)
    fun getForAccountFragment(accountAgentFragment: AccountAgentFragment)
    fun getForPublishFragment(publishFragment: PublishFragment)
}