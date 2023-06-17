package com.example.prayertimes.data.remote.service

import com.example.prayertimes.data.remote.dto.MethodsResponseDto
import com.example.prayertimes.data.remote.dto.PrayerTimesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PrayerTimesService {
    @GET("methods")
    suspend fun getMethods(): Response<MethodsResponseDto>

    @GET("timings/{date}")
    suspend fun getTimesPray(
        @Path("date") date: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("method") method: Int
    ): Response<PrayerTimesDto>

}