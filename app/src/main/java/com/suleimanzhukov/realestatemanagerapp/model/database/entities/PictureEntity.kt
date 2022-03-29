package com.suleimanzhukov.realestatemanagerapp.model.database.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity
data class PictureEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var propertyId: Long,
    var url: String,
    var orderNumber: Int
) : Parcelable