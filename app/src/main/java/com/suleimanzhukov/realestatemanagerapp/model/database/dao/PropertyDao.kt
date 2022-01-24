package com.suleimanzhukov.realestatemanagerapp.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.AgentEntity
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.PropertyEntity

@Dao
interface PropertyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProperty(property: PropertyEntity)

    @Query("SELECT * FROM PropertyEntity")
    suspend fun getAllProperties(): List<PropertyEntity>

    @Query("SELECT * FROM PropertyEntity WHERE id = :id")
    suspend fun getPropertyById(id: Long): PropertyEntity?
}