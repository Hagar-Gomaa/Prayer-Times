package com.example.prayertimes.data.local.mapper

import com.example.prayertimes.Mapper
import com.example.prayertimes.data.local.dataBaseEntity.PrayDataBaseEntity
import com.example.prayertimes.data.remote.dto.PrayerTimesDto
import javax.inject.Inject

class PrayerDtoToLocalMapper @Inject constructor() {}
// : Mapper<PrayerTimesDto, PrayDataBaseEntity> {
//    override fun map(input: PrayerTimesDto): PrayDataBaseEntity {
//        return PrayDataBaseEntity(
//            id = input.prayerTimesData?.date?.readable.toString(),
//            date = input.prayerTimesData?.date?.readable.toString(),
//            latitude = input.prayerTimesData?.meta?.latitude.toString(),
//            longitude = input.prayerTimesData?.meta?.longitude.toString(),
//            method = input.prayerTimesData?.meta?.method?.id.toString(),
//            address = input.prayerTimesData?.meta?.method?.name.toString(),
//            fajr = input.prayerTimesData?.timings?.fajr.toString(),
//            sunrise = input.prayerTimesData?.timings?.sunrise.toString(),
//            dhuhr = input.prayerTimesData?.timings?.dhuhr.toString(),
//            asr = input.prayerTimesData?.timings?.asr.toString(),
//            maghrib = input.prayerTimesData?.timings?.maghrib.toString(),
//            isha = input.prayerTimesData?.timings?.isha.toString()
//        )
//    }
//}