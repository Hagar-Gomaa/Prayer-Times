package com.example.prayerstimes.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.prayertimes.data.local.dataBaseEntity.PrayDataBaseEntity

@Dao
interface PrayTimesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(prayDataBaseEntity: PrayDataBaseEntity)

    @Query(
        "SELECT * FROM Pray_times_table"
    )
   suspend fun getAllPrayTimes(): List<PrayDataBaseEntity>
//    @Delete(" DELETE * FROM Pray_times_table")
//    fun clear(){
//
//    }
}