package com.suleimanzhukov.realestatemanagerapp.model.database

import androidx.room.Entity

@Entity
data class Entity(
    val id: Long,
    var propertyName: String
)