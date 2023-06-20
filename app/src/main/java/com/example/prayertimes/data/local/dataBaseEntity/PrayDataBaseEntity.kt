package com.example.prayertimes.data.local.dataBaseEntity
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Pray_times_table")
data class PrayDataBaseEntity(
    @PrimaryKey (autoGenerate = false) val id: String,
    val date: String,
    val dateHijri: String,
    val latitude: String,
    val longitude: String,
    var method: String,
    val address:String,
    val fajr: String,
    val sunrise: String,
    val dhuhr: String,
    val asr: String,
    val maghrib: String,
    val isha: String,

    )