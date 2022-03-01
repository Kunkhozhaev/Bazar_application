package com.example.bazar.data.helper

import com.example.bazar.core.Constants
import com.example.bazar.data.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class ProductHelper(
    private val db: FirebaseFirestore
) {

    fun addNewProduct(
        productName: String,
        onSuccess: (msg: String?) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val id = UUID.randomUUID().toString()

        db.collection(Constants.PRODUCTS).document(id).set(Product(id, productName))
            .addOnSuccessListener {
                onSuccess.invoke("")
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun allProducts(
        onSuccess: (products: MutableList<Product>) -> Unit,
        onFailure: (msg: String?) -> Unit
    ){
        db.collection(Constants.PRODUCTS).get()
            .addOnSuccessListener {
                val documentsList = it.documents.map {
                    it.toObject(Product::class.java)
                }
                onSuccess.invoke(documentsList as MutableList<Product>)

            }
            .addOnFailureListener{
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun deleteProduct(
        id: String,
        onSuccess: (msg: String?) -> Unit,
        onFailure: (msg: String?) -> Unit
    ){
        db.collection(Constants.PRODUCTS).document(id).delete()
            .addOnSuccessListener {
                onSuccess.invoke("")
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }
}