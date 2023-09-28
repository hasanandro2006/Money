package com.hasan_cottage.finalmoneymanager.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import codewithcal.au.calendarappexample.CalendarAdapter
import com.hasan_cottage.finalmoneymanager.bottomFragment.BottomSheetFragmentCalender
import com.hasan_cottage.finalmoneymanager.databinding.ActivityTakeCalenderBinding
import com.hasan_cottage.finalmoneymanager.helper.HelperClass
import com.hasan_cottage.finalmoneymanager.roomDatabase.DatabaseAll
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

class TakeCalender : AppCompatActivity(), CalendarAdapter.OnItemListener {

    val binding by lazy {
        ActivityTakeCalenderBinding.inflate(layoutInflater)
    }
    private lateinit var monthYearText: TextView
    private lateinit var calendarRecyclerView: RecyclerView
    private lateinit var selectedDate: Calendar
    lateinit var myViewModel: AppViewModel
    private lateinit var daysInMonth: ArrayList<String>

    private lateinit var databaseTow:DatabaseTow
    private  var stock=0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // room database
        val daoM = DatabaseAll.getInstanceAll(this).getAllDaoM()
        val repository = Repository(daoM)
        myViewModel =
            ViewModelProvider(this, ViewModelFactory(repository))[AppViewModel::class.java]


         databaseTow = DatabaseTow.getInstanceAllTow(this)
        val sharedPreferences =this.getSharedPreferences("Name", Context.MODE_PRIVATE)
        stock = sharedPreferences.getInt("oldPosition", 0)//come from (adapter_name)

        binding.searce.setOnClickListener {
            MainScope().launch(Dispatchers.Default){
                startActivity(Intent(this@TakeCalender, SearchActivity::class.java))
            }
        }

        initWidgets()
        selectedDate = Calendar.getInstance()
        setMonthView()
        setIncomeExpense()

        binding.back.setOnClickListener { onBackPressed() }
    }

    private fun initWidgets() {
        calendarRecyclerView = binding.calendarRecyclerView
        monthYearText = binding.monthYearTV
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setIncomeExpense() {
        val data = monthYearFromDate(selectedDate)

        databaseTow.getAllDaoTow().getDataId(stock).observe(this) { work ->
            work.forEach { workId ->

                myViewModel.getMonth(data,workId.id).observe(this) {
                    var storeT:Long = 0
                    var incomeT:Long = 0
                    var expenseT:Long = 0
                    it.forEach { data ->
                        storeT += data.amount
                        if (data.type == HelperClass.INCOME) {
                            incomeT += data.amount
                        } else if (data.type == HelperClass.EXPENSE) {
                            expenseT += data.amount
                        }
                    }
                    Log.d("main2", it.toString())



                    databaseTow.getAllDaoTow().getDataId(stock).observe(this) {

                        if (it.isNullOrEmpty()) {

                            binding.totalS.text = "$ $storeT"
                            binding.incomeS.text = "$ $incomeT"
                            val stores = "-$ $expenseT"
                            binding.expanseS.text = stores

                        } else {

                            it.forEach {
                                binding.totalS.text = "${it.currencySymbol} $storeT"
                                binding.incomeS.text = "${it.currencySymbol} $incomeT"
                                val stores = "-${it.currencySymbol} $expenseT"
                                binding.expanseS.text = stores

                            }
                        }


                    }

                }
            }
        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun daysInMonthArray(calendar: Calendar): ArrayList<String> {
        val daysInMonthArray = ArrayList<String>()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)

        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        val firstOfMonth = Calendar.getInstance()
        firstOfMonth.set(Calendar.YEAR, year)
        firstOfMonth.set(Calendar.MONTH, month)
        firstOfMonth.set(Calendar.DAY_OF_MONTH, 0)
        val dayOfWeek = firstOfMonth.get(Calendar.DAY_OF_WEEK)


        for (i in 1..42) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add(",,")
            } else {
                daysInMonthArray.add((i - dayOfWeek).toString())
            }
        }

        return daysInMonthArray
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun previousMonthAction(view: View) {
       MainScope().launch (Dispatchers.Main){
            selectedDate.add(Calendar.MONTH, -1)
            setMonthView()
            setIncomeExpense()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun nextMonthAction(view: View) {

       MainScope().launch (Dispatchers.Main){
           selectedDate.add(Calendar.MONTH, 1)
           setMonthView()
           setIncomeExpense()
       }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setMonthView() {
        monthYearText.text = monthYearFromDate(selectedDate)
        daysInMonth = daysInMonthArray(selectedDate)
        val data = monthYearFromDate(selectedDate)

        val calendarAdapter = CalendarAdapter(daysInMonth, this, myViewModel, data, databaseTow,stock)
        val layoutManager = GridLayoutManager(applicationContext, 7)
        calendarRecyclerView.layoutManager = layoutManager
        calendarRecyclerView.adapter = calendarAdapter

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun monthYearFromDate(calendar: Calendar): String {
        val formatter = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        return formatter.format(calendar.time)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onItemClick(position: Int, dayText: String) {

        if (dayText != ",,") {
            val bottomSheet = BottomSheetFragmentCalender(dayText, monthYearFromDate(selectedDate))
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        } else {
            Toast.makeText(this, "empty", Toast.LENGTH_SHORT).show()
        }
    }
}
