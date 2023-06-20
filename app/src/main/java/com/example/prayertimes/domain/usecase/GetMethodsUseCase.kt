package com.example.prayertimes.domain.usecase

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.example.prayertimes.domain.Repository
import com.example.prayertimes.domain.entities.MethodsEntity
import com.example.prayertimes.ui.home.HomeFragmentDirections
import com.example.prayertimes.ui.home.HomeUiState
import com.example.prayertimes.ui.home.HomeViewPagerAdapter
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.properties.Delegates

class GetMethodsUseCase @Inject constructor(private val repository: Repository) {


    suspend operator fun invoke ():MethodsEntity {
        return repository.getMethods()
    }

}