package com.suleimanzhukov.realestatemanagerapp.model.repository

import android.content.Context
import android.content.LocusId
import android.provider.ContactsContract
import com.suleimanzhukov.realestatemanagerapp.model.database.Databases
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.PropertyEntity
import javax.inject.Inject

class PropertyRepositoryImpl @Inject constructor(
    private val context: Context
) : PropertyRepository {

    override suspend fun addProperty(property: PropertyEntity): PropertyEntity {
        Databases.getDatabase(context).propertyDao().addProperty(property)
        return property
    }

    override suspend fun getAllProperties(): List<PropertyEntity> {
        return Databases.getDatabase(context).propertyDao().getAllProperties()
    }

    override suspend fun getAllPropertiesWithAgent(agentEmail: String): MutableList<PropertyEntity> {
        return Databases.getDatabase(context).propertyDao().getAllPropertiesWithAgent(agentEmail)
    }

    override suspend fun getPropertyById(id: Long): PropertyEntity? {
        return Databases.getDatabase(context).propertyDao().getPropertyById(id)
    }
}