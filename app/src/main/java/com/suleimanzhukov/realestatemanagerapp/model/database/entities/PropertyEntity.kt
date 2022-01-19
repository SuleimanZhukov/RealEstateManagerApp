package com.suleimanzhukov.realestatemanagerapp.model.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PropertyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var publisher: String,
    var price: Int,
    var address: String,
    var type: String,
    var timePublished: String,
    var beds: Int,
    var baths: Int,
    var garages: Int,
    var details: String,
    var images: String,
    var location: String
)