package com.suleimanzhukov.realestatemanagerapp

import com.suleimanzhukov.realestatemanagerapp.model.utils.Agent


sealed class AppState {
    data class registerAgent(val agent: Agent) : AppState()
}