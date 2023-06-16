package com.example.prayertimes.data.remote.service

import com.example.prayertimes.data.remote.dto.MethodsResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface PrayerTimesService {

    @GET("methods")
    suspend fun getMethods(): Response<MethodsResponseDto>
}