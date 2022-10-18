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
            swipeToRefresh.setOnRefreshListener {
                refresh()
                swipeToRefresh.isRefreshing = false
            }
            toolbar.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.add_product -> {
                        showDialog()
                        true
                    }
                    R.id.delete -> {
                        showMenu(toolbar.findViewById(R.id.delete))
                        true
                    }
                    else -> false
                }
            }
        }
        adapter.setOnItemClickListener { it ->
            viewModel.deleteProduct(it.id)
        }

        adapter.setOnCheckBoxClickListener { product, state ->
            viewModel.setCheckboxState(product.id, state)
        }

        viewModel.allProducts()
        setUpObserver()
    }

    private fun setUpObserver() {
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
        viewModel.productDelete.observe(requireActivity()) {
            when (it.status) {
                ResourceState.LOADING -> {
                    showProgress()
                }
                ResourceState.SUCCESS -> {
                    hideProgress()
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
                    //toast("Все отмеченные удалены")
                    true
                }
                R.id.deleteAll -> {
                    viewModel.deleteAllProducts()
                    //toast("Весь список удален")
                    true
                }
                else -> false
            }
        }

        popUp.inflate(R.menu.popup)

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