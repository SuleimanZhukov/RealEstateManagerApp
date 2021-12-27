package com.suleimanzhukov.realestatemanagerapp.model.repository

import com.suleimanzhukov.realestatemanagerapp.model.database.AgentEntity
import com.suleimanzhukov.realestatemanagerapp.model.utils.Agent

interface AgentRepository {

    fun addAgent(agent: Agent): Agent
    fun getAgentByEmail(email: String): Agent

}