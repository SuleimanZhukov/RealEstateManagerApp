package com.suleimanzhukov.realestatemanagerapp.model.repository

import android.content.Context
import android.content.LocusId
import android.provider.ContactsContract
import com.suleimanzhukov.realestatemanagerapp.model.database.Databases
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.PropertyEntity

class PropertyRepositoryImpl : PropertyRepository {

    override suspend fun addProperty(property: PropertyEntity, context: Context): PropertyEntity {
        Databases.getDatabase(context).propertyDao().addProperty(property)
        return property
    }

    override suspend fun getAllProperties(context: Context): List<PropertyEntity> {
        return Databases.getDatabase(context).propertyDao().getAllProperties()
    }

    override suspend fun getPropertyById(context: Context, id: Long): PropertyEntity? {
        return Databases.getDatabase(context).propertyDao().getPropertyById(id)
    }
}