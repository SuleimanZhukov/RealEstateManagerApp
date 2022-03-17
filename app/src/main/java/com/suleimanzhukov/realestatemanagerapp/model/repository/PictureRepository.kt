package com.suleimanzhukov.realestatemanagerapp.model.repository

import com.suleimanzhukov.realestatemanagerapp.model.database.entities.PictureEntity

interface PictureRepository {

    suspend fun getAllPicturesForPropertyId(id: Long): List<PictureEntity>

    suspend fun addPictures(pictures: List<PictureEntity>): PictureEntity

    suspend fun getAllPictures(): List<PictureEntity>

    suspend fun updateIdPictures(propertyId: Long, id: Long): PictureEntity

    suspend fun updatePictures(pictures: MutableList<PictureEntity>): PictureEntity
}