package com.hasan_cottage.finalmoneymanager.BottomFragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hasan_cottage.finalmoneymanager.Activity.Signup_Activity
import com.hasan_cottage.finalmoneymanager.Adapter.Adapter_name
import com.hasan_cottage.finalmoneymanager.Roomdatabase.DatabaseAll
import com.hasan_cottage.finalmoneymanager.Roomdatabase.Repostry
import com.hasan_cottage.finalmoneymanager.databinding.FragmentBottomSheetTowBinding
import com.hasan_cottage.finalmoneymanager.viewmodelclass.Appviewmodel.Appviewmodel
import com.hasan_cottage.finalmoneymanager.viewmodelclass.ViewmodelFactory

class BottomSheetFragment_tow : BottomSheetDialogFragment() {
    lateinit var binding:FragmentBottomSheetTowBinding
    lateinit var viewmodelM:Appviewmodel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=FragmentBottomSheetTowBinding.inflate(inflater)
        val context=requireContext().applicationContext
        val daoM= DatabaseAll.getInstanceAll(context).getAllDaoM()
        val dao= DatabaseAll.getInstanceAll(context).getAllDao()
        val repostryM= Repostry(dao,daoM)
        viewmodelM= ViewModelProvider(this, ViewmodelFactory(repostryM)).get(Appviewmodel::class.java)


        val sharedPreferences = context.getSharedPreferences("Name", Context.MODE_PRIVATE)
        val stock=sharedPreferences.getInt("oldPosition",0)

        Log.d("Hasan",stock.toString())
        viewmodelM.getData().observe(viewLifecycleOwner, Observer {
            val adapter= Adapter_name(context,it,stock)
            binding.recyclerviewName.adapter=adapter
            binding.recyclerviewName.setHasFixedSize(true)
            binding.recyclerviewName.layoutManager= LinearLayoutManager(context)
            binding.recyclerviewName.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))
        })
        binding.addA.setOnClickListener {
            startActivity(Intent(context,Signup_Activity::class.java))
        }
        return binding.root
    }





}