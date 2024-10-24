package com.mobileapp.mymobileapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mobileapp.mymobileapp.data.dao.ArtistDao
import com.mobileapp.mymobileapp.models.ArtistEntity

@Database(entities = [ArtistEntity::class], version = 1, exportSchema = false)
abstract class ArtistDatabase : RoomDatabase() {

    abstract fun artistDao(): ArtistDao

    companion object {
        @Volatile
        private var INSTANCE: ArtistDatabase? = null

        fun getDatabase(context: Context): ArtistDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ArtistDatabase::class.java,
                    "artist_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
