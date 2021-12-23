package com.suleimanzhukov.realestatemanagerapp.model.database

import androidx.room.*
import androidx.room.OnConflictStrategy.ABORT
import com.suleimanzhukov.realestatemanagerapp.model.utils.Agent

@Dao
interface AgentDao {

    @Insert(onConflict = ABORT)
    fun addAgent(agent: AgentEntity): Agent

    @Query("SELECT * FROM AgentEntity")
    fun getAllAgents(): List<Agent>

    @Query("SELECT * FROM AgentEntity WHERE id = :id")
    fun getAgentById(id: Long): Agent

    @Update
    fun updateAgent(agent: Agent)

    @Query("DELETE FROM AgentEntity WHERE id = :id")
    fun deleteAgentById(id: Long)
}