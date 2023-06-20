package com.example.prayertimes.ui.qibla

import android.app.AlertDialog
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.hardware.SensorManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import com.example.prayertimes.R
import com.example.prayertimes.databinding.FragmentQiblaBinding
import com.example.prayertimes.ui.base.BaseFragment
import com.example.prayertimes.ui.base.BaseViewModel
import com.example.prayertimes.ui.home.HomeUiEvent
import com.example.prayertimes.ui.home.HomeUiState
import com.example.prayertimes.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Math.atan2
import java.lang.Math.cos
import java.lang.Math.sin

@AndroidEntryPoint
class QiblaFragment : BaseFragment<FragmentQiblaBinding, HomeUiState, HomeUiEvent>() {
 
    private val qiblaFragmentArgs :QiblaFragmentArgs  by navArgs()
    override val layoutIdFragment: Int
        get() = R.layout.fragment_qibla

    override val viewModel: HomeViewModel by viewModels()
    override fun onEvent(event: HomeUiEvent) {
        TODO("Not yet implemented")
    }

    private lateinit var locationManager: LocationManager

    private var compass: Compass? = null
    private var currentAzimuth: Float = 0.toFloat()
    private lateinit var prefs: SharedPreferences
    private lateinit var gps: GpsTracker



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgQiblaArrow.isVisible=false
        prefs = requireContext().getSharedPreferences("", Context.MODE_PRIVATE)
        locationManager = context?.getSystemService(LOCATION_SERVICE) as LocationManager
        gps = GpsTracker(locationManager)
        setupCompass()
        fetchGPS()
    }

    override fun onStart() {
        super.onStart()
        compass?.start()
    }

    override fun onStop() {
        super.onStop()
        compass?.stop()
    }
    fun back(){

    }

    private fun setupCompass() {
        binding.textKaabaDir.text = resources.getString(R.string.msg_permission_not_granted_yet)
        binding.textCurrentLoc.text =
            resources.getString(R.string.msg_permission_not_granted_yet)
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            1
        )
        compass = context?.let {
            val sensorManager = it.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            Compass(sensorManager)
        }
        compass?.setListener(object : Compass.CompassListener {
            override fun onNewAzimuth(azimuth: Float) {
                adjustGambarDial(azimuth)
                adjustArrowQiblat(azimuth)
            }
        })
    }


    private fun adjustGambarDial(azimuth: Float) {

        val an = RotateAnimation(
            -currentAzimuth, -azimuth,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
            0.5f
        )
        currentAzimuth = azimuth
        an.duration = 500
        an.repeatCount = 0
        an.fillAfter = true
        binding.imgCompass.startAnimation(an)
    }

    private fun adjustArrowQiblat(azimuth: Float) {

        val qiblaDir = retrieveFloat()
        val an = RotateAnimation(
            -currentAzimuth + qiblaDir, -azimuth,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
            0.5f
        )
        currentAzimuth = azimuth
        an.duration = 500
        an.repeatCount = 0
        an.fillAfter = true
        binding.imgQiblaArrow.startAnimation(an)
        binding.imgQiblaArrow.isVisible = qiblaDir > 0
    }

    private fun getBearing() {
        // Get the location manager

        val qiblaDir = retrieveFloat()
        if (qiblaDir > 0.0001) {
            binding.textCurrentLoc.text =
                getString(R.string.your_location, gps.latitude, gps.longitude)
            binding.textKaabaDir.text = getString(R.string.qibla_direction, qiblaDir)
            binding.imgQiblaArrow.isVisible=true
        } else {
            fetchGPS()
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    getBearing()
                    binding.textKaabaDir.text = resources.getString(R.string.msg_permission_granted)
                    binding.textCurrentLoc.text =
                        resources.getString(R.string.msg_permission_granted)
                    binding.imgQiblaArrow.isVisible=false

                }
                return
            }
        }// other 'case' lines to check for other
        // permissions this app might request
    }

    private fun saveFloat(value: Float?) {
        val edit = prefs.edit()
        edit.putFloat(KEY_LOC, value ?: 0f)
        edit.apply()
    }

    private fun retrieveFloat(value: String = KEY_LOC): Float {
        return prefs.getFloat(value, 0f)
    }

    private fun fetchGPS() {
        val result: Double
        gps = GpsTracker(locationManager)
        if (gps.canGetLocation()) {
            val latitude = gps.latitude
            val longitude = gps.longitude
            binding.textCurrentLoc.text = getString(R.string.your_location, latitude, longitude)
            if (latitude < 0.001 && longitude < 0.001) {
                binding.imgQiblaArrow.isVisible=false
                binding.textKaabaDir.text = resources.getString(R.string.location_not_ready)
                binding.textCurrentLoc.text = resources.getString(R.string.location_not_ready)
            } else {
                val longitude2 =
                    qiblaFragmentArgs.locationdata.longitudeOfRegion
                val latitude2 =
                    Math.toRadians( qiblaFragmentArgs.locationdata.latitudeOfRegion)
                val latitude1 = Math.toRadians(latitude)
                val longDiff = Math.toRadians(longitude2 - longitude)
                val y = sin(longDiff) * cos(latitude2)
                val x =
                    cos(latitude1) * sin(latitude2) - sin(latitude1) * cos(latitude2) * cos(longDiff)
                result = (Math.toDegrees(atan2(y, x)) + 360) % 360
                val result2 = result.toFloat()
                saveFloat(value = result2)
                binding.textKaabaDir.text =
                    getString(R.string.qibla_direction, result2)
                binding.imgQiblaArrow.isVisible=true

            }
        } else {

            showSettingsAlert()

            binding.imgQiblaArrow.isVisible=false
            binding.textKaabaDir.text = resources.getString(R.string.pls_enable_location)
            binding.textCurrentLoc.text = resources.getString(R.string.pls_enable_location)

        }
    }

    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     */
    private fun showSettingsAlert() {
        val alertDialog = context?.let { AlertDialog.Builder(it) }
        // Setting Dialog Title
        alertDialog?.setTitle(getString(R.string.gps_settings_title))
        // Setting Dialog Message
        alertDialog?.setMessage(getString(R.string.gps_settings_text))
        // On pressing Settings button
        alertDialog?.setPositiveButton(getString(R.string.button_ok)) { _, _ ->
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
        // on pressing cancel button
        alertDialog?.setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.cancel() }
        // Showing Alert Message
        alertDialog?.show()
    }

    companion object {
        private const val KEY_LOC = "SAVED_LOC"
    }
}