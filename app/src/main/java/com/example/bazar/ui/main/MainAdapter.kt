package com.example.bazar.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bazar.core.onClick
import com.example.bazar.data.model.Product
import com.example.bazar.databinding.ItemProductBinding

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun populateModel(product: Product) {
            binding.apply {
                elementName.text = product.productName
                checkBox.isChecked = product.checked
                deleteButton.onClick {
                    onItemClick.invoke(product)
                }
                checkBox.onClick {
                    onCheckBoxClick.invoke(product, checkBox.isChecked)
                }
            }
        }
    }

    private var onItemClick: (product: Product) -> Unit = {}
    fun setOnItemClickListener(onItemClick: (product: Product) -> Unit) {
        this.onItemClick = onItemClick
    }

    private var onCheckBoxClick: (product: Product, state: Boolean) -> Unit = {_, _->}
    fun setOnCheckBoxClickListener(onCheckBoxClick: (product: Product, state: Boolean) -> Unit) {
        this.onCheckBoxClick = onCheckBoxClick
    }

    var models: MutableList<Product> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            // After applying changes to firestore,
            // it clears the whole list of products in the device.
            // Then retrieves new changed data from firestore and shows to user again
            models.clear()
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.populateModel(models[position])
    }

    override fun getItemCount() = models.size
}