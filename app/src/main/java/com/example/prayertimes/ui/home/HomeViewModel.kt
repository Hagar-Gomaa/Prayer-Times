package com.example.prayertimes.ui.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.prayertimes.domain.entities.MethodsEntity
import com.example.prayertimes.domain.entities.PrayerTimesEntity
import com.example.prayertimes.domain.usecase.GetMethodsUseCase
import com.example.prayertimes.domain.usecase.GetPrayerTimesUseCase
import com.example.prayertimes.ui.base.BaseViewModel
import com.example.prayertimes.ui.mapper.MethodsEntityToUiMapper
import com.example.prayertimes.ui.mapper.PrayerTimesEntityToUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMethodsUseCase: GetMethodsUseCase,
    private val getPrayerTimesUseCase: GetPrayerTimesUseCase,
    private val methodsMapper: MethodsEntityToUiMapper,
    private val prayerTimesEntityToUiMapper: PrayerTimesEntityToUiMapper
) :
    BaseViewModel<HomeUiState, HomeUiEvent>(
        HomeUiState()
    ), HomeListener {
    suspend fun getMethods() {
        val data: MethodsEntity = getMethodsUseCase()
        Log.e("Home ViewModel", data.gulf.second + data.maka.first)
        tryToExecute(
            { getMethodsUseCase() },
            ::onSuccessGetMethods,
            methodsMapper,
            ::onErrorGetMethods
        )
    }

    private fun onSuccessGetMethods(methodsUiState: HomeUiState.MethodsUiState) {
        _state.update {
            it.copy(methods = methodsUiState, isLoading = false)
        }
    }

    private fun onErrorGetMethods(e: Throwable) {
        _state.update {
            it.copy(error = listOf(e.message.toString()))
        }
        viewModelScope.launch {
            _event.emit(HomeUiEvent.ShowSnackBar(e.message.toString()))
        }
    }

    suspend fun getPrayerTimes(date: String, latitude: Double, longitude: Double, methodId: Int) {
        val data: PrayerTimesEntity = getPrayerTimesUseCase(date, latitude, longitude, methodId)
        Log.e("Home ViewModel", data.timings.toString())
        tryToExecute(
            { getPrayerTimesUseCase(date, latitude, longitude, methodId) },
            ::onSuccessGetPrayerTimes,
            prayerTimesEntityToUiMapper,
            ::onErrorGetPrayerTimes
        )
    }

    private fun onSuccessGetPrayerTimes(prayerTimesUiState: HomeUiState.PrayerTimesUiState) {
        _state.update {
            it.copy(prayerTimes = prayerTimesUiState, isLoading = false)
        }
    }

    private fun onErrorGetPrayerTimes(e: Throwable) {
        _state.update {
            it.copy(error = listOf(e.message.toString()))
        }
    }

    override fun onClickArrowBack() {
        sendEvent(HomeUiEvent.ClickArrowBack)
    }

    override fun onClickArrowNext() {
        sendEvent(HomeUiEvent.ClickArrowNext)
    }
}