package com.example.prayertimes.domain.entities

import com.example.prayertimes.data.remote.dto.Date
import com.example.prayertimes.data.remote.dto.PrayerTimesData
import com.example.prayertimes.data.remote.dto.Timings

data class PrayerTimesEntity(val id:String ,val date : PrayerTimesData, val timings: Timings)
