package com.example.prayertimes.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MethodsResponseDto(

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("data")
    val prayerTimesData: Data? = null,

    @field:SerializedName("status")
    val status: String? = null
)

data class MAKKAH(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("location")
    val location: Location? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("params")
    val params: Params? = null
)

data class Params(

    @field:SerializedName("Isha")
    val isha: String? = null,

    @field:SerializedName("Fajr")
    val fajr: Double? = null,

    @field:SerializedName("shafaq")
    val shafaq: String? = null,

    @field:SerializedName("Maghrib")
    val maghrib: Any? = null,

    @field:SerializedName("Midnight")
    val midnight: String? = null
)

data class RUSSIA(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("location")
    val location: Location? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("params")
    val params: Params? = null
)

data class MWL(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("location")
    val location: Location? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("params")
    val params: Params? = null
)

data class DUBAI(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("location")
    val location: Location? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("params")
    val params: Params? = null
)

data class EGYPT(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("location")
    val location: Location? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("params")
    val params: Params? = null
)

data class Location(

    @field:SerializedName("latitude")
    val latitude: Any? = null,

    @field:SerializedName("longitude")
    val longitude: Any? = null
)

data class TEHRAN(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("location")
    val location: Location? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("params")
    val params: Params? = null
)

data class JAFARI(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("location")
    val location: Location? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("params")
    val params: Params? = null
)

data class CUSTOM(

    @field:SerializedName("id")
    val id: Int? = null
)

data class QATAR(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("location")
    val location: Location? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("params")
    val params: Params? = null
)

data class TURKEY(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("location")
    val location: Location? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("params")
    val params: Params? = null
)

data class KUWAIT(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("location")
    val location: Location? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("params")
    val params: Params? = null
)

data class ISNA(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("location")
    val location: Location? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("params")
    val params: Params? = null
)

data class FRANCE(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("location")
    val location: Location? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("params")
    val params: Params? = null
)

data class SINGAPORE(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("location")
    val location: Location? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("params")
    val params: Params? = null
)

data class GULF(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("location")
    val location: Location? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("params")
    val params: Params? = null
)

data class KARACHI(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("location")
    val location: Location? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("params")
    val params: Params? = null

)

data class Data(
    @field:SerializedName("QATAR")
    val qATAR: QATAR? = null,

    @field:SerializedName("MWL")
    val mWL: MWL? = null,

    @field:SerializedName("TURKEY")
    val tURKEY: TURKEY? = null,

    @field:SerializedName("MAKKAH")
    val mAKKAH: MAKKAH? = null,

    @field:SerializedName("GULF")
    val gULF: GULF? = null,

    @field:SerializedName("SINGAPORE")
    val sINGAPORE: SINGAPORE? = null,

    @field:SerializedName("TEHRAN")
    val tEHRAN: TEHRAN? = null,

    @field:SerializedName("KUWAIT")
    val kUWAIT: KUWAIT? = null,

    @field:SerializedName("FRANCE")
    val fRANCE: FRANCE? = null,

    @field:SerializedName("JAFARI")
    val jAFARI: JAFARI? = null,

    @field:SerializedName("MOONSIGHTING")
    val mOONSIGHTING: MOONSIGHTING? = null,

    @field:SerializedName("EGYPT")
    val eGYPT: EGYPT? = null,

    @field:SerializedName("CUSTOM")
    val cUSTOM: CUSTOM? = null,

    @field:SerializedName("RUSSIA")
    val rUSSIA: RUSSIA? = null,

    @field:SerializedName("DUBAI")
    val dUBAI: DUBAI? = null,

    @field:SerializedName("ISNA")
    val iSNA: ISNA? = null,

    @field:SerializedName("KARACHI")
    val kARACHI: KARACHI? = null
)

data class MOONSIGHTING(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("params")
    val params: Params? = null
)
