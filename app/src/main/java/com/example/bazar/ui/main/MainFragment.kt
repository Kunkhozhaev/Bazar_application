package com.example.bazar.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.bazar.R
import com.example.bazar.core.Constants.NO_INTERNET
import com.example.bazar.core.hideProgress
import com.example.bazar.core.onClick
import com.example.bazar.core.showProgress
import com.example.bazar.core.toast
import com.example.bazar.databinding.FragmentMainBinding
import org.koin.android.viewmodel.ext.android.viewModel
import com.example.bazar.core.ResourceState
import com.example.bazar.databinding.ItemAddBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModel()
    private val adapter = MainAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        binding.addingBtn.onClick {
            findNavController().navigate(R.id.action_mainFragment_to_dialog)
        }
        setUpObserver()
        viewModel.allProducts()
    }

    private fun setUpObserver() {
        viewModel.product.observe(requireActivity()) {
            when (it.status) {
                ResourceState.LOADING -> {
                    showProgress()
                }
                ResourceState.SUCCESS -> {
                    hideProgress()
                    toast("Product added to Firestore")
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

    private fun setUpObserverForAllData() {
        viewModel.productList.observe(requireActivity()) {
            when (it.status) {
                ResourceState.LOADING -> {
                    showProgress()
                }
                ResourceState.SUCCESS -> {
                    hideProgress()

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

    // todo usi jerge setUpObservers() degen funkciya jaziw kerek

    private fun addInfo() {
//        val addDialog = AlertDialog.Builder(requireContext())
//        addDialog.setView(v)
//        addDialog.setPositiveButton("Ок") { dialog, _ ->
//            val names = elementName.text.toString()
//            elementList.add(ElementClass(names))
//            mainAdapter.notifyDataSetChanged()
////            Toast.makeText(this,"Продукт добавлен в список", Toast.LENGTH_SHORT).show(
//
//            databaseRef.update("bazarList", FieldValue.arrayUnion(names))
//
//            dialog.dismiss()
//        }
//        addDialog.setNegativeButton("Отменить") { dialog, _ ->
//            dialog.dismiss()
////            Toast.makeText(this,"Отмена", Toast.LENGTH_SHORT).show()
//
//        }
//        addDialog.create()
//        addDialog.show()
    }
}