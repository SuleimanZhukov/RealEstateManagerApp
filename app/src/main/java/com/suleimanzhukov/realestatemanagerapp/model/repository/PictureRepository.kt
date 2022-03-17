package com.suleimanzhukov.realestatemanagerapp.model.repository

import com.suleimanzhukov.realestatemanagerapp.model.database.entities.PictureEntity

interface PictureRepository {

    suspend fun getAllPicturesForPropertyId(id: Long): List<PictureEntity>

    suspend fun addPicture(picture: PictureEntity): PictureEntity
}