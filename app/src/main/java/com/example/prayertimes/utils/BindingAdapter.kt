package com.example.prayertimes.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.Locale


@BindingAdapter("to12HourFormat")
fun to12HourFormat(view: TextView, time: String?) {
    val sdf24 = SimpleDateFormat("HH:mm", Locale.getDefault())
    val sdf12 = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val time24Format = time?.let { sdf24.parse(it) }
    val convertedTime = time24Format?.let { sdf12.format(it) }
    view.text = convertedTime.toString()
}