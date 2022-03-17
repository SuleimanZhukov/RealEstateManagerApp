package com.suleimanzhukov.realestatemanagerapp.model.database.dao

import androidx.room.*
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.PictureEntity

@Dao
interface PictureDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPicture(picture: PictureEntity)

    @Query("SELECT * FROM PictureEntity WHERE propertyId = :id")
    suspend fun getAllPicturesForPropertyId(id: Long): List<PictureEntity>

    @Update
    suspend fun updatePictures(pictures: List<PictureEntity>)
}