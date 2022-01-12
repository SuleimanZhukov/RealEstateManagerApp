package com.suleimanzhukov.realestatemanagerapp

import com.suleimanzhukov.realestatemanagerapp.model.database.AgentEntity
import com.suleimanzhukov.realestatemanagerapp.model.utils.Agent


sealed class AppState {
    data class registerAgent(val agent: Agent) : AppState()

    data class getAgentByEmail(val agent: AgentEntity?) : AppState()
    data class updateAgent(val agent: Agent?) : AppState()
    data class getPasswordByEmail(val pass: String) : AppState()
}