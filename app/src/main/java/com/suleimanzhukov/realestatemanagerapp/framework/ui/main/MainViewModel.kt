package com.suleimanzhukov.realestatemanagerapp.framework.ui.main

import android.content.Context
import android.content.pm.PackageManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.AgentEntity
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.PropertyEntity
import com.suleimanzhukov.realestatemanagerapp.model.repository.AgentRepository
import com.suleimanzhukov.realestatemanagerapp.model.repository.AgentRepositoryImpl
import com.suleimanzhukov.realestatemanagerapp.model.repository.PropertyRepository
import com.suleimanzhukov.realestatemanagerapp.model.repository.PropertyRepositoryImpl
import javax.inject.Inject

class MainViewModel(
    @JvmField @Inject public var repository: AgentRepository,
    @JvmField @Inject public var propertyRepository: PropertyRepository
) : ViewModel() {

//    private val repository: AgentRepository = AgentRepositoryImpl()
//    private val propertyRepository: PropertyRepository = PropertyRepositoryImpl()

    private val agentLiveData: MutableLiveData<AgentEntity?> = MutableLiveData()
    private val propertyLiveData: MutableLiveData<List<PropertyEntity?>> = MutableLiveData()

    fun getAgentLiveData() = agentLiveData
    fun getPropertyLiveData() = propertyLiveData

    suspend fun getAgentByEmail(email: String, context: Context) {
        agentLiveData.postValue(repository.getAgentByEmail(email, context))
    }

    suspend fun getAllProperties(context: Context) {
        propertyLiveData.postValue(propertyRepository.getAllProperties(context))
    }
}