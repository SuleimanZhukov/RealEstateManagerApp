package com.suleimanzhukov.realestatemanagerapp.model.repository

import android.content.Context
import com.suleimanzhukov.realestatemanagerapp.model.database.AgentEntity
import com.suleimanzhukov.realestatemanagerapp.model.database.Databases
import com.suleimanzhukov.realestatemanagerapp.model.utils.Agent

class AgentRepositoryImpl() : AgentRepository {

    override fun addAgent(agent: Agent, context: Context): Agent {
        Databases.getDatabase(context).agentDao().addAgent(convertToAgentEntity(agent))
        return agent
    }

    override fun getAgentByEmail(email: String, context: Context): Agent {
        return Databases.getDatabase(context).agentDao().getAgentByEmail(email)
    }

    private fun convertToAgent(agentEntity: AgentEntity): Agent {
        return Agent(
            agentEntity.username,
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