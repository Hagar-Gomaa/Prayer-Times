package com.example.prayertimes.ui.home

import com.example.prayertimes.data.remote.dto.PrayerTimesData
import com.google.gson.annotations.SerializedName


data class HomeUiState(
    val isLoading: Boolean = false,
    val error: List<String>? = null,
    val prayerTimes: PrayerTimesUiState = PrayerTimesUiState(
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        ""
    ),
    val methods: MethodsUiState = MethodsUiState(
        Pair(0, ""),
        Pair(0, ""),
        Pair(0, ""),
        Pair(0, ""),
        Pair(0, ""),
        Pair(0, ""),
        Pair(0, ""),
        Pair(0, ""),
        Pair(0, ""),
        Pair(0, ""),
        Pair(0, ""),
        Pair(0, ""),
        Pair(0, ""),
        Pair(0, ""),
        Pair(0, ""),
        Pair(0, ""),
        Pair(0, "")
    )
) {
    data class PrayerTimesUiState(
        val sunset: String,
        val asr: String,
        val isha: String,
        val fajr: String,
        val dhuhr: String,
        val maghrib: String,
        val lastthird: String,
        val firstthird: String,
        val sunrise: String,
        val midnight: String,
        val imsak: String
    )

    data class MethodsUiState(
        val qatar: Pair<Int, String>,
        val mwl: Pair<Int, String>,
        val turkey: Pair<Int, String>,
        val maka: Pair<Int, String>,
        val gulf: Pair<Int, String>,
        val singapore: Pair<Int, String>,
        val tehran: Pair<Int, String>,
        val kuwait: Pair<Int, String>,
        val france: Pair<Int, String>,
        val jafari: Pair<Int, String>,
        val moonsighting: Pair<Int, String>,
        val egypt: Pair<Int, String>,
        val custom: Pair<Int, String>,
        val russia: Pair<Int, String>,
        val dubai: Pair<Int, String>,
        val isna: Pair<Int, String>,
        val karachi: Pair<Int, String>
    )
}