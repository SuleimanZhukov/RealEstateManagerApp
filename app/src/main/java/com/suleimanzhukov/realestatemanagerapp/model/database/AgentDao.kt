package com.suleimanzhukov.realestatemanagerapp.model.database

import androidx.room.*
import androidx.room.OnConflictStrategy.ABORT
import com.suleimanzhukov.realestatemanagerapp.model.utils.Agent

@Dao
interface AgentDao {

    @Insert(onConflict = ABORT)
    fun addAgent(agent: AgentEntity)

    @Query("SELECT name FROM AgentEntity")
    fun getAllAgents(): List<String>

    @Query("SELECT * FROM AgentEntity WHERE id = :id")
    fun getAgentById(id: Long): Agent

    @Query("SELECT * FROM AgentEntity WHERE email = :email")
    fun getAgentByEmail(email: String): Agent

    @Update
    fun updateAgent(agentEntity: AgentEntity)

    @Query("DELETE FROM AgentEntity WHERE id = :id")
    fun deleteAgentById(id: Long)
}