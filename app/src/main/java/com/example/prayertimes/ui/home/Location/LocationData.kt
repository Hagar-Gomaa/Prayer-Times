package com.example.prayertimes.ui.home.Location
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationData(
    var latitudeOfRegion:Double,
    var longitudeOfRegion:Double,
    var country: String,
    var addressOfRegion: String,
    var city: String,
) : Parcelable {

}