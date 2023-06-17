package com.example.prayertimes.ui.home

import com.example.prayertimes.ui.base.BaseFragment
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.prayertimes.R
import com.example.prayertimes.databinding.FragmentHomeBinding
import com.example.prayertimes.ui.home.methos.MethodsItem
import com.example.prayertimes.ui.home.methos.SpinnerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeUiState, HomeUiEvent>() {
    private val homeFragmentArgs : HomeFragmentArgs by navArgs()

    override val layoutIdFragment: Int
        get() = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModels()
    override fun onEvent(event: HomeUiEvent) {
        TODO("Not yet implemented")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
              Log.e("long",homeFragmentArgs.locationData.latitudeOfRegion.toString())
        Log.e("methodId",homeFragmentArgs.methodId.toString())

    }

}