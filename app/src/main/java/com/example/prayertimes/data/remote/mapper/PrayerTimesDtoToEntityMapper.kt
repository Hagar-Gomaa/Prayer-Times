package com.example.prayertimes.data.remote.mapper

import com.example.prayertimes.Mapper
import com.example.prayertimes.data.remote.dto.PrayerTimesData
import com.example.prayertimes.data.remote.dto.PrayerTimesDto
import com.example.prayertimes.data.remote.dto.Timings
import com.example.prayertimes.domain.entities.PrayerTimesEntity
import javax.inject.Inject

class PrayerTimesDtoToEntityMapper @Inject constructor() : Mapper<PrayerTimesDto, PrayerTimesEntity> {
    override fun map(input: PrayerTimesDto): PrayerTimesEntity {
        return PrayerTimesEntity(input.prayerTimesData?.date?.readable.toString(),input.prayerTimesData?: PrayerTimesData(),input.prayerTimesData?.timings?: Timings())
    }
}