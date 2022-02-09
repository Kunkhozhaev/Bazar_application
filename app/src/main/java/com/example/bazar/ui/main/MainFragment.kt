package com.example.bazar.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.bazar.R
import com.example.bazar.databinding.FragmentMainBinding
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var mainAdapter: MainAdapter
    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

    }

    private fun addProductDialog() {
        AlertDialog.Builder(requireContext())
            .apply {
                setCancelable(true)
                setView(R.layout.item_add)
                setTitle(getString(R.string.add_product))
                setPositiveButton("ADD") { _, _ ->
                    mainAdapter.addData()
                }
                setNeutralButton("CANCEL") { dialog, _ ->
                    dialog.dismiss()
                }
                create()
                show()
            }
    }

    // todo usi jerge setUpObservers() degen funkciya jaziw kerek

    private fun addInfo() {
//        val addDialog = AlertDialog.Builder(requireContext())
//        addDialog.setView(v)
//        addDialog.setPositiveButton("Ок") { dialog, _ ->
//            val names = elementName.text.toString()
//            elementList.add(ElementClass(names))
//            mainAdapter.notifyDataSetChanged()
////            Toast.makeText(this,"Продукт добавлен в список", Toast.LENGTH_SHORT).show(
//
//            databaseRef.update("bazarList", FieldValue.arrayUnion(names))
//
//            dialog.dismiss()
//        }
//        addDialog.setNegativeButton("Отменить") { dialog, _ ->
//            dialog.dismiss()
////            Toast.makeText(this,"Отмена", Toast.LENGTH_SHORT).show()
//
//        }
//        addDialog.create()
//        addDialog.show()
    }
}