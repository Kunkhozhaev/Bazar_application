package com.example.bazar.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
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
        binding.apply {
            rvProducts.adapter = adapter

            addingBtn.onClick { showDialog() }

            swipeToRefresh.setOnRefreshListener {
                refresh()
                swipeToRefresh.isRefreshing = false
            }

            popUpImg.setOnClickListener {
                showMenu(it)
            }
        }
        //When delete icon is clicked
        adapter.setOnItemClickListener { it ->
            viewModel.deleteProduct(it.id)
        }

        //When checkbox state is updated
        adapter.setOnCheckBoxClickListener { product, state ->
            viewModel.setCheckboxState(product.id, state)
        }

        viewModel.allProducts() // Show all products from Firestore
        setUpObserver()
    }

    private fun setUpObserver() {
        //Observer of ProductHelper.allProducts() function
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
        // Observer of ProductHelper.deleteProduct() function
        viewModel.productDelete.observe(requireActivity()) {
            when (it.status) {
                ResourceState.LOADING -> {
                    //showProgress()
                }
                ResourceState.SUCCESS -> {
                    //hideProgress()
                    toast("Продукт удален")
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
        //Observer of ProductHelper.setCheckboxState() function
        viewModel.checkboxState.observe(requireActivity()) {
            when (it.status) {
                ResourceState.LOADING -> {
                    //showProgress()
                }
                ResourceState.SUCCESS -> {
                    //hideProgress()
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

        viewModel.selectedProducts.observe(requireActivity()) {
            when (it.status) {
                ResourceState.LOADING -> {
                    showProgress()
                }
                ResourceState.SUCCESS -> {
                    viewModel.allProducts()
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

        viewModel.deleteAllProducts.observe(requireActivity()) {
            when (it.status) {
                ResourceState.LOADING -> {
                    showProgress()
                }
                ResourceState.SUCCESS -> {
                    viewModel.allProducts()
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

    fun refresh() {
        viewModel.allProducts()
    }

    private fun showDialog() {
        AddDialogFragment(this)
    }

    private fun showMenu(v: View) {
        val popUp = PopupMenu(context, v)
        popUp.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.deleteSelected -> {
                    viewModel.deleteSelected()
                    toast("All selected are deleted")
                    true
                }
                R.id.deleteAll -> {
                    viewModel.deleteAllProducts()
                    toast("All data deleted")
                    true
                }
                else -> false
            }
        }

        popUp.inflate(R.menu.popup)

        //try..catch to show icons in popUp menu elements. Don't ask how it works ¯\_(ツ)_/¯
        try {
            val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
            fieldMPopup.isAccessible = true
            val mPopup = fieldMPopup.get(popUp)
            mPopup.javaClass
                .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(mPopup, true)
        } catch (e: Exception) {
            Log.e("Main", "Error in showing popUp menu icons", e)
        } finally {
            popUp.show() //Showing the menu
        }
    }

}