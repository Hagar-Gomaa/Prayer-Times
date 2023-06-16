package com.example.prayertimes.ui.home.methos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import com.example.prayertimes.R
import com.example.prayertimes.databinding.FragmentHomeBinding
import com.example.prayertimes.databinding.ItemMethodBinding

class SpinnerAdapter (private var list: List<MethodsItem>) : BaseAdapter() {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(p0: Int): Any {
        return list[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {

        val viewHolder: ViewHolder
        var currentView = view
        if (currentView == null) {
            val viewBinding: ItemMethodBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent?.context), R.layout.item_method, parent, false
            )
            viewHolder = ViewHolder(viewBinding)
            currentView = viewHolder.viewBinding.root
            currentView.tag = viewHolder
        } else {viewHolder = currentView.tag as ViewHolder
        }

        val item=list[position]
       viewHolder.viewBinding.item =item
        return currentView
    }

    class ViewHolder(var viewBinding: ItemMethodBinding)
}