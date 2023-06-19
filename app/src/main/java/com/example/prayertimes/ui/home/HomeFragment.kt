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

    private var v: HomeUiState.PrayerTimesUiState? = null
    private var methodId by Delegates.notNull<Int>()
    private var latitude by Delegates.notNull<Double>()
    private var longitude by Delegates.notNull<Double>()

    private val dateListForWeek = mutableListOf<String>()
    private val prayerTimesListDisplayForWeek = mutableListOf<HomeUiState.PrayerTimesUiState>()

    override val layoutIdFragment: Int
        get() = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModels()
    override fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.ClickArrowNext -> clickNextButton()
            is HomeUiEvent.ClickArrowBack -> clickBackButton()
            is HomeUiEvent.ShowSnackBar -> showSnackBar(viewModel.state.value.error.toString())

            else -> {
                return throw Exception("exception")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        methodId = homeFragmentArgs.methodId
        latitude = homeFragmentArgs.locationData.latitudeOfRegion
        longitude = homeFragmentArgs.locationData.longitudeOfRegion


        Log.e("long", homeFragmentArgs.locationData.latitudeOfRegion.toString())
        Log.e("methodId", homeFragmentArgs.methodId.toString())
        lifecycleScope.launch {
            getDateListForWeek()
            prayerTimesListDisplayForWeek.addAll(getPrayerTimesDataForWeek(dateListForWeek))
            nextPrayer = getNextPrayer(prayerTimesListDisplayForWeek[0])
            timeLeft = calculateTimeDifference(getCurrentTime(), nextPrayerTime)
            binding.nextPray.text = getNextPrayer(prayerTimesListDisplayForWeek[0])

            binding.location.text = homeFragmentArgs.locationData.addressOfRegion
            binding.date.text = getCurrentDate()
            binding.timeLeft.text = timeLeft

            setViewPagerAdapter(prayerTimesListDisplayForWeek)


        }
        goToQiblaScreen()
    }

    private suspend fun getPrayerTimesDataForWeek(list: List<String>): List<HomeUiState.PrayerTimesUiState> {
        val prayerTimesList = mutableListOf<HomeUiState.PrayerTimesUiState>()
        list.forEach {
            viewModel.getPrayerTimes(
                it,
                latitude,
                longitude,
                methodId
            )
            prayerTimesList.add(viewModel.state.value.prayerTimes)
        }
        Log.e("Home Fragment", prayerTimesList.toString())
        return prayerTimesList.filter { isNotEmpty(it) }
    }

    private fun isNotEmpty(it: HomeUiState.PrayerTimesUiState): Boolean {
        return it.asr.isNotEmpty() && it.dhuhr.isNotEmpty() && it.fajr.isNotEmpty() && it.isha.isNotEmpty() && it.maghrib.isNotEmpty() && it.sunrise.isNotEmpty()
    }


    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun getDateListForWeek(): MutableList<String> {
        dateListForWeek.add(getCurrentDate())
        for (i in 1..8) {
            val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, i)
            val nextDate = sdf.format(calendar.time)
            dateListForWeek.add(nextDate)
        }
        return dateListForWeek
    }

    private fun setViewPagerAdapter(list: List<HomeUiState.PrayerTimesUiState>) {
        pagerAdapter = HomeViewPagerAdapter(list)
        binding.recyclerViewPager.adapter = pagerAdapter
        pagerAdapter.notifyDataSetChanged()

    }


    private fun clickBackButton() {
        val currentPosition: Int = binding.recyclerViewPager.currentItem
        if (currentPosition > 0) {
            binding.recyclerViewPager.currentItem = (currentPosition - 1)
            pagerAdapter.notifyDataSetChanged()

        }
    }

    private fun clickNextButton() {
        val currentPosition: Int = binding.recyclerViewPager.currentItem
        if (currentPosition < (binding.recyclerViewPager.adapter?.count?.minus(1)!!)) {
            binding.recyclerViewPager.currentItem = (currentPosition + 1)
            pagerAdapter.notifyDataSetChanged()

        }

    }

    private fun getNextPrayer(prayerTimesUiState: HomeUiState.PrayerTimesUiState): String {
        val currentTime = getCurrentTime()
        return when {
            currentTime < prayerTimesUiState.fajr -> {
                nextPrayerTime = prayerTimesUiState.fajr
                return "Fajr"
            }

            currentTime < prayerTimesUiState.sunrise -> {
                nextPrayerTime = prayerTimesUiState.fajr
                return "Sunrise"
            }

            currentTime < prayerTimesUiState.dhuhr -> {
                nextPrayerTime = prayerTimesUiState.fajr
                return "Dhuhr"
            }

            currentTime < prayerTimesUiState.asr -> {
                nextPrayerTime = prayerTimesUiState.fajr
                return "Asr"
            }

            currentTime < prayerTimesUiState.maghrib -> {
                nextPrayerTime = prayerTimesUiState.fajr
                return "Maghrib"
            }

            currentTime < prayerTimesUiState.isha -> {
                nextPrayerTime = prayerTimesUiState.fajr
                return "Isha"
            }

            else -> ""
        }
    }

    private fun getCurrentTime(): String {
        val dateFormat = SimpleDateFormat("HH:mm")
        val currentTime = Calendar.getInstance().time
        return dateFormat.format(currentTime)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun calculateTimeDifference(start: String, end: String): String {
        // Parse the start and end times into LocalTime objects
        val startTime = LocalTime.parse(start, DateTimeFormatter.ofPattern("HH:mm"))
        val endTime = LocalTime.parse(end, DateTimeFormatter.ofPattern("HH:mm"))

        // Convert the start and end times to minutes
        val startMinutes = startTime.hour * 60 + startTime.minute
        val endMinutes = endTime.hour * 60 + endTime.minute

        // Calculate the difference in minutes
        var diffMinutes = endMinutes - startMinutes
        if (diffMinutes < 0) {
            diffMinutes += 24 * 60 // Add 24 hours if the end time is on the next day
        }

        // Convert the difference back to hours and minutes
        val hours = diffMinutes / 60
        val minutes = diffMinutes % 60
        Log.e("kkkkkkkkkkkk", "${hours}H and ${minutes}min")
        // Format the result as "XH and Ymin"
        return "${hours}H ${minutes}min"
    }


    private fun goToQiblaScreen() {
        binding.qibla.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToQiblaFragment(homeFragmentArgs.locationData)
            )
        }
    }


}