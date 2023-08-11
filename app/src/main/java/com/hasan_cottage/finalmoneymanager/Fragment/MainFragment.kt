package com.hasan_cottage.finalmoneymanager.Fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.hasan_cottage.finalmoneymanager.Activity.MainActivity
import com.hasan_cottage.finalmoneymanager.Adapter.Adapter_mainrecyclerview
import com.hasan_cottage.finalmoneymanager.BottomFragment.BottomSheetFragment_tow
import com.hasan_cottage.finalmoneymanager.Helper.HelperClass
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.RoomdataNot.DatabaseTow
import com.hasan_cottage.finalmoneymanager.Roomdatabase.DatabaseAll
import com.hasan_cottage.finalmoneymanager.Roomdatabase.ModelM
import com.hasan_cottage.finalmoneymanager.Roomdatabase.Repostry
import com.hasan_cottage.finalmoneymanager.databinding.FragmentMainBinding
import com.hasan_cottage.finalmoneymanager.viewmodelclass.Appviewmodel.Appviewmodel
import com.hasan_cottage.finalmoneymanager.viewmodelclass.ViewmodelFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding
    lateinit var viewmodelM:Appviewmodel
    val arrayListRecyclerview: ArrayList<ModelM> = ArrayList()
    private val calenderM=Calendar.getInstance()
    private val calenderD=Calendar.getInstance()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=FragmentMainBinding.inflate(inflater)

        val context=requireContext().applicationContext

        HelperClass.catagoryItem()// HelperClass initilize in main activity . for this wheme open app automatic set recyclerview image color



        val daoM = DatabaseAll.getInstanceAll(context).getAllDaoM()
        val repostryM = Repostry(daoM)
        viewmodelM =ViewModelProvider(this, ViewmodelFactory(repostryM)).get(Appviewmodel::class.java)



        binding.addAccount.setOnClickListener {
            val bottomSheetFragment = BottomSheetFragment_tow()
            bottomSheetFragment.show(requireFragmentManager(), bottomSheetFragment.tag)
        }

        // Set Daily monthly all thrangtion
        binding.tabMode.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when(tab.position){
                        0 ->{
                            dailyTeanstion(context)
                        }
                        1 ->{
                            monthlyTranstion(context)
                        }
                        2 ->{
                            allTranstion(context)
                        }
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        //first time not click this time run this code and come daily transtion
        dailyTeanstion(context)
        setTheName(context)

        return binding.root
    }



    private fun setTheName(context: Context) {
        val databaseTow= DatabaseTow.getInstanceAllTow(context)
        // set name .......
        val sharedPreferences =context.getSharedPreferences("Name", Context.MODE_PRIVATE)
        val stock=sharedPreferences.getInt("oldPosition",0)//come from (adapter_name)
        databaseTow.getAllDaoTow().getData().observe(viewLifecycleOwner, Observer {

            if (it.isNullOrEmpty()){
                binding.Name.text="Project name"
            }else{
                binding.Name.text=it[stock].name
            }

        })
    }

    fun dailyTeanstion(context: Context){

        val day=HelperClass.dateFormet(calenderD.time)
        Log.d("Dily",day)
        viewmodelM.getDataDily(day).observe(viewLifecycleOwner, Observer {

            arrayListRecyclerview.addAll(it)
            var storeT:Double=0.0
            var incomeT:Double=0.0
            var expenseT:Double=0.0
            it.forEach{ data ->

                storeT += data.amount

                if (data.type.equals(HelperClass.INCOME)) {
                    incomeT+=data.amount
                } else if (data.type.equals(HelperClass.EXPENSE)){
                    expenseT+=data.amount
                }
            }
            Log.d("Daily",it.toString())
            binding.totalS.text=storeT.toString()
            binding.incomeS.text=incomeT.toString()
            binding.expanseS.text="- "+expenseT.toString()


            val adapter= Adapter_mainrecyclerview(context,it)
            binding.recyclerView.adapter=adapter
            binding.recyclerView.setHasFixedSize(true)
            binding.recyclerView.addItemDecoration(
                DividerItemDecoration(context,
                    DividerItemDecoration.VERTICAL)
            )
            binding.recyclerView.layoutManager= LinearLayoutManager(context)
        })

    }

    fun monthlyTranstion(context: Context){
        val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        val store=dateFormat.format(calenderM.time)
        viewmodelM.getMOnth(store).observe(viewLifecycleOwner, Observer {

            arrayListRecyclerview.addAll(it)
            var storeT:Double=0.0
            var incomeT:Double=0.0
            var expenseT:Double=0.0
            it.forEach{ data ->

                storeT += data.amount

                if (data.type.equals(HelperClass.INCOME)) {
                    incomeT+=data.amount
                } else if (data.type.equals(HelperClass.EXPENSE)){
                    expenseT+=data.amount
                }
            }
            Log.d("Monthly",it.toString())
            binding.totalS.text=storeT.toString()
            binding.incomeS.text=incomeT.toString()
            binding.expanseS.text="- "+expenseT.toString()


            val adapter= Adapter_mainrecyclerview(context,it)
            binding.recyclerView.adapter=adapter
            binding.recyclerView.setHasFixedSize(true)
            binding.recyclerView.addItemDecoration(
                DividerItemDecoration(context,
                    DividerItemDecoration.VERTICAL)
            )
            binding.recyclerView.layoutManager= LinearLayoutManager(context)
        })
    }


    fun allTranstion(context: Context){
        viewmodelM.getDataM().observe(viewLifecycleOwner, Observer {

            arrayListRecyclerview.addAll(it)
            var storeT:Double=0.0
            var incomeT:Double=0.0
            var expenseT:Double=0.0
            it.forEach{ data ->

                storeT += data.amount

                if (data.type.equals(HelperClass.INCOME)) {
                    incomeT+=data.amount
                } else if (data.type.equals(HelperClass.EXPENSE)){
                    expenseT+=data.amount
                }
            }
            Log.d("All Trans...",it.toString())
            binding.totalS.text=storeT.toString()
            binding.incomeS.text=incomeT.toString()
            binding.expanseS.text="- "+expenseT.toString()


            val adapter= Adapter_mainrecyclerview(context,it)
            binding.recyclerView.adapter=adapter
            binding.recyclerView.setHasFixedSize(true)
            binding.recyclerView.addItemDecoration(
                DividerItemDecoration(context,
                    DividerItemDecoration.VERTICAL)
            )
            binding.recyclerView.layoutManager= LinearLayoutManager(context)
        })


    }


}