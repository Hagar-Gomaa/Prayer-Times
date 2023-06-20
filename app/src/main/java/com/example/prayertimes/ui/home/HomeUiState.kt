package com.example.prayertimes.ui.home


data class HomeUiState(
    val isLoading: Boolean = false,
    val error: List<String>? = null,
    val currentTime: String? = null,
    val nextPrayer: String? = null,
    val nextPrayerTime: String? = null,
    val timeLeft: String,
    val date: String? = null,
    val dateHijri: String? = null,
    val dateForWeek: List<String>? = null,
    val address: String?=null,
    val latitude: String?=null,
    val longitude: String?=null,
    val methodId: String?=null,


    val prayerTimes: PrayerTimesUiState = PrayerTimesUiState(
        "",
        "",
        "",
        "",
        "",
        "",

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
        val asr: String,
        val isha: String,
        val fajr: String,
        val dhuhr: String,
        val maghrib: String,
        val sunrise: String,

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