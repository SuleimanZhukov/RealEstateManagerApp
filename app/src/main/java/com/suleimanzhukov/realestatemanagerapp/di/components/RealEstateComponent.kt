package com.suleimanzhukov.realestatemanagerapp.di.components

import com.suleimanzhukov.realestatemanagerapp.di.modules.ContextModule
import com.suleimanzhukov.realestatemanagerapp.di.modules.ReposModule
import com.suleimanzhukov.realestatemanagerapp.framework.ui.DetailsFragment
import com.suleimanzhukov.realestatemanagerapp.framework.ui.auth.AccountAgentFragment
import com.suleimanzhukov.realestatemanagerapp.framework.ui.auth.AuthFragment
import com.suleimanzhukov.realestatemanagerapp.framework.ui.auth.PublishFragment
import com.suleimanzhukov.realestatemanagerapp.framework.ui.auth.SignUpFragment
import com.suleimanzhukov.realestatemanagerapp.framework.ui.main.MainFragment
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        ReposModule::class,
        ContextModule::class
    ]
)
@Singleton
interface RealEstateComponent {

    fun inject(mainFragment: MainFragment)

    fun inject(authFragment: AuthFragment)

    fun inject(signUpFragment: SignUpFragment)

    fun inject(accountAgentFragment: AccountAgentFragment)

    fun inject(publishFragment: PublishFragment)

    fun inject(detailsFragment: DetailsFragment)
}