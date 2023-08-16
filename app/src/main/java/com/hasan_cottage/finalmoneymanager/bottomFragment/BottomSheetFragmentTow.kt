package com.hasan_cottage.finalmoneymanager.bottomFragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hasan_cottage.finalmoneymanager.adapter.AdapterName
import com.hasan_cottage.finalmoneymanager.roomDatabaseNot.DataSignup
import com.hasan_cottage.finalmoneymanager.roomDatabaseNot.DatabaseTow
import com.hasan_cottage.finalmoneymanager.roomDatabase.DatabaseAll
import com.hasan_cottage.finalmoneymanager.roomDatabase.Repository
import com.hasan_cottage.finalmoneymanager.activity.SignupActivity
import com.hasan_cottage.finalmoneymanager.activity.SplashActivity
import com.hasan_cottage.finalmoneymanager.databinding.FragmentBottomSheetTowBinding
import com.hasan_cottage.finalmoneymanager.viewModelClass.AppViewModel
import com.hasan_cottage.finalmoneymanager.viewModelClass.ViewModelFactory

class BottomSheetFragmentTow : BottomSheetDialogFragment(), AdapterName.ClickDataCome {
    lateinit var binding: FragmentBottomSheetTowBinding
    private lateinit var myViewModel: AppViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomSheetTowBinding.inflate(inflater)
        val context = requireContext().applicationContext

        val daoM = DatabaseAll.getInstanceAll(context).getAllDaoM()
        val repository = Repository(daoM)
        myViewModel =
            ViewModelProvider(this, ViewModelFactory(repository))[AppViewModel::class.java]


        val sharedPreferences = context.getSharedPreferences("Name", Context.MODE_PRIVATE)
        val stock = sharedPreferences.getInt("oldPosition", 0)

        val databaseTow = DatabaseTow.getInstanceAllTow(context)
        databaseTow.getAllDaoTow().getData().observe(viewLifecycleOwner) {
            Log.d("Hasan", it.toString())
            val adapter = AdapterName(context, it, stock, this)
            binding.recyclerviewName.adapter = adapter
            binding.recyclerviewName.setHasFixedSize(true)
            binding.recyclerviewName.layoutManager = LinearLayoutManager(context)
            binding.recyclerviewName.addItemDecoration(
                DividerItemDecoration(
                    context, DividerItemDecoration.VERTICAL
                )
            )
        }
        binding.addA.setOnClickListener {
            startActivity(Intent(context, SignupActivity::class.java))

        }
        return binding.root
    }

    override fun click(dataa: DataSignup) {
        restartApp()
    }

    private fun restartApp() {
        val intent = Intent(context, SplashActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }


}