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
    private val loginLiveData: MutableLiveData<AppState> = MutableLiveData()

    fun getSignUpLiveData() = signUpLiveData

    fun registerAgent(agent: Agent, context: Context) {
        Thread {
            signUpLiveData.postValue(AppState.registerAgent(repository.addAgent(agent, context)))
        }.start()
    }

    fun getAgentByEmail(email: String, context: Context): Agent {
        lateinit var agent: Agent
        Thread {
            agent = repository.getAgentByEmail(email, context)
            signUpLiveData.postValue(AppState.getAgentByEmail(agent))
        }.start()
        return agent
    }

    fun getPasswordByEmail(email: String, context: Context): String {
        lateinit var password: String
        Thread {
            password = repository.getAgentByEmail(email, context).password
            signUpLiveData.postValue(AppState.getAgentByEmail(repository.getAgentByEmail(email, context)))
        }.start()
        return password
    }

}