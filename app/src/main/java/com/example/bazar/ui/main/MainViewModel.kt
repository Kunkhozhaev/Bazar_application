package com.example.bazar.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bazar.data.helper.ProductHelper
import com.example.bazar.core.Resource
import com.example.bazar.data.model.Product

class MainViewModel(private val productHelper: ProductHelper) : ViewModel() {

    //Creating LiveData for all processes to observe them in MainFragment & AddDialogFragment:
    //Adding, Getting all data, Deleting, Changing checkbox state, Deleting Selected and Deleting all data
    private var _productAdd: MutableLiveData<Resource<String>> = MutableLiveData()
    val productAdd: LiveData<Resource<String>> get() = _productAdd

    fun addNewProduct(productName: String, time: Long) {
        _productAdd.value = Resource.loading()
        productHelper.addNewProduct(
            productName,
            time,
            {
                _productAdd.value = Resource.success("")
            },
            {
                _productAdd.value = Resource.error(it)
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

    fun deleteProduct(id: String) {
        _productDelete.value = Resource.loading()
        productHelper.deleteProduct(
            id,
            {
                _productDelete.value = Resource.success("")
            },
            {
                _productDelete.value = Resource.error(it)
            }
        )
    }

    private var _checkboxState: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    val checkboxState: LiveData<Resource<Boolean>> get() = _checkboxState

    fun setCheckboxState(id: String, checked: Boolean) {
        _checkboxState.value = Resource.loading()
        productHelper.setCheckboxState(
            id,
            checked,
            {
                _checkboxState.value = Resource.success(it)
            },
            {
                _checkboxState.value = Resource.error(it)
            }
        )
    }

    private var _selectedProducts: MutableLiveData<Resource<String?>> = MutableLiveData()
    val selectedProducts: LiveData<Resource<String?>> get() = _selectedProducts

    fun deleteSelected() {
        _selectedProducts.value = Resource.loading()
        productHelper.deleteSelected(
            {
                _selectedProducts.value = Resource.success(it)
            },
            {
                _selectedProducts.value = Resource.error(it)
            }
        )
    }

    private var _deleteAllProducts: MutableLiveData<Resource<String?>> = MutableLiveData()
    val deleteAllProducts: LiveData<Resource<String?>> get() = _deleteAllProducts

    fun deleteAllProducts() {
        _selectedProducts.value = Resource.loading()
        productHelper.deleteAllProducts(
            {
                _selectedProducts.value = Resource.success(it)
            },
            {
                _selectedProducts.value = Resource.error(it)
            }
        )
    }

}