package com.hasan_cottage.finalmoneymanager.bottomFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hasan_cottage.finalmoneymanager.adapter.AdapterMainRecyclerview
import com.hasan_cottage.finalmoneymanager.databinding.FragmentBottomSheetCalenderBinding
import com.hasan_cottage.finalmoneymanager.roomDatabase.DatabaseAll
import com.hasan_cottage.finalmoneymanager.roomDatabase.Repository
import com.hasan_cottage.finalmoneymanager.viewModelClass.AppViewModel
import com.hasan_cottage.finalmoneymanager.viewModelClass.ViewModelFactory


class BottomSheetFragmentCalender(private val day:String,private val monthYear:String): BottomSheetDialogFragment() {

    lateinit var binding: FragmentBottomSheetCalenderBinding
    private lateinit var myViewModel: AppViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding= FragmentBottomSheetCalenderBinding.inflate(inflater,container,false)

        val context=requireContext() as AppCompatActivity
        // room database
        val daoM = DatabaseAll.getInstanceAll(context).getAllDaoM()
        val repository = Repository(daoM)
        myViewModel = ViewModelProvider(this, ViewModelFactory(repository))[AppViewModel::class.java]

        val data="$day $monthYear"
        Log.d("comm",data)

        binding.textView10.text=data

        myViewModel.getDataDaily(data).observe(viewLifecycleOwner){
            if (data.isNotEmpty()) {
                val adapter = AdapterMainRecyclerview(context, it)
                binding.recyclerviewWo.adapter = adapter
                binding.recyclerviewWo.setHasFixedSize(true)
                binding.recyclerviewWo.layoutManager = LinearLayoutManager(requireContext())
            } else {
               Toast.makeText(context,"not come",Toast.LENGTH_SHORT).show()
            }
        }
        binding.addData.setOnClickListener {
            val bottomSheetFragment=BottomSheetFragment(-1,data)
            bottomSheetFragment.show(childFragmentManager,bottomSheetFragment.tag)
        }






        return binding.root
    }

}