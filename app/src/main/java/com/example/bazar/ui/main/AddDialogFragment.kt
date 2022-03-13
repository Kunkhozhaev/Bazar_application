package com.example.bazar.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bazar.R
import com.example.bazar.core.*
import com.example.bazar.databinding.ItemAddBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.viewmodel.ext.android.viewModel

class AddDialogFragment(private val fragment: MainFragment) : BottomSheetDialogFragment() {

    private var savedViewInstance: View? = null
    private lateinit var binding: ItemAddBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        setUpAddObserver()
        return if (savedInstanceState != null) {
            savedViewInstance
        } else {
            savedViewInstance = inflater.inflate(R.layout.item_add, container, true)
            savedViewInstance
        }
    }

    init {
        show(fragment.requireActivity().supportFragmentManager, "tag")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ItemAddBinding.bind(view)
        setUpAddObserver()
        binding.apply {
            btnAdd.onClick {
                val productName = productName.text.toString()
                viewModel.addNewProduct(productName) //Add a new product

            }
            cancel.onClick {
                dialog!!.dismiss()
            }
        }
    }

    // Observer of a ProductHelper.addNewProduct()
    private fun setUpAddObserver(){
        viewModel.productAdd.observe(requireActivity()) {
            when (it.status) {
                ResourceState.LOADING -> {
                    //showProgress()
                }
                ResourceState.SUCCESS -> {
                    //hideProgress()
                    fragment.refresh()
                    dismiss()
                    toast("Продукт добавлен")
                }
                ResourceState.ERROR -> {
                    toast(it.message!!)
                    hideProgress()
                }
                ResourceState.NETWORK_ERROR -> {
                    hideProgress()
                    toast(Constants.NO_INTERNET)
                }
            }
        }
    }
}