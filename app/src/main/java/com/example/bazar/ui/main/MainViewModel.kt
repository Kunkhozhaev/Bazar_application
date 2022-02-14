package com.example.bazar.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bazar.data.helper.ProductHelper
import com.example.bazar.core.Resource
import com.example.bazar.data.model.Product

class MainViewModel(private val productHelper: ProductHelper) : ViewModel(){

    private var _product: MutableLiveData<Resource<String>> = MutableLiveData()
    val product: LiveData<Resource<String>> get() = _product

    fun addNewProduct(productName: String) {
        _product.value = Resource.loading()
        productHelper.addNewProduct(
            productName,
            {
                _product.value = Resource.success("")
            },
            {
                _product.value = Resource.error(it)
            }
        )
    }

    private var _productList: MutableLiveData<Resource<MutableList<Product>>> = MutableLiveData()
    val productList: LiveData<Resource<MutableList<Product>>> get() = _productList

    fun allProducts() {
        _productList.value = Resource.loading()
        productHelper.allProducts(
            {
                _productList.value = Resource.success(it)
            },
            {
                _productList.value = Resource.error(it)
            }
        )

    }

    private var _productDelete: MutableLiveData<Resource<String>> = MutableLiveData()
    val productDelete: LiveData<Resource<String>> get() = _productDelete

    fun deleteProduct(productName: String) {
        _productDelete.value = Resource.loading()
        productHelper.deleteProduct(
            productName,
            {
                _productDelete.value = Resource.success("")
            },
            {
                _productDelete.value = Resource.error(it)
            }
        )
    }
}