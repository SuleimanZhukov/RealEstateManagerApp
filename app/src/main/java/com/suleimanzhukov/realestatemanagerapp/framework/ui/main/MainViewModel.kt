package com.suleimanzhukov.realestatemanagerapp.framework.ui.main

import android.content.Context
import android.content.pm.PackageManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.AgentEntity
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.PropertyEntity
import com.suleimanzhukov.realestatemanagerapp.model.repository.*
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: AgentRepository,
    private val propertyRepository: PropertyRepository,
    private val pictureRepository: PictureRepository
) : ViewModel() {

//    private val repository: AgentRepository = AgentRepositoryImpl()
//    private val propertyRepository: PropertyRepository = PropertyRepositoryImpl()

    private val agentLiveData: MutableLiveData<AgentEntity?> = MutableLiveData()
    private val propertyLiveData: MutableLiveData<List<PropertyEntity?>> = MutableLiveData()

    fun getAgentLiveData() = agentLiveData
    fun getPropertyLiveData() = propertyLiveData

    suspend fun getAgentByEmail(email: String) {
        agentLiveData.postValue(repository.getAgentByEmail(email))
    }

    suspend fun getAllProperties() {
        propertyLiveData.postValue(propertyRepository.getAllProperties())
    }
}