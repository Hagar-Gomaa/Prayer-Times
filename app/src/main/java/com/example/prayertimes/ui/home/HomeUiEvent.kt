package com.example.prayertimes.ui.home

sealed interface HomeUiEvent {
    data class ShowSnackBar(val messages: String) : HomeUiEvent
    object ClickArrowBack: HomeUiEvent
    object ClickArrowNext: HomeUiEvent


}