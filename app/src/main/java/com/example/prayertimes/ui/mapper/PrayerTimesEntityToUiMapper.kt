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
            sunrise = input.timings.sunrise.toString(),
            asr = input.timings.asr.toString(),
            isha = input.timings.isha.toString(),
            fajr = input.timings.fajr.toString(),
            dhuhr = input.timings.dhuhr.toString(),
            maghrib = input.timings.maghrib.toString()
        )
    }
}