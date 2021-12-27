package com.suleimanzhukov.realestatemanagerapp.model.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Agent(
    val id: Long,
    var name: String,
    var age: String,
    var email: String,
    var password: String,
    var phone: String,
    var isSignedIn: Boolean,
    var profileImg: String
): Parcelable