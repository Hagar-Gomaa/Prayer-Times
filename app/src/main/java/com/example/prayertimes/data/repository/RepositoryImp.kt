package com.example.prayertimes.data.repository

import com.example.prayertimes.data.remote.dto.PrayerTimesDto
import com.example.prayertimes.data.remote.mapper.MethodsDtoToEntityMapper
import com.example.prayertimes.data.remote.mapper.PrayerTimesDtoToEntityMapper
import com.example.prayertimes.data.remote.service.PrayerTimesService
import com.example.prayertimes.domain.Repository
import com.example.prayertimes.domain.entities.MethodsEntity
import com.example.prayertimes.domain.entities.PrayerTimesEntity
import com.example.prayertimes.domain.usecase.BaseRepository
import javax.inject.Inject

class RepositoryImp @Inject constructor(private val mapper: MethodsDtoToEntityMapper,private val prayerTimesDtoToEntityMapper: PrayerTimesDtoToEntityMapper, private val prayerTimesService: PrayerTimesService): BaseRepository() ,Repository {
    override suspend fun getMethods(): MethodsEntity {
        return mapper.map(wrapApiCall { prayerTimesService.getMethods() })
    }

    override suspend fun getPrayTimes(
        date: String,
        latitude: Double,
        longitude: Double,
        methodeId: Int
    ): PrayerTimesEntity {
        return prayerTimesDtoToEntityMapper.map(
            wrapApiCall { prayerTimesService.getTimesPray(date,latitude,longitude,methodeId) }
        )
    }

//    private fun <T> wrapperResponse(response: suspend () -> Response<T>):
//            Flow<UIState<T>> {
//        return flow {
//            try {
//                val result = response()
//                if (result.isSuccessful) {
//                    emit(UIState.Success(result.body()))
//                } else {
//                    emit(UIState.Error(result.message()))
//                    Log.d("TAG", "wrapperResponse:${result.message()} ")
//                }
//            } catch (e: Exception) {
//                emit(UIState.Error(e.message!!))
//                Log.d("TAG", "Exceptioaaan:${e.message!!} ")
//
//            }
//
//        }
//
//
//    }
}