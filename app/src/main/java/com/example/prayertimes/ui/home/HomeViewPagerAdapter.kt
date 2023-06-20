package com.example.prayertimes.ui.home


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.example.prayertimes.R
import com.example.prayertimes.databinding.ItemPrayersTimesBinding

class HomeViewPagerAdapter(private val list: List<HomeUiState.PrayerTimesUiState>) :
    PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val binding: ItemPrayersTimesBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.item_prayers_times,
            container,
            false
        )
        val model: HomeUiState.PrayerTimesUiState = list[position]
        binding.item = model
        binding.executePendingBindings()
        container.addView(binding.root)
        notifyDataSetChanged()
        return binding.root
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }
}
