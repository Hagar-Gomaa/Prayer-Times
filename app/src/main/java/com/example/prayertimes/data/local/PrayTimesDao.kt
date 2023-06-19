package com.example.prayerstimes.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.prayertimes.data.local.PrayDataBaseEntity

@Dao
interface PrayTimesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(prayDataBaseEntity: PrayDataBaseEntity)

    @Query(
        "SELECT * FROM Pray_times_table  WHERE readable " +
                "==:data and latitude==:latitude and longitude==:longitude and id==:method"
    )
    fun getAllPrayTimes(
        data: String, latitude: Double, longitude: Double, method: Int
    ): PrayDataBaseEntity
}