package com.example.prayertimes.ui.mapper

import com.example.prayertimes.Mapper
import com.example.prayertimes.domain.entities.MethodsEntity
import com.example.prayertimes.domain.entities.PrayerTimesEntity
import com.example.prayertimes.ui.home.HomeUiState
import javax.inject.Inject

class PrayerTimesEntityToUiMapper @Inject constructor() :
    Mapper<PrayerTimesEntity, HomeUiState.PrayerTimesUiState> {
    override fun map(input: PrayerTimesEntity): HomeUiState.PrayerTimesUiState {
        return HomeUiState.PrayerTimesUiState(
            input.timings.sunset ?: "",
            input.timings.asr ?: "",
            input.timings.isha ?: "",
            input.timings.fajr ?: "",
            input.timings.dhuhr ?: "",
            input.timings.maghrib ?: "",
            input.timings.lastthird ?: "",
            input.timings.firstthird ?: "",
            input.timings.sunrise ?: "",
            input.timings.midnight ?: "",
            input.timings.imsak ?: ""
        )
    }
}