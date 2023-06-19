package com.example.prayertimes.data.local
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Pray_times_table")
data class PrayDataBaseEntity(
    @PrimaryKey (autoGenerate = true)val id:Int ,
    val readable: String,
    val asr: String?,
    val dhuhr: String?,
    val fajr: String?,
    val isha: String?,
    val maghrib: String?,
    val sunrise: String?,
    val latitude: Double?,
    val longitude: Double?,
    var method: Int?,

    )