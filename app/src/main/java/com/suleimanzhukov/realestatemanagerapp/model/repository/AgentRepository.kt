package com.suleimanzhukov.realestatemanagerapp.model.repository

import android.content.Context
import com.suleimanzhukov.realestatemanagerapp.model.utils.Agent

interface AgentRepository {

    fun addAgent(agent: Agent, context: Context): Agent
    fun getAgentByEmail(email: String, context: Context): Agent

}