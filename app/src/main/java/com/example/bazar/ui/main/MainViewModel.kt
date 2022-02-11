package com.example.bazar.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bazar.data.helper.ProductHelper
import com.example.bazar.core.Resource

class MainViewModel(private val productHelper: ProductHelper) : ViewModel(){

    private var _product: MutableLiveData<Resource<String>> = MutableLiveData()
    val product: LiveData<Resource<String>> get() = _product

    fun addNewProduct(productName: String) {
        _product.value = Resource.loading()
        productHelper.addNewProduct(
            productName,
            {
                _product.value = Resource.success(productName)
            },
            {
                _product.value = Resource.error(it)
            }
        )
    }

    private var _productList: MutableLiveData<Resource<List<String>>> = MutableLiveData()
    val productList: LiveData<Resource<List<String>>> get() = _productList

    fun allProducts() {

    }

    fun deleteProducts() {

    }
}