package com.example.bazar.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.bazar.R
import com.example.bazar.core.onClick
import com.example.bazar.databinding.ItemAddBinding
import org.koin.android.viewmodel.ext.android.viewModel

class Dialog : DialogFragment() {

    private lateinit var binding: ItemAddBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        dialog!!.setCanceledOnTouchOutside(false)
        return inflater.inflate(R.layout.item_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ItemAddBinding.bind(view)
        binding.apply {
            btnAdd.onClick {
                val productName = productName.text.toString()
                viewModel.addNewProduct(productName)
                dialog!!.dismiss()
            }
            cancel.onClick {
                dialog!!.dismiss()
            }
        }
    }
}