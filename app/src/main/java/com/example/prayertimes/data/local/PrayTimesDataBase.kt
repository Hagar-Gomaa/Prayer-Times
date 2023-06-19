package com.example.prayertimes.data.local
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.prayerstimes.data.local.PrayTimesDao


@Database(entities = [PrayDataBaseEntity::class], version = 1)
abstract class PrayTimesDataBase:RoomDatabase() {
    abstract fun prayTimeDao():PrayTimesDao
}
