package com.suleimanzhukov.realestatemanagerapp.model.database.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class AgentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var username: String,
    var age: String,
    var email: String,
    var password: String,
    var mobile: String,
    var profileImg: String,
    var location: String,
    var overview: String,
    var languages: String,
    var forSale: Int,
    var forRent: Int
) : Parcelable