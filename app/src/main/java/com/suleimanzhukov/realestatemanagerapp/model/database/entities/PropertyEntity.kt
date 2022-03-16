package com.suleimanzhukov.realestatemanagerapp.model.database.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class PropertyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val publisherUsername: String,
    var publisher: String,
    var price: Int,
    var address: String,
    var type: String,
    var timePublished: String,
    var beds: Int,
    var baths: Int,
    var garages: Int,
    var area: Int,
    var details: String,
    var images: String,
    var location: String
) : Parcelable