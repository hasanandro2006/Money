package com.hasan_cottage.finalmoneymanager.activity

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.hasan_cottage.finalmoneymanager.adapter.AdapterStats
import com.hasan_cottage.finalmoneymanager.bottomFragment.BottomSheetFragmentName
import com.hasan_cottage.finalmoneymanager.helper.HelperClass
import com.hasan_cottage.finalmoneymanager.model.StatsModel
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.roomDatabase.DatabaseAll
import com.hasan_cottage.finalmoneymanager.roomDatabase.ModelM
import com.hasan_cottage.finalmoneymanager.roomDatabase.Repository
import com.hasan_cottage.finalmoneymanager.databinding.ActivityExpenseIncomeStructerBinding
import com.hasan_cottage.finalmoneymanager.fragment.MainFragment
import com.hasan_cottage.finalmoneymanager.roomDatabaseNot.DatabaseTow
import com.hasan_cottage.finalmoneymanager.viewModelClass.AppViewModel
import com.hasan_cottage.finalmoneymanager.viewModelClass.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class ExpenseIncomeChart : AppCompatActivity() {

    val binding by lazy {
        ActivityExpenseIncomeStructerBinding.inflate(layoutInflater)
    }
    private lateinit var myViewModel: AppViewModel

    private var calender = Calendar.getInstance()

    private lateinit var entries: ArrayList<PieEntry>

    var tabBoolean = true
    lateinit var storeAll: String
    private var weekNumber: Int? = null
    private var weekData: String? = null

    private lateinit var databaseTow:DatabaseTow
    private  var stock=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val daoM = DatabaseAll.getInstanceAll(this).getAllDaoM()
        val repository = Repository(daoM)
        myViewModel =
            ViewModelProvider(this, ViewModelFactory(repository))[AppViewModel::class.java]

       databaseTow = DatabaseTow.getInstanceAllTow(this)
        val sharedPreferencesA =this.getSharedPreferences("Name", Context.MODE_PRIVATE)
        stock = sharedPreferencesA.getInt("oldPosition", 0)//come from (adapter_name)

        val sharedPreferences = getSharedPreferences("Time", Context.MODE_PRIVATE)

        val daily: Int = sharedPreferences.getInt("Daily", 1)

        binding.back.setOnClickListener {
            onBackPressed()
        }

        // here sev boolean value which one save when button bar come and intent come value 1
        val sharedPreferencesTrueFalseCome = getSharedPreferences("SaveTrueFalse", MODE_PRIVATE)
        val work = intent.getIntExtra("work", 1)

        tabBoolean = if (work == 1) {

             sharedPreferencesTrueFalseCome.getBoolean("U", false)
        } else {
            intent.getBooleanExtra("isFalse", true)
        }
        tabBoolean=intent.getBooleanExtra("isFalse", true)
        val timeData = intent.getStringExtra("nowData")
        weekNumber = intent.getIntExtra("week", 0)


        // first time call .............

        when (daily) {

            1 -> {
                val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                if (timeData != null) {
                    val t = outputFormat.parse(timeData.toString())!!
                    calender.time = t
                }
                storeAll = outputFormat.format(calender.time)
                if (tabBoolean) {
                    updateCalenderFirst(storeAll, daily, true)
                } else {
                    updateCalenderFirst(storeAll, daily, false)
                }

            }

            2 -> {
                storeAll = "Work"
                weekData = "Hasan"
                if(weekNumber != 0) {
                    calender.set(Calendar.WEEK_OF_YEAR, weekNumber!!)
                    weekNumber=0
                }else{
                    calender.get(Calendar.WEEK_OF_YEAR)
                }
                newForWeek()
                if (tabBoolean) {
                    updateCalenderFirst(weekData!!, daily, true)
                } else {
                    updateCalenderFirst(weekData!!, daily, false)
                }
            }

            3 -> {
                val outputFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
                if (timeData != null) {
                    val t = outputFormat.parse(timeData.toString())!!
                    calender.time = t
                }
                storeAll = outputFormat.format(calender.time)
                if (tabBoolean) {
                    updateCalenderFirst(storeAll, daily, true)
                } else {
                    updateCalenderFirst(storeAll, daily, false)
                }
            }

            4 -> {
                val outputFormat = SimpleDateFormat("yyyy", Locale.getDefault())
                if (timeData != null) {
                    val t = outputFormat.parse(timeData.toString())!!
                    calender.time = t
                }
                storeAll = outputFormat.format(calender.time)
                if (tabBoolean) {
                    updateCalenderFirst(storeAll, daily, true)
                } else {
                    updateCalenderFirst(storeAll, daily, false)
                }
            }

            5 -> {
                storeAll = "All Transaction"

                if (tabBoolean) {
                    updateCalenderFirst(storeAll, daily, true)
                } else {
                    updateCalenderFirst(storeAll, daily, false)
                }
            }


        }

        tabBoolean = if (tabBoolean) {
            binding.tabLayout.getTabAt(0)?.select()
            binding.active.setBackgroundResource(R.drawable.degine_for_active)
            true
        } else {
            binding.tabLayout.getTabAt(1)?.select()
            binding.active.setBackgroundResource(R.drawable.degine_for_nagative)
            false
        }



        // click tabview ............
        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        binding.active.setBackgroundResource(R.drawable.degine_for_active)
                        if (weekNumber != 0) {
                            updateCalenderFirst(weekData!!, daily, true)
                        } else {
                            updateCalenderFirst(storeAll, daily, true)
                        }
                        tabBoolean = true

                    }

                    1 -> {
                        binding.active.setBackgroundResource(R.drawable.degine_for_nagative)
                        updateCalenderFirst(storeAll, daily, false)

                        if (weekNumber != 0) {
                            updateCalenderFirst(weekData!!, daily, false)
                        } else {
                            updateCalenderFirst(storeAll, daily, false)
                        }
                        tabBoolean = false
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })


        // click previous button ............

        binding.monthS1.setOnClickListener {

            when (daily) {
                1 -> {
                    calender.add(Calendar.DAY_OF_MONTH, -1)
                    val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                    storeAll = outputFormat.format(calender.time)
                    updateCalender(storeAll, daily)
                }

                2 -> {
                    calender.add(Calendar.WEEK_OF_YEAR, -1)
                    newForWeek()
                    weekNumber = calender.get(Calendar.WEEK_OF_YEAR)
                    updateCalender(weekData!!, daily)
                }

                3 -> {
                    calender.add(Calendar.MONTH, -1)
                    val outputFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
                    storeAll = outputFormat.format(calender.time)
                    updateCalender(storeAll, daily)
                }

                4 -> {
                    calender.add(Calendar.YEAR, -1)
                    val outputFormat = SimpleDateFormat("yyyy", Locale.getDefault())
                    storeAll = outputFormat.format(calender.time)
                    updateCalender(storeAll, daily)
                }

            }

        }

        // click next button ..........

        binding.monthS2.setOnClickListener {
            when (daily) {
                1 -> {
                    calender.add(Calendar.DAY_OF_MONTH, 1)
                    val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                    storeAll = outputFormat.format(calender.time)
                    updateCalender(storeAll, daily)
                }

                2 -> {
                    calender.add(Calendar.WEEK_OF_YEAR, 1)
                    newForWeek()
                    weekNumber = calender.get(Calendar.WEEK_OF_YEAR)
                    updateCalender(weekData!!, daily)
                }

                3 -> {
                    calender.add(Calendar.MONTH, 1)
                    val outputFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
                    storeAll = outputFormat.format(calender.time)
                    updateCalender(storeAll, daily)
                }

                4 -> {
                    calender.add(Calendar.YEAR, 1)
                    val outputFormat = SimpleDateFormat("yyyy", Locale.getDefault())
                    storeAll = outputFormat.format(calender.time)
                    updateCalender(storeAll, daily)
                }

            }

        }


        // come bottom chose time and this time save boolean value and when refresh activity again this time save boolean value
        val sharedPreferencesTrueFalse = getSharedPreferences("SaveTrueFalse", MODE_PRIVATE)
        val editor = sharedPreferencesTrueFalse.edit()
        binding.moreItem.setOnClickListener {

            editor.putBoolean("U", tabBoolean)
            editor.apply()
            val bottomSheetFragment = BottomSheetFragmentName(3)
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
        binding.choseChange.setOnClickListener {
            editor.putBoolean("U", tabBoolean)
            editor.apply()
            val bottomSheetFragment = BottomSheetFragmentName(3)
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }


    }

    private fun newForWeek() {

        val sdf = SimpleDateFormat("dd MMM", Locale.getDefault())


        calender.set(Calendar.DAY_OF_WEEK, calender.firstDayOfWeek)
        val fN = sdf.format(calender.time)

        calender.add(Calendar.DAY_OF_WEEK, 6)
        val lN = sdf.format(calender.time)

        weekNumber = calender.get(Calendar.WEEK_OF_YEAR)

        val store = "$fN - $lN"
        weekData = store
        binding.monthSet.text = weekData

    }

    // End Main Method ................

    fun updateCalenderFirst(calendar: String, daily: Int, boolean: Boolean) {


        binding.monthSet.text = calendar


        if (boolean) {
            binding.tabLayout.getTabAt(0)?.select()
            when (daily) {
                1 -> {
                    chartDayIncome(calendar)
                }

                2 -> {
                    chartWeekIncome()
                }

                3 -> {
                    chartMonthIncome(calendar)
                }

                4 -> {
                    chartYearIncome(calendar)
                }

                5 -> {
                    chartAllIncome()
                }


            }

        } else {
            binding.tabLayout.getTabAt(1)?.select()
            when (daily) {
                1 -> {
                    chartDayExpense(calendar)
                }

                2 -> {

                    chartWeekExpense()
                }

                3 -> {
                    chartMonthExpense(calendar)
                }

                4 -> {
                    chartYearExpense(calendar)
                }

                5 -> {

                    chartAllExpense()
                }
            }

        }

    }


    private fun updateCalender(calendar: String, daily: Int) {
        binding.monthSet.text = calendar
        if (tabBoolean) {
            when (daily) {
                1 -> {
                    chartDayIncome(calendar)
                }

                2 -> {

                    chartWeekIncome()
                }

                3 -> {
                    chartMonthIncome(calendar)
                }

                4 -> {
                    chartYearIncome(calendar)
                }

            }

        } else {
            when (daily) {
                1 -> {

                    chartDayExpense(calendar)

                }

                2 -> {

                    chartWeekExpense()
                }

                3 -> {
                    chartMonthExpense(calendar)
                }

                4 -> {
                    chartYearExpense(calendar)
                }
            }

        }

    }


    private fun chartDayIncome(store: String) {

        databaseTow.getAllDaoTow().getDataId(stock).observe(this) { it ->
            it.forEach {
                myViewModel.getDataDailyT(store,it.id).observe(this) {
                    oneChartIncome(it)
                }
            }
        }
    }

    private fun chartDayExpense(store: String) {
        databaseTow.getAllDaoTow().getDataId(stock).observe(this) { it ->
            it.forEach {
                myViewModel.getDataDailyT(store,it.id).observe(this) {
                    oneChartExpense(it)
                }
            }
        }
    }

    private fun chartWeekIncome() {

        databaseTow.getAllDaoTow().getDataId(stock).observe(this) { it ->
            it.forEach {
                myViewModel.getDataBetweenDates(weekNumber!!,it.id).observe(this) {
                    oneChartIncome(it)
                }
            }
        }
    }

    private fun chartWeekExpense() {

        databaseTow.getAllDaoTow().getDataId(stock).observe(this) { it ->
            it.forEach {
                myViewModel.getDataBetweenDates(weekNumber!!,it.id).observe(this) {
                    oneChartExpense(it)
                }
            }
        }
    }

    private fun chartMonthIncome(store: String) {

        databaseTow.getAllDaoTow().getDataId(stock).observe(this) { it ->
            it.forEach {
                myViewModel.getMonth(store,it.id).observe(this) {
                    oneChartIncome(it)
                    }
                }
            }

        }



    private fun chartMonthExpense(store: String) {
        databaseTow.getAllDaoTow().getDataId(stock).observe(this) { it ->
            it.forEach {
                myViewModel.getMonth(store,it.id).observe(this) {
                    oneChartExpense(it)
                }
            }
        }
    }

    private fun chartYearIncome(store: String) {

        databaseTow.getAllDaoTow().getDataId(stock).observe(this) { it ->
            it.forEach {
                myViewModel.getDataYear(store,it.id).observe(this) {
                    oneChartIncome(it)
                }
            }

        }
    }


    private fun chartYearExpense(store: String) {

        databaseTow.getAllDaoTow().getDataId(stock).observe(this) { it ->
            it.forEach {
                myViewModel.getDataYear(store,it.id).observe(this) {
                    oneChartExpense(it)
                }
            }

        }

    }

    private fun chartAllIncome() {

        databaseTow.getAllDaoTow().getDataId(stock).observe(this) { it ->
            it.forEach {
                myViewModel.getDataM(it.id).observe(this) {
                    oneChartIncome(it)
                }
            }
        }

    }


    private fun chartAllExpense() {

        databaseTow.getAllDaoTow().getDataId(stock).observe(this) { it ->
            it.forEach {
                myViewModel.getDataM(it.id).observe(this) {
                    oneChartExpense(it)
                }
            }
        }
    }

    private fun oneChartIncome(it: List<ModelM>) {

        var income = 0.0

        var home = 0.0
        var tranH = 0
        var business = 0.0
        var tranB = 0
        var loan = 0.0
        var tranL = 0
        var investment = 0.0
        var tranI = 0
        var planing = 0.0
        var tranP = 0
        var rent = 0.0
        var tranR = 0
        var other = 0.0
        var tranO = 0

        val type=HelperClass.INCOME

        if (it.isEmpty()) {
            val chart = binding.pieChart
            chart.clear() // Clear previous data
            chart.setNoDataText("") // Clear the "No data available" text

            // Set a single color for the chart when there's no data
            val noDataColor = Color.parseColor("#D3D3D3") // Use a desired color
            val noDataEntries = ArrayList<PieEntry>()
            noDataEntries.add(PieEntry(1f, "No Data"))

            val noDataDataSet = PieDataSet(noDataEntries, "No Data")
            noDataDataSet.color = noDataColor

            val noDataPieData = PieData(noDataDataSet)
            noDataPieData.setDrawValues(false) // Show value labels for this data

            chart.data = noDataPieData
            chart.description.isEnabled = false
            chart.centerText = "Income\n$income" // Remove center text
            chart.setDrawEntryLabels(false) // Show labels outside the chart
            chart.legend.isEnabled = false // Hide legend


            val arrayList = ArrayList<StatsModel>()
            arrayList.add(
                StatsModel(
                    R.drawable.assets, 0.0, "Home", 0.0, 0.toString(), R.color.one,type
                )
            )
            arrayList.add(
                StatsModel(
                    R.drawable.bars, 0.0, "Business", 0.0, 0.toString(), R.color.tow,type
                )
            )
            arrayList.add(
                StatsModel(
                    R.drawable.database, 0.0, "Loan", 0.0, 0.toString(), R.color.four,type
                )
            )
            arrayList.add(
                StatsModel(
                    R.drawable.investment, 0.0, "Investment", 0.0, 0.toString(), R.color.five,type
                )
            )
            arrayList.add(
                StatsModel(
                    R.drawable.planning, 0.0, "Planing", 0.0, 0.toString(), R.color.six,type
                )
            )
            arrayList.add(
                StatsModel(
                    R.drawable.deal, 0.0, "Rent", 0.0, 0.toString(), R.color.hol,type
                )
            )
            arrayList.add(
                StatsModel(
                    R.drawable.reduction, 0.0, "Other", 0.0, 0.toString(), R.color.hole_s,type
                )
            )


            binding.statasRecyclerview.setHasFixedSize(true)
            binding.statasRecyclerview.adapter = AdapterStats(this, arrayList, 0,storeAll,weekNumber!!)
            binding.statasRecyclerview.layoutManager = LinearLayoutManager(this)
            binding.statasRecyclerview.addItemDecoration(
                DividerItemDecoration(
                    this, DividerItemDecoration.VERTICAL
                )
            )

            // entry label styling
            chart.setEntryLabelColor(Color.WHITE)
            chart.setEntryLabelTextSize(1f)

            chart.setHoleColor(Color.TRANSPARENT)
            chart.setTransparentCircleColor(Color.TRANSPARENT)
            chart.transparentCircleRadius = 60f
            chart.holeRadius = 70f

            val layoutParams = chart.layoutParams
            layoutParams.height = 500
            // Set the desired height in pixels

        } else {

            it.forEach { data ->

                if (data.type == HelperClass.INCOME) {
                    Log.d("month", data.amount.toString())
                    income += data.amount

                    if (data.category == HelperClass.Home) {
                        home += data.amount
                        tranH += 1
                    }
                    if (data.category == HelperClass.Business) {
                        business += data.amount
                        tranB += 1
                    }
                    if (data.category == HelperClass.Loan) {
                        loan += data.amount
                        tranL += 1
                    }
                    if (data.category == HelperClass.Investment) {
                        investment += data.amount
                        tranI += 1
                    }
                    if (data.category == HelperClass.Planing) {
                        planing += data.amount
                        tranP += 1
                    }
                    if (data.category == HelperClass.Rent) {
                        rent += data.amount
                        tranR += 1
                    }
                    if (data.category == HelperClass.Other) {
                        other += data.amount
                        tranO += 1
                    }
                }

            }


            val homeP = ((home / income) * 100).toInt()
            val businessP = ((business / income) * 100).toInt()
            val loanP = ((loan / income) * 100).toInt()
            val investmentP = ((investment / income) * 100).toInt()
            val planingP = ((planing / income) * 100).toInt()
            val rentP = ((rent / income) * 100).toInt()
            val otherP = ((other / income) * 100).toInt()


            val arrayList = ArrayList<StatsModel>()
            arrayList.add(
                StatsModel(
                    R.drawable.assets,
                    homeP.toDouble(),
                    "Home",
                    home,
                    tranH.toString(),
                    R.color.one,type
                )
            )
            arrayList.add(
                StatsModel(
                    R.drawable.bars,
                    businessP.toDouble(),
                    "Business",
                    business,
                    tranB.toString(),
                    R.color.tow,type
                )
            )
            arrayList.add(
                StatsModel(
                    R.drawable.database,
                    loanP.toDouble(),
                    "Loan",
                    loan,
                    tranL.toString(),
                    R.color.four,type
                )
            )
            arrayList.add(
                StatsModel(
                    R.drawable.investment,
                    investmentP.toDouble(),
                    "Investment",
                    investment,
                    tranI.toString(),
                    R.color.five,type
                )
            )
            arrayList.add(
                StatsModel(
                    R.drawable.planning,
                    planingP.toDouble(),
                    "Planing",
                    planing,
                    tranP.toString(),
                    R.color.six,type
                )
            )
            arrayList.add(
                StatsModel(
                    R.drawable.deal,
                    rentP.toDouble(),
                    "Rent",
                    rent,
                    tranR.toString(),
                    R.color.hol,type
                )
            )
            arrayList.add(
                StatsModel(
                    R.drawable.reduction,
                    otherP.toDouble(),
                    "Other",
                    other,
                    tranO.toString(),
                    R.color.hole_s,type
                )
            )

            binding.statasRecyclerview.setHasFixedSize(true)
            binding.statasRecyclerview.adapter = AdapterStats(this, arrayList, 0,storeAll,weekNumber!!)
            binding.statasRecyclerview.layoutManager = LinearLayoutManager(this)
            binding.statasRecyclerview.addItemDecoration(
                DividerItemDecoration(
                    this, DividerItemDecoration.VERTICAL
                )
            )

            entries = ArrayList()
            entries.add(PieEntry(homeP.toFloat(), "Home"))
            entries.add(PieEntry(businessP.toFloat(), "Business"))
            entries.add(PieEntry(loanP.toFloat(), "Loan"))
            entries.add(PieEntry(investmentP.toFloat(), "Investment"))
            entries.add(PieEntry(planingP.toFloat(), "Planing"))
            entries.add(PieEntry(rentP.toFloat(), "Rent"))
            entries.add(PieEntry(otherP.toFloat(), "Other"))

            val chart = binding.pieChart

            val dataSet = PieDataSet(entries, "Data Description")

            // Define colors for your entries (including empty ones)

            val colors = mutableListOf(
                Color.parseColor("#F44336"), // Home color
                Color.parseColor("#9C27B0"), // Business color
                Color.parseColor("#8BC34A"), // Loan color
                Color.parseColor("#2196F3"), // Investment color
                Color.parseColor("#FFC107"), // Planing color
                Color.parseColor("#4965FD"), // Rent color
                Color.parseColor("#94FD8749"), // Other color
            )

            dataSet.colors = colors

            dataSet.colors = colors

            val pieData = PieData(dataSet)
            pieData.setValueTextSize(12f)
            pieData.setValueTextColor(Color.WHITE)
            pieData.setDrawValues(false)

            chart.data = pieData
            chart.description.isEnabled = true // Hide description
            chart.animateXY(1000, 1000) // Animate the chart
            chart.description.isEnabled = false
            chart.invalidate()

            chart.setDrawEntryLabels(false)




            databaseTow.getAllDaoTow().getDataId(stock).observe(this) {

                if (it.isNullOrEmpty()) {

                    chart.centerText = "Income\n $ $income"

                } else {
                    it.forEach {
                        chart.centerText = "Income\n ${it.currencySymbol} $income"
                    }

                }

            }


            val l = chart.legend
            l.isEnabled = false // to remove out site label

            // entry label styling
            chart.setEntryLabelColor(Color.WHITE)
            chart.setEntryLabelTextSize(1f)

            chart.setHoleColor(Color.TRANSPARENT)
            chart.setTransparentCircleColor(Color.TRANSPARENT)
            chart.transparentCircleRadius = 60f
            chart.holeRadius = 70f

            val layoutParams = chart.layoutParams
            layoutParams.height = 500
            // Set the desired height in pixels
        }

    }

    private fun oneChartExpense(it: List<ModelM>) {

        var expense = 0.0

        var home = 0.0
        var tranH = 0
        var business = 0.0
        var tranB = 0
        var loan = 0.0
        var tranL = 0
        var investment = 0.0
        var tranI = 0
        var planing = 0.0
        var tranP = 0
        var rent = 0.0
        var tranR = 0
        var other = 0.0
        var tranO = 0

        val type=HelperClass.EXPENSE

        if (it.isEmpty()) {
            val chart = binding.pieChart
            chart.clear() // Clear previous data
            chart.setNoDataText("") // Clear the "No data available" text

            // Set a single color for the chart when there's no data
            val noDataColor = Color.parseColor("#D3D3D3") // Use a desired color
            val noDataEntries = ArrayList<PieEntry>()
            noDataEntries.add(PieEntry(1f, "No Data"))

            val noDataDataSet = PieDataSet(noDataEntries, "No Data")
            noDataDataSet.color = noDataColor

            val noDataPieData = PieData(noDataDataSet)
            noDataPieData.setDrawValues(false) // Show value labels for this data

            chart.data = noDataPieData
            chart.description.isEnabled = false
            chart.centerText = "Expense\n$expense" // Remove center text
            chart.setDrawEntryLabels(false) // Show labels outside the chart
            chart.legend.isEnabled = false // Hide legend
            val arrayList = ArrayList<StatsModel>()
            arrayList.add(
                StatsModel(
                    R.drawable.assets, 0.0, "Home", 0.0, 0.toString(), R.color.one,type
                )
            )
            arrayList.add(
                StatsModel(
                    R.drawable.bars, 0.0, "Business", 0.0, 0.toString(), R.color.tow,type
                )
            )
            arrayList.add(
                StatsModel(
                    R.drawable.database, 0.0, "Loan", 0.0, 0.toString(), R.color.four,type
                )
            )
            arrayList.add(
                StatsModel(
                    R.drawable.investment, 0.0, "Investment", 0.0, 0.toString(), R.color.five,type
                )
            )
            arrayList.add(
                StatsModel(
                    R.drawable.planning, 0.0, "Planing", 0.0, 0.toString(), R.color.six,type
                )
            )
            arrayList.add(
                StatsModel(
                    R.drawable.deal, 0.0, "Rent", 0.0, 0.toString(), R.color.hol,type
                )
            )
            arrayList.add(
                StatsModel(
                    R.drawable.reduction, 0.0, "Other", 0.0, 0.toString(), R.color.hole_s,type
                )
            )


            binding.statasRecyclerview.setHasFixedSize(true)
            binding.statasRecyclerview.adapter = AdapterStats(this, arrayList, 1,storeAll,weekNumber!!)
            binding.statasRecyclerview.layoutManager = LinearLayoutManager(this)
            binding.statasRecyclerview.addItemDecoration(
                DividerItemDecoration(
                    this, DividerItemDecoration.VERTICAL
                )
            )
            // entry label styling
            chart.setEntryLabelColor(Color.WHITE)
            chart.setEntryLabelTextSize(1f)

            chart.setHoleColor(Color.TRANSPARENT)
            chart.setTransparentCircleColor(Color.TRANSPARENT)
            chart.transparentCircleRadius = 60f
            chart.holeRadius = 70f

            val layoutParams = chart.layoutParams
            layoutParams.height = 500
            // Set the desired height in pixels

        } else {

            it.forEach { data ->
                if (data.type == HelperClass.EXPENSE) {
                    Log.d("month", data.amount.toString())
                    expense += data.amount

                    if (data.category == HelperClass.Home) {
                        home += data.amount
                        tranH += 1
                    }
                    if (data.category == HelperClass.Business) {
                        business += data.amount
                        tranB += 1
                    }
                    if (data.category == HelperClass.Loan) {
                        loan += data.amount
                        tranL += 1
                    }
                    if (data.category == HelperClass.Investment) {
                        investment += data.amount
                        tranI += 1
                    }
                    if (data.category == HelperClass.Planing) {
                        planing += data.amount
                        tranP += 1
                    }
                    if (data.category == HelperClass.Rent) {
                        rent += data.amount
                        tranR += 1
                    }
                    if (data.category == HelperClass.Other) {
                        other += data.amount
                        tranO += 1
                    }
                }
            }

            Log.d("all", "$home=$business=$loan=$investment=$planing =$rent =$other")


            val homeP = ((home / expense) * 100).toInt()
            val businessP = ((business / expense) * 100).toInt()
            val loanP = ((loan / expense) * 100).toInt()
            val investmentP = ((investment / expense) * 100).toInt()
            val planingP = ((planing / expense) * 100).toInt()
            val rentP = ((rent / expense) * 100).toInt()
            val otherP = ((other / expense) * 100).toInt()


            Log.d("allP", "$homeP=$businessP=$loanP=$investmentP=$planingP=$rentP=$otherP")

            val arrayList = ArrayList<StatsModel>()
            arrayList.add(
                StatsModel(
                    R.drawable.assets,
                    homeP.toDouble(),
                    "Home",
                    home,
                    tranH.toString(),
                    R.color.one,type
                )
            )
            arrayList.add(
                StatsModel(
                    R.drawable.bars,
                    businessP.toDouble(),
                    "Business",
                    business,
                    tranB.toString(),
                    R.color.tow,type
                )
            )
            arrayList.add(
                StatsModel(
                    R.drawable.database,
                    loanP.toDouble(),
                    "Loan",
                    loan,
                    tranL.toString(),
                    R.color.four,type
                )
            )
            arrayList.add(
                StatsModel(
                    R.drawable.investment,
                    investmentP.toDouble(),
                    "Investment",
                    investment,
                    tranI.toString(),
                    R.color.five,type
                )
            )
            arrayList.add(
                StatsModel(
                    R.drawable.planning,
                    planingP.toDouble(),
                    "Planing",
                    planing,
                    tranP.toString(),
                    R.color.six,type
                )
            )
            arrayList.add(
                StatsModel(
                    R.drawable.deal,
                    rentP.toDouble(),
                    "Rent",
                    rent,
                    tranR.toString(),
                    R.color.hol,type
                )
            )
            arrayList.add(
                StatsModel(
                    R.drawable.reduction,
                    otherP.toDouble(),
                    "Other",
                    other,
                    tranO.toString(),
                    R.color.hole_s,type
                )
            )

            binding.statasRecyclerview.setHasFixedSize(true)
            binding.statasRecyclerview.adapter = AdapterStats(this, arrayList, 1,storeAll,weekNumber!!)
            binding.statasRecyclerview.layoutManager = LinearLayoutManager(this)
            binding.statasRecyclerview.addItemDecoration(
                DividerItemDecoration(
                    this, DividerItemDecoration.VERTICAL
                )
            )

            entries = ArrayList()
            entries.add(PieEntry(homeP.toFloat(), "Home"))
            entries.add(PieEntry(businessP.toFloat(), "Business"))
            entries.add(PieEntry(loanP.toFloat(), "Loan"))
            entries.add(PieEntry(investmentP.toFloat(), "Investment"))
            entries.add(PieEntry(planingP.toFloat(), "Planing"))
            entries.add(PieEntry(rentP.toFloat(), "Rent"))
            entries.add(PieEntry(otherP.toFloat(), "Other"))

            val chart = binding.pieChart

            val dataSet = PieDataSet(entries, "Data Description")

            // Define colors for your entries (including empty ones)

            val colors = mutableListOf(
                Color.parseColor("#F44336"), // Home color
                Color.parseColor("#9C27B0"), // Business color
                Color.parseColor("#8BC34A"), // Loan color
                Color.parseColor("#2196F3"), // Investment color
                Color.parseColor("#FFC107"), // Planing color
                Color.parseColor("#4965FD"), // Rent color
                Color.parseColor("#94FD8749"), // Other color
            )

            dataSet.colors = colors

            dataSet.colors = colors

            val pieData = PieData(dataSet)
            pieData.setValueTextSize(12f)
            pieData.setValueTextColor(Color.WHITE)
            pieData.setDrawValues(false)

            chart.data = pieData
            chart.description.isEnabled = true // Hide description
            chart.animateXY(1000, 1000) // Animate the chart
            chart.description.isEnabled = false
            chart.invalidate()

            chart.setDrawEntryLabels(false)

            val databaseTow = DatabaseTow.getInstanceAllTow(this)
            val sharedPreferences =this.getSharedPreferences("Name", Context.MODE_PRIVATE)
            val stock = sharedPreferences.getInt("oldPosition", 0)//come from (adapter_name)

            databaseTow.getAllDaoTow().getDataId(stock).observe(this) {

                if (it.isNullOrEmpty()) {

                    chart.centerText = "Expense\n $ $expense"

                } else {

                    it.forEach {
                        chart.centerText = "Expense\n ${it.currencySymbol} $expense"
                    }

                }

            }



            val l = chart.legend
            l.isEnabled = false // to remove out site label

            // entry label styling
            chart.setEntryLabelColor(Color.WHITE)
            chart.setEntryLabelTextSize(1f)

            chart.setHoleColor(Color.TRANSPARENT)
            chart.setTransparentCircleColor(Color.TRANSPARENT)
            chart.transparentCircleRadius = 60f
            chart.holeRadius = 70f

            val layoutParams = chart.layoutParams
            layoutParams.height = 500
            // Set the desired height in pixels
        }
    }
}