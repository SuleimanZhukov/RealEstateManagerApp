package com.suleimanzhukov.realestatemanagerapp.model.database.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class PictureEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val propertyId: Long,
    val url: String,
    val orderNumber: Int
) : Parcelable