package com.suleimanzhukov.realestatemanagerapp.framework.ui.auth

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.suleimanzhukov.realestatemanagerapp.AppState
import com.suleimanzhukov.realestatemanagerapp.model.repository.AgentRepositoryImpl
import com.suleimanzhukov.realestatemanagerapp.model.utils.Agent

class AuthViewModel() : ViewModel() {

    private val repository: AgentRepositoryImpl = AgentRepositoryImpl()
    private val signUpLiveData: MutableLiveData<AppState> = MutableLiveData()

    fun getSignUpLiveData() = signUpLiveData

    fun registerAgent(agent: Agent, context: Context) {
        Thread {
            signUpLiveData.postValue(AppState.registerAgent(repository.addAgent(agent, context)))
        }.start()
    }

    fun logoutAgentByEmail(email: String, context: Context) {
        Thread {
            signUpLiveData.postValue(AppState.logoutAgentByEmail(repository.getAgentByEmail(email, context)))
        }
    }

}