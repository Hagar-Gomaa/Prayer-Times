package com.example.prayertimes.ui.mapper

import com.example.prayertimes.Mapper
import com.example.prayertimes.data.local.dataBaseEntity.PrayDataBaseEntity
import com.example.prayertimes.domain.entities.MethodsEntity
import com.example.prayertimes.domain.entities.PrayerTimesEntity
import com.example.prayertimes.ui.home.HomeUiState
import javax.inject.Inject

class PrayerTimesLocalEntityToUiMapper @Inject constructor() :
    Mapper<PrayDataBaseEntity, HomeUiState> {
    override fun map(input: PrayDataBaseEntity): HomeUiState {
        return HomeUiState(
            prayerTimes = HomeUiState.PrayerTimesUiState(
                sunrise = input.sunrise,
                asr = input.asr,
                isha = input.isha,
                fajr = input.fajr,
                dhuhr = input.dhuhr,
                maghrib = input.maghrib
            ), date = input.date,
            dateHijri = input.date,
            latitude = input.latitude,
            longitude = input.longitude,
            timeLeft = "", methodId = input.method
        )
    }
}