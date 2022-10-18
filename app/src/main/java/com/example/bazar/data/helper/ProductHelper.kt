package com.example.bazar.data.helper

import com.example.bazar.core.Constants
import com.example.bazar.core.Constants.BAZARTEST
import com.example.bazar.data.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class ProductHelper(
    private val db: FirebaseFirestore
) {
    fun addNewProduct(
        productName: String,
        time: Long,
        onSuccess: (msg: String?) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val id = UUID.randomUUID()
            .toString()

        db.collection(BAZARTEST).document(id).set(Product(id, productName, time))
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
    ) {
        db.collection(BAZARTEST).get()
            .addOnSuccessListener {
                val documentsList = it.documents.map {
                    it.toObject(Product::class.java)
                }
                var sortedList = documentsList as MutableList<Product>
                sortedList.sortBy { product ->  product.time }
                onSuccess.invoke(sortedList)

            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun deleteProduct(
        id: String,
        onSuccess: (msg: String?) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        db.collection(Constants.BAZARTEST).document(id).delete()
            .addOnSuccessListener {
                onSuccess.invoke("")
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun setCheckboxState(
        id: String,
        checked: Boolean,
        onSuccess: (bool: Boolean) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        db.collection(BAZARTEST).document(id).update("checked", checked)
            .addOnSuccessListener {
                onSuccess.invoke(checked)
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun deleteSelected(
        onSuccess: (msg: String?) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        db.collection(BAZARTEST).whereEqualTo("checked", true).get()
            .addOnSuccessListener {
                if (it.documents.isNotEmpty()) {
                    it.documents.map { doc ->
                        val product = doc.toObject(Product::class.java)!!
                        db.collection(BAZARTEST).document(product.id).delete()
                        onSuccess.invoke("")
                    }
                } else{
                    onSuccess.invoke("")
                }
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun deleteAllProducts(
        onSuccess: (msg: String?) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        db.collection(BAZARTEST).get()
            .addOnSuccessListener {
                if (it.documents.isNotEmpty()) {
                    it.documents.map { doc ->
                        val product = doc.toObject(Product::class.java)!!
                        db.collection(BAZARTEST).document(product.id).delete()
                            .addOnSuccessListener {
                                onSuccess.invoke("")
                            }
                    }
                } else{
                    onSuccess.invoke("")
                }
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }
}