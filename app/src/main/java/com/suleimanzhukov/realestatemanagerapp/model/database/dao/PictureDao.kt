package com.suleimanzhukov.realestatemanagerapp.model.database.dao

import android.net.wifi.WifiManager
import androidx.room.*
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.PictureEntity

@Dao
interface PictureDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPicture(pictures: List<PictureEntity>)

    @Query("SELECT * FROM PictureEntity WHERE propertyId = :id")
    suspend fun getAllPicturesForPropertyId(id: Long): List<PictureEntity>

    @Query("SELECT * FROM PictureEntity")
    suspend fun getAllPictures(): List<PictureEntity>

    @Query("UPDATE PictureEntity SET propertyId = :propertyId WHERE id = :id")
    suspend fun updateIdPictures(propertyId: Long, id: Long)

    @Update
    suspend fun updatePictures(pictures: MutableList<PictureEntity>)
}