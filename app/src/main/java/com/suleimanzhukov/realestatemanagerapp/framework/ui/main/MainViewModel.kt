package com.suleimanzhukov.realestatemanagerapp.framework.ui.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.suleimanzhukov.realestatemanagerapp.model.database.AgentEntity
import com.suleimanzhukov.realestatemanagerapp.model.repository.AgentRepositoryImpl
import javax.inject.Inject

class MainViewModel(
    @Inject val repository: AgentRepositoryImpl
) : ViewModel() {

    private val agentLiveData: MutableLiveData<AgentEntity?> = MutableLiveData()

    fun getAgentLiveData() = agentLiveData

    suspend fun getAgentByEmail(email: String, context: Context) {
        agentLiveData.postValue(repository.getAgentByEmail(email, context))
    }
}