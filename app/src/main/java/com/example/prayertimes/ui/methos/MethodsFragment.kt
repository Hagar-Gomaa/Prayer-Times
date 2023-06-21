package com.example.prayertimes.ui.methos

import android.Manifest
import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.prayertimes.R
import com.example.prayertimes.databinding.FragmentMethodsBinding
import com.example.prayertimes.ui.base.BaseFragment
import com.example.prayertimes.ui.home.HomeUiEvent
import com.example.prayertimes.ui.home.HomeUiState
import com.example.prayertimes.ui.home.HomeViewModel
import com.example.prayertimes.ui.home.Location.LocationData
import com.example.prayertimes.utils.Connection
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Locale

@AndroidEntryPoint
class MethodsFragment : BaseFragment<FragmentMethodsBinding, HomeUiState, HomeUiEvent>() {
    private lateinit var spinnerAdapter: SpinnerAdapter
    private lateinit var spinnerList: List<MethodsItem>

    override fun onEvent(event: HomeUiEvent) {
        TODO("Not yet implemented")
    }

    private lateinit var selectedItem: MethodsItem

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationData: LocationData
    private lateinit var address: List<Address>
    private var methodId: Int? = null
    override val layoutIdFragment: Int
        get() = R.layout.fragment_methods
    override val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Connection.isOnline(requireContext())) {
            requestPermissionLocation()
            setData()
            selectMethod()
            submit()
        } else if (!Connection.isOnline(requireContext())) {

            lifecycleScope.launch {
                viewModel.getPrayerTimesDatBase().first().let {
                    locationData = LocationData(
                        it.latitude!!.toDouble(),
                        it.longitude!!.toDouble(),
                        it.address ?: "",
                        it.address ?: "",
                        it.address ?: ""
                    )
                    methodId = it.methodId!!.toInt()
                }
                findNavController().navigate(
                    MethodsFragmentDirections.actionMethodsFragmentToHomeFragment(
                        methodId!!,
                        locationData
                    )
                )
//                spinnerList = listOf(MethodsItem(item = Pair(2, "qater")))
//                setSpinnerAdapter(spinnerList)
//                binding.spinner.adapter = spinnerAdapter
//                selectMethod()
//                submit()

            }
        }
    }

    private fun setData() {
        collectLatest {
            viewModel.getMethods()
            viewModel.state.collect { state ->
                state.methods.let {
                    spinnerList = getSpinnerList(it)
                    Log.e("Spinner List ", spinnerList.toString())
                    setSpinnerAdapter(spinnerList)
                    binding.spinner.adapter = spinnerAdapter
                }
                state.error?.last()?.let { showSnackBar(it) }
            }
        }
    }

    private fun setSpinnerAdapter(list: List<MethodsItem>) {
        spinnerAdapter = SpinnerAdapter(list)
    }

    private fun getSpinnerList(methodsUiState: HomeUiState.MethodsUiState): List<MethodsItem> {
        return listOf(
            MethodsItem(methodsUiState.qatar),
            MethodsItem(methodsUiState.mwl),
            MethodsItem(methodsUiState.egypt),
            MethodsItem(methodsUiState.karachi),
            MethodsItem(methodsUiState.isna),
            MethodsItem(methodsUiState.moonsighting),
            MethodsItem(methodsUiState.jafari),
            MethodsItem(methodsUiState.tehran),
            MethodsItem(methodsUiState.custom),
            MethodsItem(methodsUiState.singapore),
            MethodsItem(methodsUiState.france),
            MethodsItem(methodsUiState.turkey),
            MethodsItem(methodsUiState.russia),
            MethodsItem(methodsUiState.qatar),
            MethodsItem(methodsUiState.kuwait),
            MethodsItem(methodsUiState.maka),
            MethodsItem(methodsUiState.gulf),
            MethodsItem(methodsUiState.dubai)
        )
    }

    private fun submit() {
        binding.submitButton.setOnClickListener {
            if (locationData.addressOfRegion.isNotEmpty() && selectedItem.item.second.isNotEmpty() ) {
                methodId = selectedItem.item.first

                findNavController().navigate(
                    MethodsFragmentDirections.actionMethodsFragmentToHomeFragment(
                        methodId!!,
                        locationData
                    )
                )
            }
            else {
                requestPermissionLocation()
                Toast.makeText(
                    requireContext(), "Please, check Your Network ", Toast.LENGTH_SHORT
                ).show()
            }

        }

    }

    private fun requestPermissionLocation() {
        multiplePermissionsContract.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }


    @SuppressLint("MissingPermission")
    fun getLocation() {
        try {
            fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(requireActivity())
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    Log.d("TAG", "getLocation: ")

                    putLocationData(it)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()

        }
    }

    private fun putLocationData(location: Location) {
        try {
            Log.d("TAG", "putLocationData: ")

            val geocoder = Geocoder(requireContext(), Locale.getDefault())
            address =
                geocoder.getFromLocation(location.latitude, location.longitude, 1) as List<Address>

            address[0].apply {
                locationData = LocationData(
                    latitudeOfRegion = latitude,
                    longitudeOfRegion = longitude,
                    country = countryName,
                    city = locality,
                    addressOfRegion = getAddressLine(0)
                )
//                    viewModel.getAddress(getAddressLine(0))
                Log.e("Methods Fragment", locationData.toString())
            }
        } catch (e: Exception) {
        }


    }

    private val multiplePermissionsContract = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsStatusMap ->
        if (!permissionsStatusMap.containsValue(false)) {
            // all permissions are accepted
            getLocation()
        } else {
            Toast.makeText(
                requireContext(), "Location locked in this Device", Toast.LENGTH_SHORT
            ).show()
        }
    }


    private fun selectMethod() {
        binding.spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedItem = spinnerList[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

    }
}