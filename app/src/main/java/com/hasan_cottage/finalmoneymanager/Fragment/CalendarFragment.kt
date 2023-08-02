package com.hasan_cottage.finalmoneymanager.Fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hasan_cottage.finalmoneymanager.Adapter.Adapter_mainrecyclerview
import com.hasan_cottage.finalmoneymanager.Helper.HelperClass
import com.hasan_cottage.finalmoneymanager.Roomdatabase.DatabaseAll
import com.hasan_cottage.finalmoneymanager.Roomdatabase.ModelM
import com.hasan_cottage.finalmoneymanager.Roomdatabase.Repostry
import com.hasan_cottage.finalmoneymanager.databinding.FragmentCalendarBinding
import com.hasan_cottage.finalmoneymanager.viewmodelclass.Appviewmodel.Appviewmodel
import com.hasan_cottage.finalmoneymanager.viewmodelclass.ViewmodelFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class CalendarFragment : Fragment() {

lateinit var binding: FragmentCalendarBinding


    lateinit var viewmodelM:Appviewmodel
    val arrayListRecyclerview: ArrayList<ModelM> = ArrayList()
    var store:String?=null
   private val calendar = Calendar.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
     binding=FragmentCalendarBinding.inflate(inflater)

        val context=requireContext().applicationContext// get Context



        // room database
        val daoM= DatabaseAll.getInstanceAll(context).getAllDaoM()
        val dao= DatabaseAll.getInstanceAll(context).getAllDao()
        val repostryM= Repostry(dao,daoM)
        viewmodelM= ViewModelProvider(this, ViewmodelFactory(repostryM)).get(Appviewmodel::class.java)



        // set month change and data change
        updateData()
        setAgainViewmodel(context)

        binding.month1.setOnClickListener {
            calendar.add(Calendar.MONTH,-1)
            updateData()
            setAgainViewmodel(context)
        }
        binding.month2.setOnClickListener {
            calendar.add(Calendar.MONTH,1)
           updateData()
            setAgainViewmodel(context)
        }


       return binding.root
    }





    fun updateData(){
        val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        store=dateFormat.format(calendar.time)
        binding.monthSet.text=store

    }

    fun setAgainViewmodel(context: Context){
        // set data in recyclerview
        viewmodelM.getMOnth(store!!).observe(viewLifecycleOwner, Observer {

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
            Log.d("main2",it.toString())

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
