package com.suleimanzhukov.realestatemanagerapp

import com.suleimanzhukov.realestatemanagerapp.model.database.AgentEntity


sealed class AppState {
    data class registerAgent(val agent: AgentEntity) : AppState()

    data class getAgentByEmail(val agent: AgentEntity?) : AppState()
    data class updateAgent(val agent: AgentEntity?) : AppState()
    data class getPasswordByEmail(val pass: String) : AppState()
}