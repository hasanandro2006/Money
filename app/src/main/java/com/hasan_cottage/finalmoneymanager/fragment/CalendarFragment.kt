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
import com.hasan_cottage.finalmoneymanager.adapter.AdapterMainRecyclerview
import com.hasan_cottage.finalmoneymanager.helper.HelperClass
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.activity.SearchActivity
import com.hasan_cottage.finalmoneymanager.activity.TakeCalender
import com.hasan_cottage.finalmoneymanager.roomDatabase.DatabaseAll
import com.hasan_cottage.finalmoneymanager.roomDatabase.ModelM
import com.hasan_cottage.finalmoneymanager.roomDatabase.Repository
import com.hasan_cottage.finalmoneymanager.bottomFragment.BottomSheetFragmentName
import com.hasan_cottage.finalmoneymanager.databinding.FragmentCalendarBinding
import com.hasan_cottage.finalmoneymanager.roomDatabaseNot.DatabaseTow
import com.hasan_cottage.finalmoneymanager.viewModelClass.AppViewModel
import com.hasan_cottage.finalmoneymanager.viewModelClass.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class CalendarFragment : Fragment() {

    val binding by lazy {
        FragmentCalendarBinding.inflate(layoutInflater)
    }

    private lateinit var myViewModel: AppViewModel
    private val arrayListRecyclerview: ArrayList<ModelM> = ArrayList()
    private var store: String? = null
    private var weekNumber: Int? = null
    private val calendar = Calendar.getInstance()
    private val calendar7 = Calendar.getInstance()

    private lateinit var databaseTow:DatabaseTow
    private  var stock=0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{

        val context = requireContext().applicationContext// get Context
        val sharedPreferences = context.getSharedPreferences("Time", Context.MODE_PRIVATE)
        val daily: Int = sharedPreferences.getInt("Daily", 1)


       databaseTow = DatabaseTow.getInstanceAllTow(requireContext())
        val sharedPreferencesA = requireContext().getSharedPreferences("Name", Context.MODE_PRIVATE)
        stock = sharedPreferencesA.getInt("oldPosition", 0)//come from (adapter_name)

        SymbleGanarate(context)

        // room database
        val daoM = DatabaseAll.getInstanceAll(context).getAllDaoM()
        val repository = Repository(daoM)
        myViewModel =
            ViewModelProvider(this, ViewModelFactory(repository))[AppViewModel::class.java]

        binding.searce.setOnClickListener {
            MainScope().launch(Dispatchers.Default){
                startActivity(Intent(context, SearchActivity::class.java))
            }
        }


        when (daily) {
            1 -> {
                dateFormatDay()
                setForDay(context)

            }

            2 -> {
                dateFormatWeek()
                setForWeek()
            }

            3 -> {
                dateFormatMonth()
                setForMonth(context)
            }

            4 -> {
                dateFormatYear()
                setForYear()

            }

            5 -> {
                setForAll(context)
            }
        }


        binding.month1.setOnClickListener {


            when (daily) {
                1 -> {
                    calendar.add(Calendar.DAY_OF_MONTH, -1)
                    dateFormatDay()
                    setForDay(context)
                }

                2 -> {
                    calendar7.add(Calendar.WEEK_OF_YEAR, -1)
                    dateFormatWeek()
                    setForWeek()
                }

                3 -> {
                    calendar.add(Calendar.MONTH, -1)
                    dateFormatMonth()
                    setForMonth(context)
                }

                4 -> {
                    calendar.add(Calendar.YEAR, -1)
                    dateFormatYear()
                    setForYear()
                }

            }

        }
        binding.month2.setOnClickListener {

            when (daily) {
                1 -> {
                    calendar.add(Calendar.DAY_OF_MONTH, 1)
                    dateFormatDay()
                    setForDay(context)
                }

                2 -> {
                    calendar7.add(Calendar.WEEK_OF_YEAR, 1)
                    dateFormatWeek()
                    setForWeek()
                }

                3 -> {
                    calendar.add(Calendar.MONTH, 1)
                    dateFormatMonth()
                    setForMonth(context)
                }

                4 -> {
                    calendar.add(Calendar.YEAR, 1)
                    dateFormatYear()
                    setForYear()
                }

            }

        }

        binding.MoreOption.setOnClickListener {
            val bottomSheetFragment = BottomSheetFragmentName(1)
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
        }
        binding.choseChange.setOnClickListener {
            val bottomSheetFragment = BottomSheetFragmentName(1)
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
        }
        binding.dittles.setOnClickListener {
            startActivity(Intent(context,TakeCalender::class.java))
        }

        return binding.root
    }


    // set date format---------
    private fun dateFormatDay() {
        store = HelperClass.dateFormat(calendar.time)
        binding.monthSet.text = store
    }

    private fun dateFormatWeek() {
        val sdf = SimpleDateFormat("dd MMM", Locale.getDefault())

        calendar7.set(Calendar.DAY_OF_WEEK, calendar7.firstDayOfWeek)
        val firstDateOfWeek = sdf.format(calendar7.time)
        calendar7.add(Calendar.DAY_OF_MONTH, 6)
        val lastDateOfWeek = sdf.format(calendar7.time)

        weekNumber = calendar7.get(Calendar.WEEK_OF_YEAR)

        val stores = "$firstDateOfWeek - $lastDateOfWeek"
        binding.monthSet.text = stores

    }

    private fun dateFormatMonth() {
        val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        store = dateFormat.format(calendar.time)
        binding.monthSet.text = store
    }

    private fun dateFormatYear() {
        val dateFormat = SimpleDateFormat("yyyy", Locale.getDefault())
        store = dateFormat.format(calendar.time)
        binding.monthSet.text = store
    }


    // set data with livedata---------
    private fun setForDay(context: Context) {
        databaseTow.getAllDaoTow().getDataId(stock).observe(requireActivity()) { it ->
            it.forEach {
                myViewModel.getDataDaily(store!!,it.id).observe(viewLifecycleOwner) {
                    if (it.isEmpty()){
                        binding.noDataI.visibility=View.VISIBLE
                        binding.noDataT.visibility=View.VISIBLE
                        sameCodeSet(context, it)
                    }else {
                        binding.noDataI.visibility=View.GONE
                        binding.noDataT.visibility=View.GONE
                        sameCodeSet(context, it)

                    }
                }
            }

        }


    }

    private fun setForMonth(context: Context) {
        databaseTow.getAllDaoTow().getDataId(stock).observe(viewLifecycleOwner) { it ->
            it.forEach {
                myViewModel.getMonth(store!!, it.id).observe(viewLifecycleOwner) { data ->
                    // Ensure that the view is active before updating UI or accessing the data
                    if (view != null) {
                        if (data.isEmpty()) {
                            binding.noDataI.visibility = View.VISIBLE
                            binding.noDataT.visibility = View.VISIBLE
                            sameCodeSet(context, data)
                        } else {
                            binding.noDataI.visibility = View.GONE
                            binding.noDataT.visibility = View.GONE
                            sameCodeSet(context, data)
                        }
                    }
                }
            }
        }
    }


    private fun setForWeek() {
        databaseTow.getAllDaoTow().getDataId(stock).observe(requireActivity()) { it ->
            it.forEach {
                myViewModel.getDataBetweenDates(weekNumber!!,it.id).observe(viewLifecycleOwner) {
                    if (it.isEmpty()){
                        binding.noDataI.visibility=View.VISIBLE
                        binding.noDataT.visibility=View.VISIBLE
                        sameCodeSet(context, it)
                    }else {
                        binding.noDataI.visibility=View.GONE
                        binding.noDataT.visibility=View.GONE
                        sameCodeSet(context, it)
                    }
                }
            }

        }

    }

    private fun setForYear() {
        databaseTow.getAllDaoTow().getDataId(stock).observe(requireActivity()) { it ->
            it.forEach {
                myViewModel.getDataYear(store!!,it.id).observe(viewLifecycleOwner) {
                    if (it.isEmpty()){
                        binding.noDataI.visibility=View.VISIBLE
                        binding.noDataT.visibility=View.VISIBLE
                        sameCodeSet(context, it)
                    }else {
                        binding.noDataI.visibility=View.GONE
                        binding.noDataT.visibility=View.GONE
                        sameCodeSet(context, it)
                    }
                }
            }

        }

    }

    private fun setForAll(context: Context?) {
        binding.monthSet.setText(R.string.all_transaction)
        databaseTow.getAllDaoTow().getDataId(stock).observe(requireActivity()) { it ->
            it.forEach {
                myViewModel.getDataM(it.id).observe(viewLifecycleOwner) {
                    if (it.isEmpty()){
                        binding.noDataI.visibility=View.VISIBLE
                        binding.noDataT.visibility=View.VISIBLE
                        sameCodeSet(context, it)
                    }else {
                        binding.noDataI.visibility=View.GONE
                        binding.noDataT.visibility=View.GONE
                        sameCodeSet(context, it)
                    }
                }
            }

        }


    }

    private fun sameCodeSet(context: Context?, it: List<ModelM>) {
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




        databaseTow.getAllDaoTow().getDataId(stock).observe(viewLifecycleOwner) { it ->
            if(it.isNullOrEmpty()){
                val symble = "$"
                binding.totalS.text = "$symble $storeT"
                binding.incomeS.text = "$symble $incomeT"
                val stores = "-$symble $expenseT"
                binding.expanseS.text = stores
            }else {
                it.forEach {
                    val symble = it.currencySymbol
                    binding.totalS.text = "$symble $storeT"
                    binding.incomeS.text = "$symble $incomeT"
                    val stores = "-$symble $expenseT"
                    binding.expanseS.text = stores
                }
            }

        }

        Log.d("main2", it.toString())




        val adapter = AdapterMainRecyclerview(requireActivity() as AppCompatActivity, it)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun SymbleGanarate(context: Context){

    }
}
