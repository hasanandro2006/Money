package com.hasan_cottage.finalmoneymanager.Activity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.hasan_cottage.finalmoneymanager.Adapter.Adapter_statas
import com.hasan_cottage.finalmoneymanager.Fragment.MainFragment
import com.hasan_cottage.finalmoneymanager.Helper.HelperClass
import com.hasan_cottage.finalmoneymanager.Model.Statas_model
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.RoomdataNot.DatabaseTow
import com.hasan_cottage.finalmoneymanager.Roomdatabase.DatabaseAll
import com.hasan_cottage.finalmoneymanager.Roomdatabase.Repostry
import com.hasan_cottage.finalmoneymanager.databinding.ActivityExpenseIncomeStructerBinding
import com.hasan_cottage.finalmoneymanager.viewmodelclass.Appviewmodel.Appviewmodel
import com.hasan_cottage.finalmoneymanager.viewmodelclass.ViewmodelFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class ExpenseIncomeStructer : AppCompatActivity() {
    lateinit var binding: ActivityExpenseIncomeStructerBinding
    lateinit var viewmodelM: Appviewmodel

    private var calender = Calendar.getInstance()

    lateinit var entries: ArrayList<PieEntry>

    var tabBoolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpenseIncomeStructerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val daoM = DatabaseAll.getInstanceAll(this).getAllDaoM()
        val repostryM = Repostry(daoM)
        viewmodelM = ViewModelProvider(this, ViewmodelFactory(repostryM)).get(Appviewmodel::class.java)


        tabBoolean = intent.getBooleanExtra("isFalse", true)


        if (tabBoolean) {
            updateCalenderFirst(true)
            binding.tabLayout.getTabAt(0)?.select()
        } else {
            updateCalenderFirst(false)
            binding.tabLayout.getTabAt(1)?.select()
        }

        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        updateCalenderFirst(true)
                        tabBoolean = true
                    }

                    1 -> {
                        updateCalenderFirst(false)
                        tabBoolean = false
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })




        binding.monthS1.setOnClickListener {

            calender.add(Calendar.MONTH, -1)
            updateCalender(tabBoolean)

        }
        binding.monthS2.setOnClickListener {

            calender.add(Calendar.MONTH, 1)
            updateCalender(tabBoolean)

        }

    }

    // End Main Method ................

    private fun updateCalenderFirst(boo: Boolean) {
        val sss = intent.getStringExtra("nowData")
        binding.monthSet.text = sss
        if (boo) {
            createChartIncome(sss.toString())
        } else {
            createChartExpense(sss.toString())
        }

        val outputFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        val t = outputFormat.parse(sss.toString())
        calender.time = t

    }

    private fun updateCalender(boo: Boolean) {
        val store: String?
        val outputFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        store = outputFormat.format(calender.time)
        binding.monthSet.text = store
        if (boo) {
            createChartIncome(store)
        } else {
            createChartExpense(store)
        }

    }


    private fun createChartIncome(store: String) {
        viewmodelM.getMOnth(store).observe(this, Observer {
            var Total = 0.0
            var income = 0.0

            var Home = 0.0
            var tranH = 0
            var Business = 0.0
            var tranB = 0
            var Loan = 0.0
            var tranL = 0
            var Investment = 0.0
            var tranI = 0
            var Planing = 0.0
            var tranP = 0
            var Rent = 0.0
            var tranR = 0
            var Other = 0.0
            var tranO = 0

            if (it.isNullOrEmpty()) {
                val chart = binding.pieChart
                chart.clear() // Clear previous data
                chart.setNoDataText("") // Clear the "No data available" text
                val layoutParams = chart.layoutParams
                layoutParams.height = 500

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


                val arrlist = ArrayList<Statas_model>()
                arrlist.add(
                    Statas_model(
                        R.drawable.assets, 0.0, "Home", 0.0, 0.toString(), R.color.one
                    )
                )
                arrlist.add(
                    Statas_model(
                        R.drawable.bars, 0.0, "Business", 0.0, 0.toString(), R.color.tow
                    )
                )
                arrlist.add(
                    Statas_model(
                        R.drawable.database, 0.0, "Loan", 0.0, 0.toString(), R.color.four
                    )
                )
                arrlist.add(
                    Statas_model(
                        R.drawable.investment, 0.0, "Investment", 0.0, 0.toString(), R.color.five
                    )
                )
                arrlist.add(
                    Statas_model(
                        R.drawable.planning, 0.0, "Planing", 0.0, 0.toString(), R.color.six
                    )
                )
                arrlist.add(
                    Statas_model(
                        R.drawable.deal, 0.0, "Rent", 0.0, 0.toString(), R.color.hol
                    )
                )
                arrlist.add(
                    Statas_model(
                        R.drawable.reduction, 0.0, "Other", 0.0, 0.toString(), R.color.hole_s
                    )
                )


                binding.statasRecyclerview.setHasFixedSize(true)
                binding.statasRecyclerview.adapter = Adapter_statas(this, arrlist, 0)
                binding.statasRecyclerview.layoutManager = LinearLayoutManager(this)
                binding.statasRecyclerview.addItemDecoration(
                    DividerItemDecoration(
                        this, DividerItemDecoration.VERTICAL
                    )
                )

            } else {

                val dataSet: PieDataSet

                it.forEach { data ->
                    Total += data.amount
                    if (data.type.equals(HelperClass.INCOME)) {
                        Log.d("month", data.amount.toString())
                        income += data.amount

                        if (data.catagory == HelperClass.Home) {
                            Home += data.amount
                            tranH += 1
                        }
                        if (data.catagory == HelperClass.Business) {
                            Business += data.amount
                            tranB += 1
                        }
                        if (data.catagory == HelperClass.Loan) {
                            Loan += data.amount
                            tranL += 1
                        }
                        if (data.catagory == HelperClass.Investment) {
                            Investment += data.amount
                            tranI += 1
                        }
                        if (data.catagory == HelperClass.Planing) {
                            Planing += data.amount
                            tranP += 1
                        }
                        if (data.catagory == HelperClass.Rent) {
                            Rent += data.amount
                            tranR += 1
                        }
                        if (data.catagory == HelperClass.Other) {
                            Other += data.amount
                            tranO += 1
                        }
                    } else if (data.type.equals(HelperClass.INCOME)) {
                        income += data.amount

                    }

                }

                Log.d("all", "$Home=$Business=$Loan=$Investment=$Planing =$Rent =$Other")


                val HomeP = ((Home / income) * 100).toInt()
                val BusinessP = ((Business / income) * 100).toInt()
                val LoanP = ((Loan / income) * 100).toInt()
                val InvestmentP = ((Investment / income) * 100).toInt()
                val PlaningP = ((Planing / income) * 100).toInt()
                val RentP = ((Rent / income) * 100).toInt()
                val OtherP = ((Other / income) * 100).toInt()


                Log.d("allP", "$HomeP=$BusinessP=$LoanP=$InvestmentP=$PlaningP=$RentP=$OtherP")

                val arrlist = ArrayList<Statas_model>()
                arrlist.add(
                    Statas_model(
                        R.drawable.assets,
                        HomeP.toDouble(),
                        "Home",
                        Home,
                        tranH.toString(),
                        R.color.one
                    )
                )
                arrlist.add(
                    Statas_model(
                        R.drawable.bars,
                        BusinessP.toDouble(),
                        "Business",
                        Business,
                        tranB.toString(),
                        R.color.tow
                    )
                )
                arrlist.add(
                    Statas_model(
                        R.drawable.database,
                        LoanP.toDouble(),
                        "Loan",
                        Loan,
                        tranL.toString(),
                        R.color.four
                    )
                )
                arrlist.add(
                    Statas_model(
                        R.drawable.investment,
                        InvestmentP.toDouble(),
                        "Investment",
                        Investment,
                        tranI.toString(),
                        R.color.five
                    )
                )
                arrlist.add(
                    Statas_model(
                        R.drawable.planning,
                        PlaningP.toDouble(),
                        "Planing",
                        Planing,
                        tranP.toString(),
                        R.color.six
                    )
                )
                arrlist.add(
                    Statas_model(
                        R.drawable.deal,
                        RentP.toDouble(),
                        "Rent",
                        Rent,
                        tranR.toString(),
                        R.color.hol
                    )
                )
                arrlist.add(
                    Statas_model(
                        R.drawable.reduction,
                        OtherP.toDouble(),
                        "Other",
                        Other,
                        tranO.toString(),
                        R.color.hole_s
                    )
                )


                binding.statasRecyclerview.setHasFixedSize(true)
                binding.statasRecyclerview.adapter = Adapter_statas(this, arrlist, 0)
                binding.statasRecyclerview.layoutManager = LinearLayoutManager(this)
                binding.statasRecyclerview.addItemDecoration(
                    DividerItemDecoration(
                        this, DividerItemDecoration.VERTICAL
                    )
                )


                entries = ArrayList<PieEntry>()
                entries.add(PieEntry(HomeP.toFloat(), "Home"))
                entries.add(PieEntry(BusinessP.toFloat(), "Business"))
                entries.add(PieEntry(LoanP.toFloat(), "Loan"))
                entries.add(PieEntry(InvestmentP.toFloat(), "Investment"))
                entries.add(PieEntry(PlaningP.toFloat(), "Planing"))
                entries.add(PieEntry(RentP.toFloat(), "Rent"))
                entries.add(PieEntry(OtherP.toFloat(), "Other"))

                val chart = binding.pieChart

                dataSet = PieDataSet(entries, "Data Description")

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
                chart.centerText = "Income\n$income"
                chart.animateXY(1000, 1000) // Animate the chart
                chart.description.isEnabled = false
                chart.invalidate()

                chart.setDrawEntryLabels(false)

                val l = chart.legend
                l.isEnabled = false // to remove out site lable

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

        })
    }

    private fun createChartExpense(store: String) {
        viewmodelM.getMOnth(store).observe(this, Observer {
            var Total = 0.0
            var income = 0.0
            var expense = 0.0

            var Home = 0.0
            var tranH = 0
            var Business = 0.0
            var tranB = 0
            var Loan = 0.0
            var tranL = 0
            var Investment = 0.0
            var tranI = 0
            var Planing = 0.0
            var tranP = 0
            var Rent = 0.0
            var tranR = 0
            var Other = 0.0
            var tranO = 0

            if (it.isNullOrEmpty()) {
                val chart = binding.pieChart
                chart.clear() // Clear previous data
                chart.setNoDataText("") // Clear the "No data available" text
                val layoutParams = chart.layoutParams
                layoutParams.height = 500

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
                val arrlist = ArrayList<Statas_model>()
                arrlist.add(
                    Statas_model(
                        R.drawable.assets, 0.0, "Home", 0.0, 0.toString(), R.color.one
                    )
                )
                arrlist.add(
                    Statas_model(
                        R.drawable.bars, 0.0, "Business", 0.0, 0.toString(), R.color.tow
                    )
                )
                arrlist.add(
                    Statas_model(
                        R.drawable.database, 0.0, "Loan", 0.0, 0.toString(), R.color.four
                    )
                )
                arrlist.add(
                    Statas_model(
                        R.drawable.investment, 0.0, "Investment", 0.0, 0.toString(), R.color.five
                    )
                )
                arrlist.add(
                    Statas_model(
                        R.drawable.planning, 0.0, "Planing", 0.0, 0.toString(), R.color.six
                    )
                )
                arrlist.add(
                    Statas_model(
                        R.drawable.deal, 0.0, "Rent", 0.0, 0.toString(), R.color.hol
                    )
                )
                arrlist.add(
                    Statas_model(
                        R.drawable.reduction, 0.0, "Other", 0.0, 0.toString(), R.color.hole_s
                    )
                )


                binding.statasRecyclerview.setHasFixedSize(true)
                binding.statasRecyclerview.adapter = Adapter_statas(this, arrlist, 1)
                binding.statasRecyclerview.layoutManager = LinearLayoutManager(this)
                binding.statasRecyclerview.addItemDecoration(
                    DividerItemDecoration(
                        this, DividerItemDecoration.VERTICAL
                    )
                )

            } else {

                val dataSet: PieDataSet

                it.forEach { data ->
                    Total += data.amount
                    if (data.type.equals(HelperClass.EXPENSE)) {
                        Log.d("month", data.amount.toString())
                        expense += data.amount

                        if (data.catagory == HelperClass.Home) {
                            Home += data.amount
                            tranH += 1
                        }
                        if (data.catagory == HelperClass.Business) {
                            Business += data.amount
                            tranB += 1
                        }
                        if (data.catagory == HelperClass.Loan) {
                            Loan += data.amount
                            tranL += 1
                        }
                        if (data.catagory == HelperClass.Investment) {
                            Investment += data.amount
                            tranI += 1
                        }
                        if (data.catagory == HelperClass.Planing) {
                            Planing += data.amount
                            tranP += 1
                        }
                        if (data.catagory == HelperClass.Rent) {
                            Rent += data.amount
                            tranR += 1
                        }
                        if (data.catagory == HelperClass.Other) {
                            Other += data.amount
                            tranO += 1
                        }
                    } else if (data.type.equals(HelperClass.INCOME)) {
                        income += data.amount

                    }

                }

                Log.d("all", "$Home=$Business=$Loan=$Investment=$Planing =$Rent =$Other")


                val HomeP = ((Home / expense) * 100).toInt()
                val BusinessP = ((Business / expense) * 100).toInt()
                val LoanP = ((Loan / expense) * 100).toInt()
                val InvestmentP = ((Investment / expense) * 100).toInt()
                val PlaningP = ((Planing / expense) * 100).toInt()
                val RentP = ((Rent / expense) * 100).toInt()
                val OtherP = ((Other / expense) * 100).toInt()


                Log.d("allP", "$HomeP=$BusinessP=$LoanP=$InvestmentP=$PlaningP=$RentP=$OtherP")

                val arrlist = ArrayList<Statas_model>()
                arrlist.add(
                    Statas_model(
                        R.drawable.assets,
                        HomeP.toDouble(),
                        "Home",
                        Home,
                        tranH.toString(),
                        R.color.one
                    )
                )
                arrlist.add(
                    Statas_model(
                        R.drawable.bars,
                        BusinessP.toDouble(),
                        "Business",
                        Business,
                        tranB.toString(),
                        R.color.tow
                    )
                )
                arrlist.add(
                    Statas_model(
                        R.drawable.database,
                        LoanP.toDouble(),
                        "Loan",
                        Loan,
                        tranL.toString(),
                        R.color.four
                    )
                )
                arrlist.add(
                    Statas_model(
                        R.drawable.investment,
                        InvestmentP.toDouble(),
                        "Investment",
                        Investment,
                        tranI.toString(),
                        R.color.five
                    )
                )
                arrlist.add(
                    Statas_model(
                        R.drawable.planning,
                        PlaningP.toDouble(),
                        "Planing",
                        Planing,
                        tranP.toString(),
                        R.color.six
                    )
                )
                arrlist.add(
                    Statas_model(
                        R.drawable.deal,
                        RentP.toDouble(),
                        "Rent",
                        Rent,
                        tranR.toString(),
                        R.color.hol
                    )
                )
                arrlist.add(
                    Statas_model(
                        R.drawable.reduction,
                        OtherP.toDouble(),
                        "Other",
                        Other,
                        tranO.toString(),
                        R.color.hole_s
                    )
                )


                binding.statasRecyclerview.setHasFixedSize(true)
                binding.statasRecyclerview.adapter = Adapter_statas(this, arrlist, 1)
                binding.statasRecyclerview.layoutManager = LinearLayoutManager(this)
                binding.statasRecyclerview.addItemDecoration(
                    DividerItemDecoration(
                        this, DividerItemDecoration.VERTICAL
                    )
                )


                entries = ArrayList<PieEntry>()
                entries.add(PieEntry(HomeP.toFloat(), "Home"))
                entries.add(PieEntry(BusinessP.toFloat(), "Business"))
                entries.add(PieEntry(LoanP.toFloat(), "Loan"))
                entries.add(PieEntry(InvestmentP.toFloat(), "Investment"))
                entries.add(PieEntry(PlaningP.toFloat(), "Planing"))
                entries.add(PieEntry(RentP.toFloat(), "Rent"))
                entries.add(PieEntry(OtherP.toFloat(), "Other"))

                val chart = binding.pieChart

                dataSet = PieDataSet(entries, "Data Description")

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
                chart.centerText = "Expense\n$expense"
                chart.animateXY(1000, 1000) // Animate the chart
                chart.description.isEnabled = false
                chart.invalidate()

                chart.setDrawEntryLabels(false)

                val l = chart.legend
                l.isEnabled = false // to remove out site lable

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

        })
    }


}
