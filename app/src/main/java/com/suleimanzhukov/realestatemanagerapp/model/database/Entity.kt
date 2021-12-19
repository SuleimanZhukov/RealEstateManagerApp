package com.suleimanzhukov.realestatemanagerapp.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Entity(
    @PrimaryKey(autoGenerate = true)
    var id: Long,

    var cost: String,
    var address: String,
    var timePosted: String,
    var beds: Int,
    var Baths: Int,
    var garage: Int,
    var area: String,
    var overview: String,
    var apartment: Boolean,
    var like: Boolean,
    var forSale: Boolean,
    var images: String
)