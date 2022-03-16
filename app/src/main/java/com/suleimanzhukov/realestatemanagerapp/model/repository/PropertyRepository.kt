package com.suleimanzhukov.realestatemanagerapp.model.repository

import android.content.Context
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.PropertyEntity

interface PropertyRepository {

    suspend fun addProperty(property: PropertyEntity): PropertyEntity

    suspend fun getAllProperties(): List<PropertyEntity>

    suspend fun getAllPropertiesWithAgent(agentEmail: String): MutableList<PropertyEntity>

    suspend fun getPropertyById(id: Long): PropertyEntity?
}