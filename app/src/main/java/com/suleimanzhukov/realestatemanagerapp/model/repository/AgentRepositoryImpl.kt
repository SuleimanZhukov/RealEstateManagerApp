package com.suleimanzhukov.realestatemanagerapp.model.repository

import android.content.Context
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.AgentEntity
import com.suleimanzhukov.realestatemanagerapp.model.database.Databases
import javax.inject.Inject

class AgentRepositoryImpl @Inject constructor(
    private val context: Context
) : AgentRepository {

    override suspend fun addAgent(agent: AgentEntity): AgentEntity {
        Databases.getDatabase(context).agentDao().addAgent(agent)
        return agent
    }

    override suspend fun getAgentByEmail(email: String): AgentEntity? {
        return Databases.getDatabase(context).agentDao().getAgentByEmail(email)
    }

    override suspend fun getPasswordByEmail(email: String): String? {
        return Databases.getDatabase(context).agentDao().getPasswordByEmail(email)
    }

    override suspend fun updateAgent(agent: AgentEntity): AgentEntity {
        Databases.getDatabase(context).agentDao().updateAgent(agent)
        return agent
    }
}