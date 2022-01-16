package com.suleimanzhukov.realestatemanagerapp.model.repository

import android.content.Context
import com.suleimanzhukov.realestatemanagerapp.model.database.AgentEntity

interface AgentRepository {

    suspend fun addAgent(agent: AgentEntity): AgentEntity
    suspend fun getAgentByEmail(email: String, context: Context): AgentEntity?
    suspend fun getPasswordByEmail(email: String, context: Context): String?
    suspend fun updateAgent(agent: AgentEntity, context: Context): AgentEntity
}