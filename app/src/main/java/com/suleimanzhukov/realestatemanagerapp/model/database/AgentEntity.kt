package com.suleimanzhukov.realestatemanagerapp.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AgentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    var name: String,
    var age: String,
    var email: String,
    var password: String,
    var phone: String,
    var profileImg: String
)