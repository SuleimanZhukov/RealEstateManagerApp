package com.suleimanzhukov.realestatemanagerapp.model.repository

import com.suleimanzhukov.realestatemanagerapp.model.database.AgentEntity
import com.suleimanzhukov.realestatemanagerapp.model.database.Databases
import com.suleimanzhukov.realestatemanagerapp.model.utils.Agent

class AgentRepositoryImpl() : AgentRepository {

    override fun addAgent(agent: Agent): Agent {
        Databases.INSTANCE!!.agentDao().addAgent(convertToAgentEntity(agent))
        return agent
    }

    override fun getAgentByEmail(email: String): Agent {
        return Databases.INSTANCE!!.agentDao().getAgentByEmail(email)
    }

    private fun convertToAgent(agentEntity: AgentEntity): Agent {
        return Agent(
            agentEntity.id,
            agentEntity.name,
            agentEntity.age,
            agentEntity.email,
            agentEntity.password,
            agentEntity.phone,
            agentEntity.isSignedIn,
            agentEntity.profileImg
        )
    }

    private fun convertToAgentEntity(agent: Agent): AgentEntity {
        return AgentEntity(
            0,
            agent.name,
            agent.age,
            agent.email,
            agent.password,
            agent.phone,
            agent.isSignedIn,
            agent.profileImg
        )
    }
}