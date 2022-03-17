package com.suleimanzhukov.realestatemanagerapp.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.PictureEntity

@Dao
interface PictureDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPicture(picture: PictureEntity)

    @Query("SELECT * FROM PictureEntity WHERE id = :id")
    suspend fun getAllPicturesForPropertyId(id: Long): List<PictureEntity>
}