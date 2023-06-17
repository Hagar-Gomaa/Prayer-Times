package com.example.prayertimes.data.remote.mapper

import com.example.prayertimes.Mapper
import com.example.prayertimes.data.remote.dto.MethodsResponseDto
import com.example.prayertimes.domain.entities.MethodsEntity
import javax.inject.Inject

class MethodsDtoToEntityMapper @Inject constructor() : Mapper<MethodsResponseDto, MethodsEntity> {
    override fun map(input: MethodsResponseDto): MethodsEntity {
        return MethodsEntity(
            qatar = Pair(input.prayerTimesData?.qATAR?.id ?: 0, input.prayerTimesData?.qATAR?.name ?: ""),
            mwl = Pair(input.prayerTimesData?.mWL?.id ?: 0, input.prayerTimesData?.mWL?.name ?: ""),
            turkey = Pair(input.prayerTimesData?.tURKEY?.id ?: 0, input.prayerTimesData?.tURKEY?.name ?: ""),
            maka = Pair(input.prayerTimesData?.mAKKAH?.id ?: 0, input.prayerTimesData?.mAKKAH?.name ?: ""),
            gulf = Pair(input.prayerTimesData?.gULF?.id ?: 0, input.prayerTimesData?.gULF?.name ?: ""),
            singapore = Pair(input.prayerTimesData?.sINGAPORE?.id ?: 0, input.prayerTimesData?.sINGAPORE?.name ?: ""),
            tehran = Pair(input.prayerTimesData?.tEHRAN?.id ?: 0, input.prayerTimesData?.tEHRAN?.name ?: ""),
            kuwait = Pair(input.prayerTimesData?.kUWAIT?.id ?: 0, input.prayerTimesData?.kUWAIT?.name ?: ""),
            france = Pair(input.prayerTimesData?.fRANCE?.id ?: 0, input.prayerTimesData?.fRANCE?.name ?: ""),
            jafari = Pair(input.prayerTimesData?.jAFARI?.id ?: 0, input.prayerTimesData?.jAFARI?.name ?: ""),
            moonsighting = Pair(input.prayerTimesData?.mOONSIGHTING?.id ?: 0, input.prayerTimesData?.mOONSIGHTING?.name ?: ""),
            egypt = Pair(input.prayerTimesData?.eGYPT?.id ?: 0, input.prayerTimesData?.eGYPT?.name ?: ""),
            custom = Pair(input.prayerTimesData?.cUSTOM?.id ?: 0,  ""),
            russia = Pair(input.prayerTimesData?.rUSSIA?.id ?: 0, input.prayerTimesData?.rUSSIA?.name ?: ""),
            dubai = Pair(input.prayerTimesData?.dUBAI?.id ?: 0, input.prayerTimesData?.dUBAI?.name ?: ""),
            isna = Pair(input.prayerTimesData?.iSNA?.id ?: 0, input.prayerTimesData?.iSNA?.name ?: ""),
            karachi = Pair(input.prayerTimesData?.kARACHI?.id ?: 0, input.prayerTimesData?.kARACHI?.name ?: "")
        )
    }
}