package com.example.prayertimes.domain

import com.example.prayertimes.data.remote.dto.PrayerTimesDto
import com.example.prayertimes.domain.entities.MethodsEntity
import com.example.prayertimes.domain.entities.PrayerTimesEntity

interface Repository {
suspend fun getMethods(): MethodsEntity
suspend fun getPrayTimes(date:String, latitude:Double, longitude:Double, methodeId:Int):PrayerTimesEntity
}