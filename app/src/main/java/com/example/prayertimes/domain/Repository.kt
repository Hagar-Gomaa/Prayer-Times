package com.example.prayertimes.domain

import com.example.prayertimes.data.local.dataBaseEntity.PrayDataBaseEntity
import com.example.prayertimes.domain.entities.MethodsEntity
import com.example.prayertimes.domain.entities.PrayerTimesEntity

interface Repository {
    suspend fun getMethods(): MethodsEntity
    suspend fun getPrayTimes(
        date: String,
        latitude: Double,
        longitude: Double,
        methodeId: Int
    ): PrayerTimesEntity

    suspend fun insertPrayerTimesDataBase(prayerTimesEntity: PrayerTimesEntity)
    suspend fun getAllPrayerTimesDataBase(): List<PrayDataBaseEntity>

}