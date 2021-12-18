package com.suleimanzhukov.realestatemanagerapp.model.repository

import com.suleimanzhukov.realestatemanagerapp.model.utils.Property

interface Repository {

    fun getPropertyById(id: Long): Property

}