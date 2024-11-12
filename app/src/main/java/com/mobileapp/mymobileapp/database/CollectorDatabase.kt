package com.mobileapp.mymobileapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mobileapp.mymobileapp.data.dao.CollectorDao
import com.mobileapp.mymobileapp.models.CollectorEntity


@Database(entities = [CollectorEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CollectorDatabase : RoomDatabase() {
    abstract fun collectorDao(): CollectorDao

    companion object {
        @Volatile private var instance: CollectorDatabase? = null

        fun getDatabase(context: Context): CollectorDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, CollectorDatabase::class.java, "collector_database")
                .fallbackToDestructiveMigration()
                .build()
    }
}