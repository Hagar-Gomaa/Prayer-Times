package com.example.prayertimes.ui.home

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
    private lateinit var nextPrayerTime: String
    private lateinit var timeLeft: String
    private lateinit var date: String


    private var v: HomeUiState.PrayerTimesUiState? = null
    private var methodId by Delegates.notNull<Int>()
    private var latitude by Delegates.notNull<Double>()
    private var longitude by Delegates.notNull<Double>()

    private lateinit var dateListForWeek:List<String>
    private val prayerTimesListDisplayForWeek = mutableListOf<HomeUiState.PrayerTimesUiState>()

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
        methodId = homeFragmentArgs.methodId
        latitude = homeFragmentArgs.locationData.latitudeOfRegion
        longitude = homeFragmentArgs.locationData.longitudeOfRegion
        lifecycleScope.launch {
            date = viewModel.state.value.date!!
            dateListForWeek=viewModel.state.value.dateForWeek!!
            getPrayerTimesDataForWeek(date)
            nextPrayer = viewModel.state.value.nextPrayer!!
            nextPrayerTime =viewModel.state.value.nextPrayerTime!!
            timeLeft = viewModel.state.value.timeLeft!!
            binding.nextPray.text = nextPrayer
            binding.location.text = homeFragmentArgs.locationData.addressOfRegion
            binding.timeLeft.text = timeLeft
            binding.date.text = date
            setViewPagerAdapter(prayerTimesListDisplayForWeek)


        }
        goToQiblaScreen()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun getPrayerTimesDataForWeek(date: String) {
            viewModel.getPrayerTimes(
                date,
                latitude,
                longitude,
                methodId
            )
           val m =viewModel.state.value.prayerTimes
                 Log.e("Home Fragment", m.toString())
        prayerTimesListDisplayForWeek
       if (isNotEmpty(m)) prayerTimesListDisplayForWeek.add(m) else getPrayerTimesDataForWeek(date)

    }

    private fun isNotEmpty(it: HomeUiState.PrayerTimesUiState): Boolean {
        return it.asr.isNotEmpty() && it.dhuhr.isNotEmpty() && it.fajr.isNotEmpty() && it.isha.isNotEmpty() && it.maghrib.isNotEmpty() && it.sunrise.isNotEmpty()
    }


    private fun setViewPagerAdapter(list: List<HomeUiState.PrayerTimesUiState>) {
        pagerAdapter = HomeViewPagerAdapter(list)
        binding.recyclerViewPager.adapter = pagerAdapter
        pagerAdapter.notifyDataSetChanged()

    }
    private var i =0

    @RequiresApi(Build.VERSION_CODES.O)
    private  fun clickBackButton() {
        lifecycleScope.launch {
            val currentPosition: Int = binding.recyclerViewPager.currentItem
            if (currentPosition > 0&&prayerTimesListDisplayForWeek.size<8) {
                date=dateListForWeek[currentPosition-1]
                binding.date.text=date
                getPrayerTimesDataForWeek(date)
                setViewPagerAdapter(prayerTimesListDisplayForWeek)
                binding.recyclerViewPager.currentItem = (currentPosition - 1)
                pagerAdapter.notifyDataSetChanged()

            }else if(currentPosition < 6&& prayerTimesListDisplayForWeek.size==8){
                swipeViewPagerBack()
            }
        }

    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun clickNextButton() {
        lifecycleScope.launch {
            val currentPosition: Int = binding.recyclerViewPager.currentItem
            if (currentPosition < 6&&prayerTimesListDisplayForWeek.size<8) {
                date=dateListForWeek[currentPosition+1]
                binding.date.text=date
                getPrayerTimesDataForWeek(date)
                setViewPagerAdapter(prayerTimesListDisplayForWeek)
                binding.recyclerViewPager.currentItem = (currentPosition + 1)
                pagerAdapter.notifyDataSetChanged()
            }else if(currentPosition < 6&& prayerTimesListDisplayForWeek.size==8){
                date=dateListForWeek[currentPosition+1]
                binding.date.text=date
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
        if (currentPosition < 6 ) {
            binding.recyclerViewPager.currentItem = (currentPosition + 1)
            pagerAdapter.notifyDataSetChanged()

        }}
        private fun goToQiblaScreen() {
            binding.qibla.setOnClickListener {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToQiblaFragment(homeFragmentArgs.locationData)
                )
            }
        }



    }



