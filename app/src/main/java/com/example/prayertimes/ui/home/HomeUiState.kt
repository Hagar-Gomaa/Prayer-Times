package com.example.prayertimes.ui.home

data class HomeUiState(
    val isLoading: Boolean = false,
    val error: List<String>? = null,
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