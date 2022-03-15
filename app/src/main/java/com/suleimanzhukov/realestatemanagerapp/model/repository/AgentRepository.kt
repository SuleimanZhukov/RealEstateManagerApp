package com.suleimanzhukov.realestatemanagerapp.model.repository

import android.content.Context
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.AgentEntity

interface AgentRepository {

    suspend fun addAgent(agent: AgentEntity): AgentEntity

    suspend fun getAgentByEmail(email: String): AgentEntity?

    suspend fun getPasswordByEmail(email: String): String?

    suspend fun updateAgent(agent: AgentEntity): AgentEntity
}