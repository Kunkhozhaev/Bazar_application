package com.example.bazar

import android.app.AlertDialog
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class ElementAdapter(val c: Context, val elementList:ArrayList<ElementClass>):RecyclerView.Adapter<ElementAdapter.elementViewHolder>() {
    inner class elementViewHolder(val v: View):RecyclerView.ViewHolder(v) {
        var name: TextView
        var deleteElement: ImageView

        private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val databaseRef = db.collection("Example").document("LTSJJrjjBHAKbl8mRNAC")
//        val check_box:CheckBox

        init {
            name = v.findViewById<TextView>(R.id.element_name)
            deleteElement = v.findViewById(R.id.delete_button)

            deleteElement.setOnClickListener{delete_item(it)}
        }

        /*private fun strike(v: View){
            val v = LayoutInflater.from(c).inflate(R.layout.list_element,null)
            val name = v.findViewById<EditText>(R.id.element_name)

            val position = elementList[adapterPosition]

            check_box.setOnCheckedChangeListener{buttonView, isChecked ->
                if (isChecked){

                }else{
                    name.paintFlags = name.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG
                }
            }
        }*/

        private fun delete_item(v: View){
            
            val builder = AlertDialog.Builder(c)
            builder.setTitle("Удалить")
                .setIcon(R.drawable.ic_warning)
                .setMessage("Вы уверены что хотите оширгенский?")
                .setPositiveButton("Як") { dialog, _ ->
                    dialog.dismiss()
                }
                .setNegativeButton("Ауа") { dialog, _ ->
                    elementList.removeAt(adapterPosition)
                    notifyDataSetChanged()
//                    Toast.makeText(c, "Deleted this Information", Toast.LENGTH_SHORT).show()

                    databaseRef.update("bazarList", FieldValue.arrayRemove(elementList[adapterPosition]))

                    dialog.dismiss()
                }
                .create()
                .show()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): elementViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v  = inflater.inflate(R.layout.list_element,parent,false)
        return elementViewHolder(v)
    }

    override fun onBindViewHolder(holder: elementViewHolder, position: Int) {
        val newList = elementList[position]
        holder.name.text = newList.elementName
    }

    override fun getItemCount(): Int {
        return elementList.size
    }
}