package com.example.bazar.ui.main

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bazar.data.model.Product
import com.example.bazar.databinding.ItemProductBinding

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun populateModel(product: Product) {
            binding.elementName.text = product.productName
        }
    }

    var models: MutableList<Product> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            models.clear()
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.populateModel(models[position])
    }

    override fun getItemCount() = models.size

    private fun delete_item(v: View){
//
//            val builder = AlertDialog.Builder(c)
//            builder.setTitle("Удалить")
//                .setIcon(R.drawable.ic_warning)
//                .setMessage("Вы уверены что хотите оширгенский?")
//                .setPositiveButton("Як") { dialog, _ ->
//                    dialog.dismiss()
//                }
//                .setNegativeButton("Ауа") { dialog, _ ->
//                    elementList.removeAt(adapterPosition)
//                    notifyDataSetChanged()
////                    Toast.makeText(c, "Deleted this Information", Toast.LENGTH_SHORT).show()
//
//                    databaseRef.update("bazarList", FieldValue.arrayRemove(elementList[adapterPosition]))
//
//                    dialog.dismiss()
//                }
//                .create()
//                .show()
//
//        }
    }

    fun addData() {
        //
        notifyDataSetChanged()
    }
}