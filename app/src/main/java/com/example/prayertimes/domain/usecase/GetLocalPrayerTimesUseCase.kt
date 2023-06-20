package com.example.prayertimes.domain.usecase

import android.annotation.SuppressLint
import android.util.Log
import com.example.prayertimes.data.local.dataBaseEntity.PrayDataBaseEntity
import com.example.prayertimes.domain.Repository
import javax.inject.Inject

class GetLocalPrayerTimesUseCase @Inject constructor(
    private val repository: Repository
) {

    @SuppressLint("SuspiciousIndentation")
    suspend operator fun invoke(): List<PrayDataBaseEntity> {
      val arrangedList =   repository.getAllPrayerTimesDataBase().sortedByDescending {it.date}.take(7).reversed()
        Log.e("arrangedList",arrangedList.toString())
        return arrangedList
    }

}