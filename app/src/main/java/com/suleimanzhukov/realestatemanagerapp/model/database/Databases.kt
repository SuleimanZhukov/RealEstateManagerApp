package com.suleimanzhukov.realestatemanagerapp.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AgentEntity::class], version = 1, exportSchema = false)
abstract class Databases : RoomDatabase() {
    abstract fun agentDao(): AgentDao

    companion object {
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
                    "agent_database")
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}