package com.example.prayertimes.ui.mapper

import com.example.prayertimes.Mapper
import com.example.prayertimes.domain.entities.MethodsEntity
import com.example.prayertimes.ui.home.HomeUiState
import javax.inject.Inject

class MethodsEntityToUiMapper @Inject constructor() : Mapper<MethodsEntity, HomeUiState.MethodsUiState> {
    override fun map(input: MethodsEntity): HomeUiState.MethodsUiState {
        return HomeUiState.MethodsUiState(
            qatar = Pair(input.qatar.first , input.qatar.second),
            mwl = Pair(input.mwl.first , input.mwl.second),
            turkey = Pair(input.turkey.first , input.turkey.second),
            maka = Pair(input.maka.first , input.maka.second),
            gulf = Pair(input.gulf.first , input.gulf.second),
            singapore = Pair(input.singapore.first , input.singapore.second),
            tehran = Pair(input.tehran.first , input.tehran.second),
            kuwait = Pair(input.kuwait.first , input.kuwait.second),
            france = Pair(input.france.first , input.france.second),
            jafari = Pair(input.jafari.first , input.jafari.second),
            moonsighting = Pair(input.moonsighting.first , input.moonsighting.second),
            egypt = Pair(input.egypt.first , input.egypt.second),
            custom = Pair(input.custom.first , input.custom.second),
            russia = Pair(input.russia.first , input.russia.second),
            dubai = Pair(input.dubai.first , input.dubai.second),
            isna = Pair(input.isna.first , input.isna.second),
            karachi = Pair(input.karachi.first , input.karachi.second)
        )
    }
}