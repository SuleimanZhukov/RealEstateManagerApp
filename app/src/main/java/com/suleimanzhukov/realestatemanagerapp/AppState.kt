package com.suleimanzhukov.realestatemanagerapp

import com.suleimanzhukov.realestatemanagerapp.model.utils.Agent


sealed class AppState {
    data class registerAgent(val agent: Agent) : AppState()

    data class getAgentByEmail(val agent: Agent) : AppState()
    data class getPasswordByEmail(val pass: String) : AppState()
}