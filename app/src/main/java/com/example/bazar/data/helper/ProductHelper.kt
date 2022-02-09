package com.example.bazar.data.helper

import com.example.bazar.core.Constants
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class ProductHelper(
    private val db: FirebaseFirestore
) {

    fun addNewProduct(
        productName: String,
        onSuccess: () -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val map: MutableMap<String, Any> = mutableMapOf()
        map["productName"] = productName
        db.collection(Constants.PRODUCTS).document(UUID.randomUUID().toString()).set(map)
            .addOnSuccessListener {
                onSuccess.invoke()
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    // todo usi jerge hamme productti alip keletin funkciya jaziw kerek
    fun allProducts(){}

    // todo usi jerge productti oshiretin funkciya jaziw kerek
    fun deleteProduct(){}
}