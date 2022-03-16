package com.suleimanzhukov.realestatemanagerapp.model.repository

import android.content.Context
import com.suleimanzhukov.realestatemanagerapp.model.database.Databases
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.PictureEntity
import javax.inject.Inject

class PictureRepositoryImpl @Inject constructor(
    private val context: Context
) : PictureRepository {

    override suspend fun getAllPicturesForPropertyId(id: Long): List<PictureEntity> {
        return Databases.getDatabase(context).pictureDao().getAllPicturesForPropertyId(id)
    }
}