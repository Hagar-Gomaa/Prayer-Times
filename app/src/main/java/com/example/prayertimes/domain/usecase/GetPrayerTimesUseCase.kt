package com.example.prayertimes.domain.usecase

import com.example.prayertimes.domain.Repository
import com.example.prayertimes.domain.entities.PrayerTimesEntity
import javax.inject.Inject

class GetPrayerTimesUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke (date:String, latitude:Double, longitude:Double, methodId: Int):PrayerTimesEntity {
        return repository.getPrayTimes(date,latitude,longitude,methodId)
    }
}