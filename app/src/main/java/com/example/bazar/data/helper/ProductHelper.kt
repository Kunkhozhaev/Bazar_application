package com.example.bazar.data.helper

import com.example.bazar.core.Constants
import com.example.bazar.data.model.Product
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class ProductHelper(
    private val db: FirebaseFirestore
) {

    fun addNewProduct(
        productName: String,
        onSuccess: () -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        db.collection(Constants.PRODUCTS).document("productList")
            .update("products", FieldValue.arrayUnion(productName))
            .addOnSuccessListener {
                onSuccess.invoke()
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun allProducts(
        onSuccess: (products: MutableList<Product>) -> Unit,
        onFailure: (msg: String?) -> Unit
    ){
        db.collection(Constants.PRODUCTS).document("productList").get()
            .addOnSuccessListener {
                val resource = it.data!!.values
                val itemList = mutableListOf<Product>()
                resource.forEach {
                    it.toString().split(",").forEach{
                        itemList.add(Product(it))
                    }
                }

                onSuccess.invoke(itemList)
            }
            .addOnFailureListener{
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun deleteProduct(
        productList: MutableList<Product>,
        position: Int,
        onSuccess: (msg: String?) -> Unit,
        onFailure: (msg: String?) -> Unit
    ){
        db.collection(Constants.PRODUCTS).document("productList")
            .update("products", FieldValue.arrayRemove(productList[position]))
            .addOnSuccessListener {
                onSuccess.invoke("")
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }
}
//
