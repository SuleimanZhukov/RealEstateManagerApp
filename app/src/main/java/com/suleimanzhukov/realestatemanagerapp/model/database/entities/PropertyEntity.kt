package com.suleimanzhukov.realestatemanagerapp.model.database.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Entity
data class PropertyEntity(
    @PrimaryKey
    val id: Long,
    val publisherUsername: String,
    var publisher: String,
    var price: Int,
    var address: String,
    var type: String,
    val timePublished: String,
    var beds: Int,
    var baths: Int,
    var garages: Int,
    var area: Int,
    var details: String,
    var location: String
)