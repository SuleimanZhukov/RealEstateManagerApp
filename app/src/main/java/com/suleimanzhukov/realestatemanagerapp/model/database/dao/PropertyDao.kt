package com.suleimanzhukov.realestatemanagerapp.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.PropertyEntity

@Dao
interface PropertyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProperty(property: PropertyEntity)
}