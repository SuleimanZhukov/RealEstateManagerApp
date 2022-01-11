package com.suleimanzhukov.realestatemanagerapp.model.repository

import android.content.Context
import com.suleimanzhukov.realestatemanagerapp.model.utils.Agent

interface AgentRepository {

    suspend fun addAgent(agent: Agent, context: Context): Agent
    suspend fun getAgentByEmail(email: String, context: Context): Agent?
    suspend fun getPasswordByEmail(email: String, context: Context): String?
    suspend fun updateAgent(agent: Agent, context: Context): Agent
}