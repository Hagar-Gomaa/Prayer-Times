package com.example.prayertimes.domain.usecase

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.prayerstimes.data.local.PrayTimesDao
import com.example.prayertimes.data.local.PrayDataBaseEntity
import com.example.prayertimes.domain.Repository
import com.example.prayertimes.domain.entities.PrayerTimesEntity
import com.example.prayertimes.ui.home.HomeUiState
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class GetPrayerTimesUseCase @Inject constructor(
    private val repository: Repository,
    private val dao: PrayTimesDao
) {

    suspend operator fun invoke(
        date: String,
        latitude: Double,
        longitude: Double,
        methodId: Int
    ): PrayerTimesEntity {
        val prayerTimesUseCaseEntity = repository.getPrayTimes(date, latitude, longitude, methodId)
        val prayDataBaseEntity = PrayDataBaseEntity(
            date = date,
            latitude = latitude,
            longitude = longitude,
            method = methodId,
            fajr = prayerTimesUseCaseEntity.timings.fajr,
            sunrise = prayerTimesUseCaseEntity.timings.sunrise,
            dhuhr = prayerTimesUseCaseEntity.timings.dhuhr,
            asr = prayerTimesUseCaseEntity.timings.asr,
            maghrib = prayerTimesUseCaseEntity.timings.maghrib,
            isha = prayerTimesUseCaseEntity.timings.isha
        )
        dao.insert(prayDataBaseEntity)
        Log.e("prayDataBaseEntity",prayDataBaseEntity.toString()+111111111111111111)
        return prayerTimesUseCaseEntity
    }

    fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return sdf.format(Date())
    }

    fun getDateListForWeek(): MutableList<String> {
        val dateListForWeek = mutableListOf<String>()

        dateListForWeek.add(getCurrentDate())
        for (i in 1..8) {
            val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, i)
            val nextDate = sdf.format(calendar.time)
            dateListForWeek.add(nextDate)
        }
        return dateListForWeek
    }

    fun getNextPrayer(prayerTimesUiState: HomeUiState.PrayerTimesUiState): Pair<String, String> {
        var nextPrayerTime: String? = null
        val currentTime = getCurrentTime()
        return when {
            currentTime < prayerTimesUiState.fajr -> {
                nextPrayerTime = prayerTimesUiState.fajr
                return Pair("Fajr", nextPrayerTime)
            }

            currentTime < prayerTimesUiState.sunrise -> {
                nextPrayerTime = prayerTimesUiState.fajr
                return Pair("Sunrise", nextPrayerTime)
            }

            currentTime < prayerTimesUiState.dhuhr -> {
                nextPrayerTime = prayerTimesUiState.fajr
                return Pair("Dhuhr", nextPrayerTime)
            }

            currentTime < prayerTimesUiState.asr -> {
                nextPrayerTime = prayerTimesUiState.fajr
                return Pair("Asr", nextPrayerTime)
            }

            currentTime < prayerTimesUiState.maghrib -> {
                nextPrayerTime = prayerTimesUiState.fajr
                return Pair("Maghrib", nextPrayerTime)
            }

            currentTime < prayerTimesUiState.isha -> {
                nextPrayerTime = prayerTimesUiState.fajr
                return Pair("Isha", nextPrayerTime)
            }

            else -> Pair("", "")
        }
    }

    fun getCurrentTime(): String {
        val dateFormat = SimpleDateFormat("HH:mm")
        val currentTime = Calendar.getInstance().time
        return dateFormat.format(currentTime)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun calculateTimeDifference(start: String, end: String): String {
        // Parse the start and end times into LocalTime objects
        val startTime = LocalTime.parse(start, DateTimeFormatter.ofPattern("HH:mm"))
        val endTime = LocalTime.parse(end, DateTimeFormatter.ofPattern("HH:mm"))

        // Convert the start and end times to minutes
        val startMinutes = startTime.hour * 60 + startTime.minute
        val endMinutes = endTime.hour * 60 + endTime.minute

        // Calculate the difference in minutes
        var diffMinutes = endMinutes - startMinutes
        if (diffMinutes < 0) {
            diffMinutes += 24 * 60 // Add 24 hours if the end time is on the next day
        }

        // Convert the difference back to hours and minutes
        val hours = diffMinutes / 60
        val minutes = diffMinutes % 60
        Log.e("TimeDifference", "${hours}H and ${minutes}min")
        // Format the result as "XH and Ymin"
        return "${hours}H ${minutes}min"
    }

}