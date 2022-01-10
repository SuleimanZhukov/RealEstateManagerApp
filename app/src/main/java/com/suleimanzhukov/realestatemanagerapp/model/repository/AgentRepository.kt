package com.suleimanzhukov.realestatemanagerapp.model.repository

import android.content.Context
import com.suleimanzhukov.realestatemanagerapp.model.utils.Agent

interface AgentRepository {

    fun addAgent(agent: Agent, context: Context): Agent
    fun getAgentByEmail(email: String, context: Context): Agent?
    fun getPasswordByEmail(email: String, context: Context): String?
    fun updataAgent(agent: Agent, context: Context): Agent
}