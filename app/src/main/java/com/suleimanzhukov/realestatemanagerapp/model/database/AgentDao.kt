package com.suleimanzhukov.realestatemanagerapp.model.database

import androidx.room.*
import androidx.room.OnConflictStrategy.*
import com.suleimanzhukov.realestatemanagerapp.model.utils.Agent

@Dao
interface AgentDao {

    @Insert(onConflict = REPLACE)
    fun addAgent(agent: AgentEntity)

    @Query("SELECT username FROM AgentEntity")
    fun getAllAgents(): List<String>

    @Query("SELECT * FROM AgentEntity WHERE id = :id")
    fun getAgentById(id: Long): AgentEntity?

    @Query("SELECT * FROM AgentEntity WHERE email = :email")
    fun getAgentByEmail(email: String): AgentEntity?

    @Query("SELECT password FROM AgentEntity WHERE email = :email")
    fun getPasswordByEmail(email: String): String?

    @Update
    fun updateAgent(agentEntity: AgentEntity)

    @Query("DELETE FROM AgentEntity WHERE id = :id")
    fun deleteAgentById(id: Long)
}