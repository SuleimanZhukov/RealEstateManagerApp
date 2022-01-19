package com.suleimanzhukov.realestatemanagerapp.model.database.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.*
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.AgentEntity

@Dao
interface AgentDao {

    @Insert(onConflict = REPLACE)
    suspend fun addAgent(agent: AgentEntity)

    @Query("SELECT username FROM AgentEntity")
    suspend fun getAllAgents(): List<String>

    @Query("SELECT * FROM AgentEntity WHERE id = :id")
    suspend fun getAgentById(id: Long): AgentEntity?

    @Query("SELECT * FROM AgentEntity WHERE email = :email")
    suspend fun getAgentByEmail(email: String): AgentEntity?

    @Query("SELECT password FROM AgentEntity WHERE email = :email")
    suspend fun getPasswordByEmail(email: String): String?

    @Update
    suspend fun updateAgent(agentEntity: AgentEntity)

    @Query("DELETE FROM AgentEntity WHERE id = :id")
    suspend fun deleteAgentById(id: Long)
}