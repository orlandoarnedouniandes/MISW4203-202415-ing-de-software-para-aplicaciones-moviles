package com.mobileapp.mymobileapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mobileapp.mymobileapp.models.CollectorEntity


@Dao
interface CollectorDao {
    @Query("SELECT * FROM collectors")
    fun getAllCollectors(): LiveData<List<CollectorEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(collectors: List<CollectorEntity>)
}





