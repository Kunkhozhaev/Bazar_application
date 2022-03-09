package com.example.bazar.data.helper

import com.example.bazar.core.Constants
import com.example.bazar.core.Constants.BAZARTEST
import com.example.bazar.data.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class ProductHelper(
    private val db: FirebaseFirestore //Firebase Firestore reference
) {
    //Adds New product to Firestore received from `productName` edittext
    fun addNewProduct(
        productName: String,
        onSuccess: (msg: String?) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        //Creating a new document for an each new product
        val id = UUID.randomUUID()
            .toString() //Create new random id used as a document name in `Products` collection

        db.collection(BAZARTEST).document(id).set(Product(id, productName))
            .addOnSuccessListener {
                onSuccess.invoke("")
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    // Receives all documents list and its content, i.e. productNames
    fun allProducts(
        onSuccess: (products: MutableList<Product>) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        db.collection(BAZARTEST).get()
            .addOnSuccessListener {
                //Casting Firestore documents list to Product Class object
                val documentsList = it.documents.map {
                    it.toObject(Product::class.java)
                }
                onSuccess.invoke(documentsList as MutableList<Product>)

            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    //Deletes a document, i.e. productName using its id
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

    //Updates checkbox state of a current productName
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

    //Delete selected
    fun deleteSelected(
        onSuccess: (msg: String?) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        db.collection(BAZARTEST).whereEqualTo("checked", true).get()
            .addOnSuccessListener {
                it.documents.map { doc ->
                    val product = doc.toObject(Product::class.java)!!
                    db.collection(BAZARTEST).document(product.id).delete()
                    onSuccess.invoke("")
                }
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    // All items deleted
    fun deleteAllProducts(
        onSuccess: (msg: String?) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        db.collection(BAZARTEST).get()
            .addOnSuccessListener {
                it.documents.map { doc ->
                    val product = doc.toObject(Product::class.java)!!
                    db.collection(BAZARTEST).document(product.id).delete()
                        .addOnSuccessListener {
                            onSuccess.invoke("")
                        }
                }
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }
}