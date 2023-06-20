package com.example.prayertimes.domain.usecase

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.prayerstimes.data.local.PrayTimesDao
import com.example.prayertimes.data.local.dataBaseEntity.PrayDataBaseEntity
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
    private val repository: Repository
) {

    suspend operator fun invoke(
        date: String,
        latitude: Double,
        longitude: Double,
        methodId: Int
    ): PrayerTimesEntity {
        val prayerTimesUseCaseEntity = repository.getPrayTimes(date, latitude, longitude, methodId)
       repository.insertPrayerTimesDataBase(prayerTimesUseCaseEntity)
        Log.e("prayDataBaseEntity",prayerTimesUseCaseEntity.toString()+111111111111111111)
       val p = repository.getAllPrayerTimesDataBase()
        Log.e("getAllPrayerTimesDataBase",p.toString()+111111111111111111)
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
                nextPrayerTime = prayerTimesUiState.sunrise
                return Pair("Sunrise", nextPrayerTime)
            }

            currentTime < prayerTimesUiState.dhuhr -> {
                nextPrayerTime = prayerTimesUiState.dhuhr
                return Pair("Dhuhr", nextPrayerTime)
            }

            currentTime < prayerTimesUiState.asr -> {
                nextPrayerTime = prayerTimesUiState.asr
                return Pair("Asr", nextPrayerTime)
            }

            currentTime < prayerTimesUiState.maghrib -> {
                nextPrayerTime = prayerTimesUiState.maghrib
                return Pair("Maghrib", nextPrayerTime)
            }

            currentTime < prayerTimesUiState.isha -> {
                nextPrayerTime = prayerTimesUiState.isha
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
        val startTime = LocalTime.parse(start, DateTimeFormatter.ofPattern("HH:mm"))
        val endTime = LocalTime.parse(end, DateTimeFormatter.ofPattern("HH:mm"))

        val startMinutes = startTime.hour * 60 + startTime.minute
        val endMinutes = endTime.hour * 60 + endTime.minute

        // Calculate the difference in minutes
        var diffMinutes = endMinutes - startMinutes
        if (diffMinutes < 0) {
            diffMinutes += 24 * 60
        }

        // Convert the difference back to hours and minutes
        val hours = diffMinutes / 60
        val minutes = diffMinutes % 60
        Log.e("StartTime", "$startTime")
        Log.e("EndTime", "$endTime")

        Log.e("TimeDifference", "${hours}H and ${minutes}min")
        // Format the result as "XH and Ymin"
       // if (hours>12) hours -= 12
        return "${hours}H ${minutes}min"
    }

}