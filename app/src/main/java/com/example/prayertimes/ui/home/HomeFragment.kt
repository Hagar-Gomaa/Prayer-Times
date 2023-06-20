package com.example.prayertimes.ui.home

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.prayertimes.R
import com.example.prayertimes.databinding.FragmentHomeBinding
import com.example.prayertimes.ui.base.BaseFragment
import com.example.prayertimes.utils.Connection
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.properties.Delegates
import kotlin.time.Duration

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeUiState, HomeUiEvent>() {
    private val homeFragmentArgs: HomeFragmentArgs by navArgs()
    private lateinit var pagerAdapter: HomeViewPagerAdapter
    private lateinit var nextPrayer: String
    private lateinit var address: String
    private lateinit var timeLeft: String
    private lateinit var date: String
    private var connection = false

    private var v: HomeUiState.PrayerTimesUiState? = null
    private var methodId by Delegates.notNull<Int>()
    private var latitude by Delegates.notNull<Double>()
    private var longitude by Delegates.notNull<Double>()

    private lateinit var dateListForWeek: List<String>
    private val prayerTimesListDisplayForWeek = mutableListOf<HomeUiState.PrayerTimesUiState>()
    private val prayerTimesLocalListDisplayForWeek = mutableListOf<HomeUiState.PrayerTimesUiState>()


    override val layoutIdFragment: Int
        get() = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onEvent(event: HomeUiEvent) {
        lifecycleScope.launch {
            when (event) {
                is HomeUiEvent.ClickArrowNext -> clickNextButton()
                is HomeUiEvent.ClickArrowBack -> clickBackButton()
                is HomeUiEvent.ShowSnackBar -> showSnackBar(viewModel.state.value.error.toString())

                else -> {
                    return@launch throw Exception("exception")
                }
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connection = Connection.isOnline(requireContext())
        if (connection) {
            methodId = homeFragmentArgs.methodId
            latitude = homeFragmentArgs.locationData.latitudeOfRegion
            longitude = homeFragmentArgs.locationData.longitudeOfRegion
            address =
                homeFragmentArgs.locationData.city + "," + homeFragmentArgs.locationData.country

            lifecycleScope.launch {
                dateListForWeek = viewModel.state.value.dateForWeek!!
                getPrayerTimesDataForWeek(viewModel.getCurrentDate().toString())
                setViewPagerAdapter(prayerTimesListDisplayForWeek)
                goToQiblaScreen()
                date = viewModel.state.value.date!!
                nextPrayer = viewModel.state.value.nextPrayer!!
                timeLeft = viewModel.state.value.timeLeft
                binding.date.text = date
                binding.location.text = address

            }
        } else if(!Connection.isOnline(requireContext()))
        {  Log.e("Connection ","false")
            lifecycleScope.launch {
              viewModel.getPrayerTimesDatBase().forEach {
                    prayerTimesLocalListDisplayForWeek.add(it.prayerTimes)
                }
                setViewPagerAdapter(prayerTimesLocalListDisplayForWeek )
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun getPrayerTimesDataForWeek(date: String) {
        viewModel.getPrayerTimes(
            date,
            latitude,
            longitude,
            methodId
        )
        val prayerTimes = viewModel.state.value.prayerTimes
        Log.e("Home Fragment", prayerTimes.toString())
        prayerTimesListDisplayForWeek
        if (isNotEmpty(prayerTimes)) prayerTimesListDisplayForWeek.add(prayerTimes) else getPrayerTimesDataForWeek(
            date
        )

    }

    private fun isNotEmpty(it: HomeUiState.PrayerTimesUiState): Boolean {
        return it.asr.isNotEmpty() && it.dhuhr.isNotEmpty() && it.fajr.isNotEmpty() && it.isha.isNotEmpty() && it.maghrib.isNotEmpty() && it.sunrise.isNotEmpty()
    }


    private fun setViewPagerAdapter(list: List<HomeUiState.PrayerTimesUiState>) {
        pagerAdapter = HomeViewPagerAdapter(list)
        binding.recyclerViewPager.adapter = pagerAdapter
        pagerAdapter.notifyDataSetChanged()

    }

    private var i = 0

    @RequiresApi(Build.VERSION_CODES.O)
    private fun clickBackButton() {
        lifecycleScope.launch {
            val currentPosition: Int = binding.recyclerViewPager.currentItem
            if (currentPosition > 0 && prayerTimesListDisplayForWeek.size < 8) {
                binding.date.text = viewModel.state.value.date!!
                getPrayerTimesDataForWeek(date)
                setViewPagerAdapter(prayerTimesListDisplayForWeek)
                binding.recyclerViewPager.currentItem = (currentPosition - 1)
                pagerAdapter.notifyDataSetChanged()

            } else if (currentPosition < 6 && prayerTimesListDisplayForWeek.size == 8) {
                swipeViewPagerBack()
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun clickNextButton() {
        lifecycleScope.launch {
            val currentPosition: Int = binding.recyclerViewPager.currentItem
            if (currentPosition < 6 && prayerTimesListDisplayForWeek.size < 8) {
                date = dateListForWeek[currentPosition + 1]
                binding.date.text = viewModel.state.value.date!!
                getPrayerTimesDataForWeek(date)
                setViewPagerAdapter(prayerTimesListDisplayForWeek)
                binding.recyclerViewPager.currentItem = (currentPosition + 1)
                pagerAdapter.notifyDataSetChanged()
            } else if (currentPosition < 6 && prayerTimesListDisplayForWeek.size == 8) {
                date = dateListForWeek[currentPosition + 1]
                binding.date.text = viewModel.state.value.date!!
                swipeViewPagerNext()
            }

        }

    }


    private fun swipeViewPagerBack() {
        val currentPosition: Int = binding.recyclerViewPager.currentItem
        if (currentPosition > 0) {
            binding.recyclerViewPager.currentItem = (currentPosition - 1)
            pagerAdapter.notifyDataSetChanged()

        }
    }

    private fun swipeViewPagerNext() {
        val currentPosition: Int = binding.recyclerViewPager.currentItem
        if (currentPosition < 6) {
            binding.recyclerViewPager.currentItem = (currentPosition + 1)
            pagerAdapter.notifyDataSetChanged()

        }
    }

    private fun goToQiblaScreen() {
        binding.qibla.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToQiblaFragment(homeFragmentArgs.locationData)
            )
        }
    }


}



