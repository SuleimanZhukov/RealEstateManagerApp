package com.suleimanzhukov.realestatemanagerapp.framework.ui.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.suleimanzhukov.realestatemanagerapp.AppState
import com.suleimanzhukov.realestatemanagerapp.model.repository.AgentRepositoryImpl
import com.suleimanzhukov.realestatemanagerapp.model.utils.Agent

class SignUpViewModel(
    private val repository: AgentRepositoryImpl
) : ViewModel() {

    private val signUpLiveData: MutableLiveData<AppState> = MutableLiveData()

    fun getSignUpLiveData() = signUpLiveData

    fun registerAgent(agent: Agent) {
        Thread {
            signUpLiveData.postValue(AppState.registerAgent(repository.addAgent(agent)))
        }.start()
    }

}