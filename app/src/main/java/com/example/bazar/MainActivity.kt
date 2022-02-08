package com.example.bazar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.sax.Element
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var addsBtn:FloatingActionButton
    private lateinit var recv:RecyclerView
    private lateinit var elementList:ArrayList<ElementClass>
    private lateinit var elementAdapter:ElementAdapter

    private var db:FirebaseFirestore = FirebaseFirestore.getInstance()
    val databaseRef = db.collection("Example").document("LTSJJrjjBHAKbl8mRNAC")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        elementList = ArrayList()

        addsBtn = findViewById(R.id.addingBtn)
        recv = findViewById(R.id.mRecycler)

        elementAdapter = ElementAdapter(this, elementList)

        recv.layoutManager = LinearLayoutManager(this)
        recv.adapter = elementAdapter
        addsBtn.setOnClickListener{ addInfo() }

//        loadContent()
    }

    /*private fun loadContent(){
        val path:File = getApplicationContext().filesDir
        var readFrom = File(path, "list.txt")
        var content = byteArrayOf(readFrom.length().toByte())

        var stream: FileInputStream? = null
        try{
            stream = FileInputStream(readFrom)
            stream.read(content)

            var s = String(content)
            s = s.substring(1, s.length - 1)

            var splitted = s.split(",")
            var itemStringList = ArrayList(Arrays.asList(splitted))
            for(item in itemStringList)
                elementList.add(ElementClass(item.toString()))

            elementAdapter = ElementAdapter(this, elementList)
            elementAdapter.notifyDataSetChanged()
        }catch (e:Exception){
            "Проблемы с файлом"
        }

    }*/

    private fun addInfo(){
        val inflter = LayoutInflater.from(this)
        val v = inflter.inflate(R.layout.add_item,null)
        /**set view*/
        val elementName = v.findViewById<EditText>(R.id.elementName)

        val addDialog = AlertDialog.Builder(this)

        addDialog.setView(v)
        addDialog.setPositiveButton("Ок"){
                dialog,_->
            val names = elementName.text.toString()
            elementList.add(ElementClass(names))
            elementAdapter.notifyDataSetChanged()
//            Toast.makeText(this,"Продукт добавлен в список", Toast.LENGTH_SHORT).show(

            databaseRef.update("bazarList", FieldValue.arrayUnion(names))

            dialog.dismiss()
        }
        addDialog.setNegativeButton("Отменить"){
                dialog,_->
            dialog.dismiss()
//            Toast.makeText(this,"Отмена", Toast.LENGTH_SHORT).show()

        }
        addDialog.create()
        addDialog.show()
    }

    /*override fun onDestroy() {
        val path:File = getApplicationContext().filesDir
        try{
            var writer = FileOutputStream(File(path, "list.txt"))
            writer.write(elementList.toString().toByteArray())
            writer.close()
        }catch (e:Exception){
            "Проблемы с файлом"
        }
        super.onDestroy()
    }*/
}