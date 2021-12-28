package com.suleimanzhukov.realestatemanagerapp.model.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Agent(
    var username: String,
    var age: String,
    var email: String,
    var password: String,
    var phone: String,
    var profileImg: String
): Parcelable