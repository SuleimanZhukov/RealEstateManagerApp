package com.suleimanzhukov.realestatemanagerapp.model.database

import android.content.Context
import android.provider.ContactsContract
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.suleimanzhukov.realestatemanagerapp.model.database.dao.AgentDao
import com.suleimanzhukov.realestatemanagerapp.model.database.dao.PropertyDao
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.AgentEntity
import com.suleimanzhukov.realestatemanagerapp.model.database.entities.PropertyEntity

@Database(
    entities = [AgentEntity::class, PropertyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class Databases() : RoomDatabase() {
    abstract fun agentDao(): AgentDao
    abstract fun propertyDao(): PropertyDao

    companion object {
        /*val db: Databases by lazy {
            Room.databaseBuilder(
                context,
                Databases::class.java,
                "real_estate_database"
            ).build()
        }*/
        @Volatile
        var INSTANCE: Databases? = null

        fun getDatabase(context: Context): Databases {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    Databases::class.java,
                    "real_estate_database")
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}