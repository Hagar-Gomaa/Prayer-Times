package com.example.prayertimes.data.remote.mapper

import com.example.prayertimes.Mapper
import com.example.prayertimes.data.remote.dto.Data
import com.example.prayertimes.data.remote.dto.MethodsResponseDto
import com.example.prayertimes.domain.entities.MethodsEntity
import javax.inject.Inject

class MethodsDtoToEntityMapper @Inject constructor() : Mapper<MethodsResponseDto, MethodsEntity> {
    override fun map(input: MethodsResponseDto): MethodsEntity {
        return MethodsEntity(
            qatar = Pair(input.data?.qATAR?.id ?: 0, input.data?.qATAR?.name ?: ""),
            mwl = Pair(input.data?.mWL?.id ?: 0, input.data?.mWL?.name ?: ""),
            turkey = Pair(input.data?.tURKEY?.id ?: 0, input.data?.tURKEY?.name ?: ""),
            maka = Pair(input.data?.mAKKAH?.id ?: 0, input.data?.mAKKAH?.name ?: ""),
            gulf = Pair(input.data?.gULF?.id ?: 0, input.data?.gULF?.name ?: ""),
            singapore = Pair(input.data?.sINGAPORE?.id ?: 0, input.data?.sINGAPORE?.name ?: ""),
            tehran = Pair(input.data?.tEHRAN?.id ?: 0, input.data?.tEHRAN?.name ?: ""),
            kuwait = Pair(input.data?.kUWAIT?.id ?: 0, input.data?.kUWAIT?.name ?: ""),
            france = Pair(input.data?.fRANCE?.id ?: 0, input.data?.fRANCE?.name ?: ""),
            jafari = Pair(input.data?.jAFARI?.id ?: 0, input.data?.jAFARI?.name ?: ""),
            moonsighting = Pair(input.data?.mOONSIGHTING?.id ?: 0, input.data?.mOONSIGHTING?.name ?: ""),
            egypt = Pair(input.data?.eGYPT?.id ?: 0, input.data?.eGYPT?.name ?: ""),
            custom = Pair(input.data?.cUSTOM?.id ?: 0,  ""),
            russia = Pair(input.data?.rUSSIA?.id ?: 0, input.data?.rUSSIA?.name ?: ""),
            dubai = Pair(input.data?.dUBAI?.id ?: 0, input.data?.dUBAI?.name ?: ""),
            isna = Pair(input.data?.iSNA?.id ?: 0, input.data?.iSNA?.name ?: ""),
            karachi = Pair(input.data?.kARACHI?.id ?: 0, input.data?.kARACHI?.name ?: "")
        )
    }
}