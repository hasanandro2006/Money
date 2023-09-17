package com.hasan_cottage.finalmoneymanager.fragment


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Data
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
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class MainFragment : Fragment() {

    val binding by lazy {
        FragmentMainBinding.inflate(layoutInflater)
    }



    companion object {
        lateinit var myViewModel: AppViewModel
    }

    private val arrayListRecyclerview: ArrayList<ModelM> = ArrayList()
    private val calenderM = Calendar.getInstance()
    private val calenderD = Calendar.getInstance()


    private lateinit var databaseTow:DatabaseTow
    private  var stock=0
    private var storeAccountId:Int?=null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        val context = requireContext().applicationContext


        HelperClass.categoryItem()// HelperClass initialize in main activity . for this where open app automatic set recyclerview image color


        val daoM = DatabaseAll.getInstanceAll(context).getAllDaoM()
        val repository = Repository(daoM)
        myViewModel =
            ViewModelProvider(this, ViewModelFactory(repository))[AppViewModel::class.java]

        // Set name .......
       databaseTow = DatabaseTow.getInstanceAllTow(context)
        val sharedPreferences = context.getSharedPreferences("Name", Context.MODE_PRIVATE)
       stock = sharedPreferences.getInt("oldPosition", 0) // Come from (adapter_name)
        databaseTow.getAllDaoTow().getDataId(stock).observe(viewLifecycleOwner) { it ->

            if (stock==0){
                storeAccountId = 0
            }else{
                it.forEach {
                   storeAccountId=it.id

                }
            }

        }

        binding.addAccount.setOnClickListener {
            val bottomSheetFragment = BottomSheetFragmentTow()
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
        }

        //first time not click this time run this code and come daily trans
        dailyTranslation(context)
        setTheName(context)
        binding.tabMode.getTabAt(0)?.select()

        // Set Daily monthly all trans
        binding.tabMode.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        CoroutineScope(Dispatchers.Main).launch {
                            dailyTranslation(context)
                        }
                    }

                    1 -> {
                        CoroutineScope(Dispatchers.Main).launch {
                            monthlyTranslation(context)
                        }
                    }

                    2 -> {
                        CoroutineScope(Dispatchers.Main).launch {
                            allTranslation(context)
                        }
                    }
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        binding.dittles.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                startActivity(Intent(context, TakeCalender::class.java))
            }

        }
        binding.searce.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                startActivity(Intent(context, SearchActivity::class.java))
            }
        }


        
        return binding.root
    }


    private fun setTheName(context: Context) {



    }

    fun dailyTranslation(context: Context) {

        val day = HelperClass.dateFormat(calenderD.time)
        Log.d("Daily", day)

            myViewModel.getDataDaily(day).observe(viewLifecycleOwner) {
                if (it.isEmpty()){
                    binding.noDataI.visibility=View.VISIBLE
                    binding.noDataT.visibility=View.VISIBLE
                    forAllDataSet(it,context)
                } else {
                    binding.noDataI.visibility=View.GONE
                    binding.noDataT.visibility=View.GONE
                    forAllDataSet(it,context)

            }
        }

    }

    fun monthlyTranslation(context: Context) {
        val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        val store = dateFormat.format(calenderM.time)
        myViewModel.getMonth(store).observe(viewLifecycleOwner) {
            if (it.isEmpty()){
                binding.noDataI.visibility=View.VISIBLE
                binding.noDataT.visibility=View.VISIBLE
                forAllDataSet(it,context)
            }else {
                binding.noDataI.visibility=View.GONE
                binding.noDataT.visibility=View.GONE
                forAllDataSet(it,context)
            }
        }
    }
    fun allTranslation(context: Context) {
        myViewModel.getDataM().observe(viewLifecycleOwner) {
            if (it.isEmpty()){
                binding.noDataI.visibility=View.VISIBLE
                binding.noDataT.visibility=View.VISIBLE
                forAllDataSet(it,context)
            }else {
                binding.noDataI.visibility=View.GONE
                binding.noDataT.visibility=View.GONE
                forAllDataSet(it,context)
            }
        }
    }

    private fun forAllDataSet(it: List<ModelM>, context: Context) {
        arrayListRecyclerview.addAll(it)
        var storeT = 0.0
        var incomeT = 0.0
        var expenseT = 0.0

        if (it.isNotEmpty()) {
            for (data in it) {
                storeT += data.amount

                if (data.type == HelperClass.INCOME) {
                    incomeT += data.amount
                } else if (data.type == HelperClass.EXPENSE) {
                    expenseT += data.amount
                }
            }
        }



        databaseTow.getAllDaoTow().getDataId(stock).observe(viewLifecycleOwner) { it ->
            if (it.isNullOrEmpty()){
                binding.Name.text = "Transaction"
                binding.symble.text = "($)"
                val symble = "$"

                binding.totalS.text = "$symble $storeT"
                binding.incomeS.text = "$symble $incomeT"
                val stores = "-$symble $expenseT"
                binding.expanseS.text = stores
            }else{
                it.forEach {
                    binding.Name.text = it.name
                    binding.symble.text = "(${it.currencySymbol})"
                    val symble = it.currencySymbol

                    binding.totalS.text = "$symble $storeT"
                    binding.incomeS.text = "$symble $incomeT"
                    val stores = "-$symble $expenseT"
                    binding.expanseS.text = stores
                }
            }
        }


        Log.d("Monthly", it.toString())

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