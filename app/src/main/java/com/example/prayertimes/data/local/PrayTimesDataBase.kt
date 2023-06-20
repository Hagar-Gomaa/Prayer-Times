package com.example.prayertimes.data.local
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.prayerstimes.data.local.PrayTimesDao
import com.example.prayertimes.data.local.dataBaseEntity.PrayDataBaseEntity


@Database(entities = [PrayDataBaseEntity::class], version = 2, exportSchema = false)
abstract class PrayTimesDataBase:RoomDatabase() {
    abstract fun prayTimeDao():PrayTimesDao
}
