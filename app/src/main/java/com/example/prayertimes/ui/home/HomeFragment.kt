package com.example.prayertimes.ui.home

import com.example.prayertimes.ui.base.BaseFragment
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.example.prayertimes.R
import com.example.prayertimes.databinding.FragmentHomeBinding
import com.example.prayertimes.ui.home.methos.MethodsItem
import com.example.prayertimes.ui.home.methos.SpinnerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeUiState, HomeUiEvent>() {
    private lateinit var spinnerAdapter: SpinnerAdapter
    private lateinit var spinnerList: List<MethodsItem>

    override val layoutIdFragment: Int
        get() = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        setAlertDialogue()
    }

    private fun setAlertDialogue() {

    }


    override fun onEvent(event: HomeUiEvent) {
        TODO("Not yet implemented")
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
}