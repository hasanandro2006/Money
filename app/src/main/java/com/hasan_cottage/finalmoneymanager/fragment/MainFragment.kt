package com.hasan_cottage.finalmoneymanager.fragment


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.activity.SearchActivity
import com.hasan_cottage.finalmoneymanager.activity.TakeCalender
import com.hasan_cottage.finalmoneymanager.adapter.AdapterMainRecyclerview
import com.hasan_cottage.finalmoneymanager.bottomFragment.BottomSheetFragmentTow
import com.hasan_cottage.finalmoneymanager.databinding.FragmentMainBinding
import com.hasan_cottage.finalmoneymanager.helper.HelperClass
import com.hasan_cottage.finalmoneymanager.roomDatabase.DatabaseAll
import com.hasan_cottage.finalmoneymanager.roomDatabase.ModelM
import com.hasan_cottage.finalmoneymanager.roomDatabase.Repository
import com.hasan_cottage.finalmoneymanager.roomDatabaseNot.DatabaseTow
import com.hasan_cottage.finalmoneymanager.viewModelClass.AppViewModel
import com.hasan_cottage.finalmoneymanager.viewModelClass.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.concurrent.thread


class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding

    companion object {
        lateinit var myViewModel: AppViewModel
    }

    private val arrayListRecyclerview: ArrayList<ModelM> = ArrayList()
    private val calenderM = Calendar.getInstance()
    private val calenderD = Calendar.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)

        val context = requireContext().applicationContext


        HelperClass.categoryItem()// HelperClass initialize in main activity . for this where open app automatic set recyclerview image color


        val daoM = DatabaseAll.getInstanceAll(context).getAllDaoM()
        val repository = Repository(daoM)
        myViewModel =
            ViewModelProvider(this, ViewModelFactory(repository))[AppViewModel::class.java]



        binding.addAccount.setOnClickListener {
            val bottomSheetFragment = BottomSheetFragmentTow()
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
        }

        // Set Daily monthly all trans
        binding.tabMode.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        dailyTranslation(context)
                    }

                    1 -> {
                        monthlyTranslation(context)
                    }

                    2 -> {
                        allTranslation(context)
                    }
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        binding.dittles.setOnClickListener {
            MainScope().launch(Dispatchers.Default){
                startActivity(Intent(context, TakeCalender::class.java))
            }
        }
        binding.searce.setOnClickListener {
            MainScope().launch(Dispatchers.Default){
                startActivity(Intent(context, SearchActivity::class.java))
            }
        }

        //first time not click this time run this code and come daily trans
        dailyTranslation(context)
        setTheName(context)

        return binding.root
    }


    private fun setTheName(context: Context) {
        val databaseTow = DatabaseTow.getInstanceAllTow(context)
        // set name .......
        val sharedPreferences = context.getSharedPreferences("Name", Context.MODE_PRIVATE)
        val stock = sharedPreferences.getInt("oldPosition", 0)//come from (adapter_name)
        databaseTow.getAllDaoTow().getData().observe(viewLifecycleOwner) {

            if (it.isNullOrEmpty()) {
                binding.Name.setText(R.string.project_name)
            } else {
                binding.Name.text = it[stock].name
            }

        }
    }

    fun dailyTranslation(context: Context) {

        val day = HelperClass.dateFormat(calenderD.time)
        Log.d("Daily", day)
        myViewModel.getDataDaily(day).observe(viewLifecycleOwner) {

            arrayListRecyclerview.addAll(it)
            var storeT = 0.0
            var incomeT = 0.0
            var expenseT = 0.0
            it.forEach { data ->

                storeT += data.amount

                if (data.type == HelperClass.INCOME) {
                    incomeT += data.amount
                } else if (data.type == HelperClass.EXPENSE) {
                    expenseT += data.amount
                }
            }
            Log.d("Daily", it.toString())
            binding.totalS.text = storeT.toString()
            binding.incomeS.text = incomeT.toString()
            val stores = "- $expenseT"
            binding.expanseS.text = stores


            val adapter = AdapterMainRecyclerview(requireActivity() as AppCompatActivity, it)
            binding.recyclerView.adapter = adapter
            binding.recyclerView.setHasFixedSize(true)
            binding.recyclerView.addItemDecoration(
                DividerItemDecoration(
                    context, DividerItemDecoration.VERTICAL
                )
            )
            binding.recyclerView.layoutManager = LinearLayoutManager(context)
        }

    }

    fun monthlyTranslation(context: Context) {
        val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        val store = dateFormat.format(calenderM.time)
        myViewModel.getMonth(store).observe(viewLifecycleOwner) {

            arrayListRecyclerview.addAll(it)
            var storeT = 0.0
            var incomeT = 0.0
            var expenseT = 0.0
            it.forEach { data ->

                storeT += data.amount

                if (data.type == HelperClass.INCOME) {
                    incomeT += data.amount
                } else if (data.type == HelperClass.EXPENSE) {
                    expenseT += data.amount
                }
            }
            Log.d("Monthly", it.toString())
            binding.totalS.text = storeT.toString()
            binding.incomeS.text = incomeT.toString()
            val stores = "- $expenseT"
            binding.expanseS.text = stores


            val adapter = AdapterMainRecyclerview(requireActivity() as AppCompatActivity, it)
            binding.recyclerView.adapter = adapter
            binding.recyclerView.setHasFixedSize(true)
            binding.recyclerView.addItemDecoration(
                DividerItemDecoration(
                    context, DividerItemDecoration.VERTICAL
                )
            )
            binding.recyclerView.layoutManager = LinearLayoutManager(context)
        }
    }


    fun allTranslation(context: Context) {
        myViewModel.getDataM().observe(viewLifecycleOwner) {

            arrayListRecyclerview.addAll(it)
            var storeT = 0.0
            var incomeT = 0.0
            var expenseT = 0.0
            it.forEach { data ->

                storeT += data.amount

                if (data.type == HelperClass.INCOME) {
                    incomeT += data.amount
                } else if (data.type == HelperClass.EXPENSE) {
                    expenseT += data.amount
                }
            }
            Log.d("All Trans...", it.toString())
            binding.totalS.text = storeT.toString()
            binding.incomeS.text = incomeT.toString()
            val stores = "- $expenseT"
            binding.expanseS.text = stores


            val adapter = AdapterMainRecyclerview(requireActivity() as AppCompatActivity, it)
            binding.recyclerView.adapter = adapter
            binding.recyclerView.setHasFixedSize(true)
            binding.recyclerView.addItemDecoration(
                DividerItemDecoration(
                    context, DividerItemDecoration.VERTICAL
                )
            )
            binding.recyclerView.layoutManager = LinearLayoutManager(context)
        }


    }


}