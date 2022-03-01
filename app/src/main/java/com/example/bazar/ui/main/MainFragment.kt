package com.example.bazar.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.bazar.R
import com.example.bazar.core.*
import com.example.bazar.core.Constants.NO_INTERNET
import com.example.bazar.databinding.FragmentMainBinding
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModel()
    private val adapter = MainAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        binding.addingBtn.onClick {
            showDialog()
        }
        binding.rvProducts.adapter = adapter

        adapter.setOnItemClickListener { it ->
            viewModel.deleteProduct(it.id)
        }
        viewModel.allProducts()
        setUpObserver()
    }

    private fun setUpObserver() {
        //Observer of Adding function

        //Observer of Getting function
        viewModel.productList.observe(requireActivity()) {
            when (it.status) {
                ResourceState.LOADING -> {
                    showProgress()
                }
                ResourceState.SUCCESS -> {
                    hideProgress()
                    adapter.models = it.data!!
                }
                ResourceState.ERROR -> {
                    toast(it.message!!)
                    hideProgress()
                }
                ResourceState.NETWORK_ERROR -> {
                    hideProgress()
                    toast(NO_INTERNET)
                }
            }
        }

        // Observer of Deleting function
        viewModel.productDelete.observe(requireActivity()) {
            when (it.status) {
                ResourceState.LOADING -> {
                    showProgress()
                }
                ResourceState.SUCCESS -> {
                    hideProgress()
                    toast("Product is deleted from Firestore")
                    viewModel.allProducts()
                }
                ResourceState.ERROR -> {
                    toast(it.message!!)
                    hideProgress()
                }
                ResourceState.NETWORK_ERROR -> {
                    hideProgress()
                    toast(NO_INTERNET)
                }
            }
        }
    }

    fun refresh() {
        viewModel.allProducts()
    }

    private fun showDialog() {
        AddDialogFragment(this)
    }
}