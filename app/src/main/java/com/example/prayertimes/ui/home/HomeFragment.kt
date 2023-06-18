package com.example.prayertimes.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.prayertimes.R
import com.example.prayertimes.databinding.FragmentHomeBinding
import com.example.prayertimes.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.properties.Delegates

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeUiState, HomeUiEvent>() {
    private val homeFragmentArgs: HomeFragmentArgs by navArgs()
    private lateinit var pagerAdapter: HomeViewPagerAdapter
    private var methodId by Delegates.notNull<Int>()
    private var latitude by Delegates.notNull<Double>()
    private var longitude by Delegates.notNull<Double>()

    private val dateListForWeek = mutableListOf<String>()
    private val prayerTimesListForWeek = mutableListOf<HomeUiState.PrayerTimesUiState>()

    override val layoutIdFragment: Int
        get() = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModels()
    override fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.ClickArrowNext -> clickNextButton()
            is HomeUiEvent.ClickArrowBack -> clickBackButton()
            is HomeUiEvent.ShowSnackBar -> showSnackBar(viewModel.state.value.error.toString())

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        methodId = homeFragmentArgs.methodId
        latitude = homeFragmentArgs.locationData.latitudeOfRegion
        longitude = homeFragmentArgs.locationData.longitudeOfRegion
        Log.e("long", homeFragmentArgs.locationData.latitudeOfRegion.toString())
        Log.e("methodId", homeFragmentArgs.methodId.toString())
        getDateListForWeek()
        getPrayerTimesDataForWeek(dateListForWeek)
        setViewPagerAdapter(prayerTimesListForWeek)
    }

    private fun getPrayerTimesDataForWeek(list: List<String>): List<HomeUiState.PrayerTimesUiState> {
        lifecycleScope.launch {
            list.forEach {
                viewModel.getPrayerTimes(
                    it,
                    latitude,
                    longitude,
                    methodId
                )
                prayerTimesListForWeek.add(viewModel.state.value.prayerTimes)
            }
            Log.e("Home Fragment", prayerTimesListForWeek.toString())
        }
        return prayerTimesListForWeek
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun getDateListForWeek(): MutableList<String> {
        dateListForWeek.add(getCurrentDate())
        for (i in 1..6) {
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

        }
    }

    private fun clickNextButton() {
        val currentPosition: Int = binding.recyclerViewPager.currentItem
        if (currentPosition < (binding.recyclerViewPager.adapter?.count?.minus(1)!!)) {
            binding.recyclerViewPager.currentItem = (currentPosition + 1)
        }

    }
}