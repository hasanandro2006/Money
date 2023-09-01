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
import com.hasan_cottage.finalmoneymanager.databinding.FragmentBottomSheetBinding
import com.hasan_cottage.finalmoneymanager.databinding.FragmentBottomSheetCalenderBinding
import com.hasan_cottage.finalmoneymanager.roomDatabase.DatabaseAll
import com.hasan_cottage.finalmoneymanager.roomDatabase.Repository
import com.hasan_cottage.finalmoneymanager.viewModelClass.AppViewModel
import com.hasan_cottage.finalmoneymanager.viewModelClass.ViewModelFactory


class BottomSheetFragmentCalender(private var day:String, private val monthYear:String): BottomSheetDialogFragment() {

    val binding by lazy {
        FragmentBottomSheetCalenderBinding.inflate(layoutInflater)
    }
    private lateinit var myViewModel: AppViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val context=requireContext() as AppCompatActivity
        // room database
        val daoM = DatabaseAll.getInstanceAll(context).getAllDaoM()
        val repository = Repository(daoM)
        myViewModel = ViewModelProvider(this, ViewModelFactory(repository))[AppViewModel::class.java]

        if (day.length == 1) {
            day = "0$day"
        }

        val data="$day $monthYear"
        Log.d("comm",data)

        binding.textView10.text=data

        myViewModel.getDataDaily(data).observe(viewLifecycleOwner) { dataList ->
            Log.d("DataSize", "Data Size: ${dataList.size}")
            if (dataList.isNotEmpty()) {

                val adapter = AdapterMainRecyclerview(context, dataList)
                binding.recyclerviewWo.adapter = adapter
                binding.recyclerviewWo.setHasFixedSize(true)
                binding.recyclerviewWo.layoutManager = LinearLayoutManager(requireContext())
            } else {
                Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show()
            }
        }

        binding.addData.setOnClickListener {
            val bottomSheetFragment=BottomSheetFragment(-1,data)
            bottomSheetFragment.show(childFragmentManager,bottomSheetFragment.tag)
        }






        return binding.root
    }

}