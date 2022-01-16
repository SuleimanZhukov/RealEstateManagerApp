package com.suleimanzhukov.realestatemanagerapp.model.repository

import android.content.Context
import com.suleimanzhukov.realestatemanagerapp.model.database.AgentEntity
import com.suleimanzhukov.realestatemanagerapp.model.database.Databases
import com.suleimanzhukov.realestatemanagerapp.model.utils.Agent

class AgentRepositoryImpl(
    private val context: Context
) : AgentRepository {

    override suspend fun addAgent(agent: AgentEntity): AgentEntity {
        Databases.getDatabase(context).agentDao().addAgent(agent)
        return agent
    }

    override suspend fun getAgentByEmail(email: String, context: Context): AgentEntity? {
        return Databases.getDatabase(context).agentDao().getAgentByEmail(email)
    }

    override suspend fun getPasswordByEmail(email: String, context: Context): String? {
        return Databases.getDatabase(context).agentDao().getPasswordByEmail(email)
    }

    override suspend fun updateAgent(agent: AgentEntity, context: Context): AgentEntity {
        Databases.getDatabase(context).agentDao().updateAgent(agent)
        return agent
    }

    private fun convertToAgent(agentEntity: AgentEntity?): Agent {
        return com.suleimanzhukov.realestatemanagerapp.model.utils.Agent(
            agentEntity!!.username,
            agentEntity.age,
            agentEntity.email,
            agentEntity.password,
            agentEntity.phone,
            agentEntity.profileImg
        )
    }

    private fun convertToAgentEntity(agent: Agent): AgentEntity {
        return AgentEntity(
            0,
            agent.username,
            agent.age,
            agent.email,
            agent.password,
            agent.phone,
            agent.profileImg
        )
    }
}