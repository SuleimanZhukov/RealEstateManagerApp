package com.suleimanzhukov.realestatemanagerapp.framework.ui.auth

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.AgentEntity
import com.suleimanzhukov.realestatemanagerapp.model.repository.AgentRepository
import com.suleimanzhukov.realestatemanagerapp.model.repository.AgentRepositoryImpl
import javax.inject.Inject

class AuthViewModel() : ViewModel() {

    private val repository: AgentRepository = AgentRepositoryImpl()

    private val signUpLiveData: MutableLiveData<AgentEntity?> = MutableLiveData()
    private val passwordLiveData: MutableLiveData<String?> = MutableLiveData()
    private val agentLiveData: MutableLiveData<AgentEntity?> = MutableLiveData()

    fun getSignUpLiveData() = signUpLiveData
    fun getPasswordLiveData() = passwordLiveData
    fun getAgentLiveData() = agentLiveData

    suspend fun registerAgent(agent: AgentEntity, context: Context) {
        signUpLiveData.postValue(repository.addAgent(agent, context))
    }

    suspend fun getAgentByEmail(email: String, context: Context) {
        agentLiveData.postValue(repository.getAgentByEmail(email, context))
    }

    suspend fun getPasswordByEmail(email: String, context: Context) {
        passwordLiveData.postValue(repository.getPasswordByEmail(email, context))
    }

    suspend fun updateAgent(agent: AgentEntity, context: Context) {
        agentLiveData.postValue(repository.updateAgent(agent, context))
    }
}