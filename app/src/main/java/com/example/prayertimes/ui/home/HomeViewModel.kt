package com.example.prayertimes.ui.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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

   init {
       getCurrentTime()
       getCurrentDate()
       getDateListForWeek()
   }
    private fun getDateListForWeek() {
       _state.update {
           it.copy(dateForWeek  =getPrayerTimesUseCase.getDateListForWeek())
       }
    }
fun getNextPrayerData(prayerTimesUiState: HomeUiState.PrayerTimesUiState) {
    _state.update {
        it.copy(nextPrayer  = getPrayerTimesUseCase.getNextPrayer(prayerTimesUiState).first,
            nextPrayerTime  = getPrayerTimesUseCase.getNextPrayer(prayerTimesUiState).second)

    }
}
    @RequiresApi(Build.VERSION_CODES.O)
    fun getTimeLeft(currentTime:String, nextPrayerTime:String){
        _state.update {
            it.copy(timeLeft = getPrayerTimesUseCase.calculateTimeDifference(currentTime,nextPrayerTime))
        }
    }
    private fun getCurrentDate(){
       _state.update {
           it.copy(date = getPrayerTimesUseCase.getCurrentDate())
       }
    }


    private fun getCurrentTime(){
        _state.update {
            it.copy(currentTime = getPrayerTimesUseCase.getCurrentTime())
        }
    }

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

    @RequiresApi(Build.VERSION_CODES.O)
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onSuccessGetPrayerTimes(prayerTimesUiState: HomeUiState.PrayerTimesUiState) {
        _state.update {
            it.copy(prayerTimes = prayerTimesUiState, isLoading = false)
        }
        getNextPrayerData(prayerTimesUiState)
        val currentTime =state.value.currentTime!!
        val nextPrayerTime = state.value.nextPrayerTime!!
        getTimeLeft(currentTime,nextPrayerTime)
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