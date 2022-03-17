package com.suleimanzhukov.realestatemanagerapp.model.repository

import android.content.Context
import com.google.gson.internal.bind.DefaultDateTypeAdapter
import com.suleimanzhukov.realestatemanagerapp.model.database.Databases
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.PictureEntity
import javax.inject.Inject

class PictureRepositoryImpl @Inject constructor(
    private val context: Context
) : PictureRepository {

    override suspend fun getAllPicturesForPropertyId(id: Long): List<PictureEntity> {
        return Databases.getDatabase(context).pictureDao().getAllPicturesForPropertyId(id)
    }

    override suspend fun addPictures(pictures: List<PictureEntity>): PictureEntity {
        Databases.getDatabase(context).pictureDao().addPicture(pictures)
        return pictures[0]
    }

    override suspend fun getAllPictures(): List<PictureEntity> {
        return Databases.getDatabase(context).pictureDao().getAllPictures()
    }

    override suspend fun updateIdPictures(propertyId: Long, id: Long): PictureEntity {
        Databases.getDatabase(context).pictureDao().updateIdPictures(propertyId, id)
        return PictureEntity(0, 0, "", 0)
    }

    override suspend fun updatePictures(pictures: MutableList<PictureEntity>): PictureEntity {
        Databases.getDatabase(context).pictureDao().updatePictures(pictures)
        return pictures[0]
    }
}