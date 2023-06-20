package com.example.prayertimes.data.repository

import android.util.Log
import com.example.prayerstimes.data.local.PrayTimesDao
import com.example.prayertimes.data.local.dataBaseEntity.PrayDataBaseEntity
import com.example.prayertimes.data.remote.dto.PrayerTimesDto
import com.example.prayertimes.data.remote.mapper.MethodsDtoToEntityMapper
import com.example.prayertimes.data.remote.mapper.PrayerTimesDtoToEntityMapper
import com.example.prayertimes.data.remote.service.PrayerTimesService
import com.example.prayertimes.domain.Repository
import com.example.prayertimes.domain.entities.MethodsEntity
import com.example.prayertimes.domain.entities.PrayerTimesEntity
import com.example.prayertimes.domain.usecase.BaseRepository
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private val mapper: MethodsDtoToEntityMapper,
    private val prayerTimesDtoToEntityMapper: PrayerTimesDtoToEntityMapper,
    private val prayerTimesService: PrayerTimesService,private val dao: PrayTimesDao
) : BaseRepository(), Repository {
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
            wrapApiCall { prayerTimesService.getTimesPray(date, latitude, longitude, methodeId) }
        )
    }

    override suspend fun insertPrayerTimesDataBase(prayerTimesEntity: PrayerTimesEntity){
        mapToDataBaseEntity(prayerTimesEntity).let {
            dao.insert(it)
            Log.e("prayDataBaseEntity",it.toString())
        }
    }

    override suspend fun getAllPrayerTimesDataBase(): List<PrayDataBaseEntity> {
return dao.getAllPrayTimes()   }

    private fun mapToDataBaseEntity(prayerTimesUseCaseEntity: PrayerTimesEntity): PrayDataBaseEntity {
        return PrayDataBaseEntity(
            id = prayerTimesUseCaseEntity.id,
            date = prayerTimesUseCaseEntity.date.date?.readable!!,
            latitude = prayerTimesUseCaseEntity.date.meta?.latitude.toString(),
            longitude = prayerTimesUseCaseEntity.date.meta?.latitude.toString(),
            method = prayerTimesUseCaseEntity.date.meta?.method?.id.toString(),
            dateHijri = prayerTimesUseCaseEntity.date.date.hijri?.day!! + " " + prayerTimesUseCaseEntity.date.date.hijri.month!! + " " + prayerTimesUseCaseEntity.date.date.hijri.year!!,
            address = prayerTimesUseCaseEntity.date?.meta?.method?.name!!,
            fajr = prayerTimesUseCaseEntity.timings.fajr!!,
            sunrise = prayerTimesUseCaseEntity.timings.sunrise!!,
            dhuhr = prayerTimesUseCaseEntity.timings.dhuhr!!,
            asr = prayerTimesUseCaseEntity.timings.asr!!,
            maghrib = prayerTimesUseCaseEntity.timings.maghrib!!,
            isha = prayerTimesUseCaseEntity.timings.isha!!
        )
    }

}