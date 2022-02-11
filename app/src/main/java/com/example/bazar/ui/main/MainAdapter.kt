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
        fun populateModel(product: Product, position: Int) {
            binding.elementName.text = product.productName
            binding.deleteButton.onClick {
                onItemClick.invoke(product, position)
            }
        }
    }

    private var onItemClick: (product: Product, position: Int) -> Unit = {_, _ ->}
    fun setOnItemClickListener(onItemClick: (product: Product, position: Int) -> Unit) {
        this.onItemClick = onItemClick
    }

    var models: MutableList<Product> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            models.clear()
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.populateModel(models[position], position)
    }

    override fun getItemCount() = models.size
}