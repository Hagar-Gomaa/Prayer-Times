package com.example.prayertimes.data.local
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Pray_times_table")
data class PrayDataBaseEntity(
    @PrimaryKey (autoGenerate = true)val id:Int =0 ,
    val date: String,
    val latitude: Double?,
    val longitude: Double?,
    var method: Int?,
    val fajr: String?,
    val sunrise: String?,
    val dhuhr: String?,
    val asr: String?,
    val maghrib: String?,
    val isha: String?,

    )