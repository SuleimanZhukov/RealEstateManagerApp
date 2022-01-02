package com.suleimanzhukov.realestatemanagerapp.framework.ui.auth

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.suleimanzhukov.realestatemanagerapp.AppState
import com.suleimanzhukov.realestatemanagerapp.model.repository.AgentRepositoryImpl
import com.suleimanzhukov.realestatemanagerapp.model.utils.Agent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AuthViewModel() : ViewModel() {

    private val repository: AgentRepositoryImpl = AgentRepositoryImpl()

    private val signUpLiveData: MutableLiveData<AppState> = MutableLiveData()
    private val passwordLiveData: MutableLiveData<String?> = MutableLiveData()
    private val agentLiveData: MutableLiveData<Agent?> = MutableLiveData()

    fun getSignUpLiveData() = signUpLiveData
    fun getPasswordLiveData() = passwordLiveData
    fun getAgentLiveData() = agentLiveData

    fun registerAgent(agent: Agent, context: Context) {
        Thread {
            signUpLiveData.postValue(AppState.registerAgent(repository.addAgent(agent, context)))
        }.start()
    }

    fun getAgentByEmail(email: String, context: Context) {
        agentLiveData.postValue(repository.getAgentByEmail(email, context))
    }

    fun getPasswordByEmail(email: String, context: Context) {
        passwordLiveData.postValue(repository.getPasswordByEmail(email, context))
    }
}