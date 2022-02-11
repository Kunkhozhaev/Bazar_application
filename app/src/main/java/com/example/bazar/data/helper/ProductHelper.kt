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
                val resource = it.data
                val itemList = mutableListOf<Product>()
                resource!!.forEach {
                    itemList.add(Product(it.key))
                }

                onSuccess.invoke(itemList)
            }
            .addOnFailureListener{
                onFailure.invoke(it.localizedMessage)
            }
    }

    // todo usi jerge productti oshiretin funkciya jaziw kerek
    fun deleteProduct(
        productName: String,
        onSuccess: () -> Unit,
        onFailure: (msg: String?) -> Unit
    ){
        db.collection(Constants.PRODUCTS).document("productList")
            .update("products", FieldValue.arrayRemove(productName))
            .addOnSuccessListener {
                onSuccess.invoke()
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }
}