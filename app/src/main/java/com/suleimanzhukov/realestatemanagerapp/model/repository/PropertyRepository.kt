package com.suleimanzhukov.realestatemanagerapp.model.repository

import android.content.Context
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.PropertyEntity

interface PropertyRepository {

    suspend fun addProperty(property: PropertyEntity, context: Context): PropertyEntity
    suspend fun getAllProperties(context: Context): List<PropertyEntity>
    suspend fun getPropertyById(context: Context, id: Long): PropertyEntity?
}