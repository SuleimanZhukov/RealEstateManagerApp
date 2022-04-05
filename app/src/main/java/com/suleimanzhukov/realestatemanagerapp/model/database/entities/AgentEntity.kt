package com.suleimanzhukov.realestatemanagerapp.model.database.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity
data class AgentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var username: String?,
    var age: Long?,
    var email: String?,
    var mobile: String?,
    var profileImg: String?,
    var overview: String?,
    var forSale: Long?
) : Parcelable