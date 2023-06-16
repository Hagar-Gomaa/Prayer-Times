package com.example.prayertimes.ui.home

import android.util.Log
import com.example.prayertimes.domain.entities.MethodsEntity
import com.example.prayertimes.domain.usecase.GetMethodsUseCase
import com.example.prayertimes.ui.base.BaseViewModel
import com.example.prayertimes.ui.mapper.MethodsEntityToUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val getMethodsUseCase: GetMethodsUseCase,
    private val mapper: MethodsEntityToUiMapper
) :
    BaseViewModel<HomeUiState, HomeUiEvent>(
        HomeUiState()
    ) , HomeListener {
    suspend fun getMethods() {
        val data:MethodsEntity = getMethodsUseCase()
        Log.e("Home ViewModel",data.gulf.second+data.maka.first)
        tryToExecute(
            { getMethodsUseCase() },
            ::onSuccessGetMethods,
            mapper,
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
    }

    override fun onClickArrow() {
        TODO("Not yet implemented")
    }
}