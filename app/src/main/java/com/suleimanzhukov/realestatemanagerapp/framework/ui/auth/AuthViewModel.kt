package com.suleimanzhukov.realestatemanagerapp.framework.ui.auth

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.AgentEntity
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.PropertyEntity
import com.suleimanzhukov.realestatemanagerapp.model.repository.AgentRepository
import com.suleimanzhukov.realestatemanagerapp.model.repository.AgentRepositoryImpl
import com.suleimanzhukov.realestatemanagerapp.model.repository.PropertyRepository
import com.suleimanzhukov.realestatemanagerapp.model.repository.PropertyRepositoryImpl
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val repository: AgentRepository,
    private val propertyRepository: PropertyRepository
) : ViewModel() {

//    private val repository: AgentRepository = AgentRepositoryImpl()
//    private val propertyRepository: PropertyRepository = PropertyRepositoryImpl()

    private val signUpLiveData: MutableLiveData<AgentEntity?> = MutableLiveData()
    private val passwordLiveData: MutableLiveData<String?> = MutableLiveData()
    private val agentLiveData: MutableLiveData<AgentEntity?> = MutableLiveData()

    private val propertyLiveData: MutableLiveData<PropertyEntity?> = MutableLiveData()
    private val propertiesLiveData: MutableLiveData<List<PropertyEntity?>> = MutableLiveData()

    fun getSignUpLiveData() = signUpLiveData
    fun getPasswordLiveData() = passwordLiveData
    fun getAgentLiveData() = agentLiveData
    fun getPropertiesLiveData() = propertiesLiveData

    suspend fun registerAgent(agent: AgentEntity) {
        signUpLiveData.postValue(repository.addAgent(agent))
    }

    suspend fun getAgentByEmail(email: String) {
        agentLiveData.postValue(repository.getAgentByEmail(email))
    }

    suspend fun getPasswordByEmail(email: String) {
        passwordLiveData.postValue(repository.getPasswordByEmail(email))
    }

    suspend fun updateAgent(agent: AgentEntity) {
        agentLiveData.postValue(repository.updateAgent(agent))
    }

    suspend fun addProperty(property: PropertyEntity) {
        propertyLiveData.postValue(propertyRepository.addProperty(property))
    }

    suspend fun getAllProperties(): List<PropertyEntity?> {
        return propertyRepository.getAllProperties()
    }

    suspend fun getAllPropertiesWithAgent(agentEmail: String?): List<PropertyEntity> {
        return propertyRepository.getAllPropertiesWithAgent(agentEmail)
    }

    suspend fun getPropertyById(id: Long): PropertyEntity? {
        return propertyRepository.getPropertyById(id)
    }
}