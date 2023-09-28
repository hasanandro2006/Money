package com.hasan_cottage.finalmoneymanager.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.hasan_cottage.finalmoneymanager.helper.HelperClass
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.roomDatabase.DatabaseAll
import com.hasan_cottage.finalmoneymanager.roomDatabase.ModelM
import com.hasan_cottage.finalmoneymanager.roomDatabase.Repository
import com.hasan_cottage.finalmoneymanager.activity.ExpenseIncomeChart
import com.hasan_cottage.finalmoneymanager.activity.SearchActivity
import com.hasan_cottage.finalmoneymanager.activity.TakeCalender
import com.hasan_cottage.finalmoneymanager.bottomFragment.BottomSheetFragmentName
import com.hasan_cottage.finalmoneymanager.databinding.FragmentStatasBinding
import com.hasan_cottage.finalmoneymanager.roomDatabaseNot.DatabaseTow
import com.hasan_cottage.finalmoneymanager.viewModelClass.AppViewModel
import com.hasan_cottage.finalmoneymanager.viewModelClass.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class StatsFragment : Fragment() {
    val binding by  lazy {
        FragmentStatasBinding.inflate(layoutInflater)
    }
    private lateinit var myViewModel: AppViewModel

    private var calender = Calendar.getInstance()
    private var store: String? = null
    private var weekNumber: Int? = null
    private lateinit var entries: ArrayList<PieEntry>

    private var symble: String? = null

    private lateinit var databaseTow:DatabaseTow
    private  var stock=0
    var isViewCreated=false

//    private var isViewCreated = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View ?{


        val context = requireActivity()
        val daoM = DatabaseAll.getInstanceAll(context).getAllDaoM()
        val repository = Repository(daoM)
        myViewModel =
            ViewModelProvider(this, ViewModelFactory(repository))[AppViewModel::class.java]

       isViewCreated=true

        binding.searce.setOnClickListener {
            MainScope().launch(Dispatchers.Default){
                startActivity(Intent(context, SearchActivity::class.java))
            }

        }

         databaseTow = DatabaseTow.getInstanceAllTow(requireContext())
        val sharedPreferencesA= requireContext().getSharedPreferences("Name", Context.MODE_PRIVATE)
         stock = sharedPreferencesA.getInt("oldPosition", 0)//come from (adapter_name)

        val sharedPreferences = context.getSharedPreferences("Time", Context.MODE_PRIVATE)
        val daily: Int = sharedPreferences.getInt("Daily", 1)



        // when chose time .....
        when (daily) {
            1 -> {

                dateFormatDay()
            }

            2 -> {
                dateFormatWeek()
            }

            3 -> {
                dateFormatMonth()
            }

            4 -> {
                dateFormatYear()
            }

            5 -> {
                dateFormatAll()
            }


        }


        // click previous
        binding.monthS1.setOnClickListener {

            when (daily) {
                1 -> {
                    calender.add(Calendar.DAY_OF_MONTH, -1)
                    binding.pieChart.notifyDataSetChanged()
                    dateFormatDay()
                }

                2 -> {
                    calender.add(Calendar.WEEK_OF_YEAR, -1)
                    weekNumber = calender.get(Calendar.WEEK_OF_YEAR)
                    binding.pieChart.notifyDataSetChanged()
                    dateFormatWeek()
                }

                3 -> {
                    calender.add(Calendar.MONTH, -1)
                    binding.pieChart.notifyDataSetChanged()
                    dateFormatMonth()
                }

                4 -> {
                    calender.add(Calendar.YEAR, -1)
                    binding.pieChart.notifyDataSetChanged()
                    dateFormatYear()
                }
            }


        }

        // click next ...
        binding.monthS2.setOnClickListener {
            when (daily) {
                1 -> {
                    calender.add(Calendar.DAY_OF_MONTH, 1)
                    binding.pieChart.notifyDataSetChanged()
                    dateFormatDay()
                }

                2 -> {
                    calender.add(Calendar.WEEK_OF_YEAR, 1)
                    weekNumber = calender.get(Calendar.WEEK_OF_YEAR)
                    binding.pieChart.notifyDataSetChanged()
                    dateFormatWeek()
                }

                3 -> {
                    calender.add(Calendar.MONTH, 1)
                    binding.pieChart.notifyDataSetChanged()
                    dateFormatMonth()
                }

                4 -> {
                    calender.add(Calendar.YEAR, 1)
                    binding.pieChart.notifyDataSetChanged()
                    dateFormatYear()
                }
            }


        }

        // pass data in chart activity ......

        binding.linearLayout2.setOnClickListener {
            val intent = Intent(context, ExpenseIncomeChart::class.java)
            if (weekNumber != null) {
                intent.putExtra("week", weekNumber)
                intent.putExtra("isFalse", false)
            }
            if (store == null) {
                intent.putExtra("nowData", "store")
                intent.putExtra("isFalse", false)
            } else {
                intent.putExtra("nowData", store)
                intent.putExtra("isFalse", false)
            }

            startActivity(intent)

        }
        binding.linearLayout3.setOnClickListener {
            val intent = Intent(context, ExpenseIncomeChart::class.java)
            if (weekNumber != null) {
                intent.putExtra("week", weekNumber)
            }
            if (store == null) {
                intent.putExtra("nowData", "select")
            } else {
                intent.putExtra("nowData", store)
            }
            startActivity(intent)
        }

        // click and come bottom fragment .......
        binding.moreItem.setOnClickListener {
            val bottomSheetFragment = BottomSheetFragmentName(2)
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)

        }
        binding.choseChange2.setOnClickListener {
            val bottomSheetFragment = BottomSheetFragmentName(2)
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
        }
        binding.dittles.setOnClickListener {
            startActivity(Intent(context, TakeCalender::class.java))
        }


        return binding.root
    }


    // set date formed ..........
    private fun dateFormatDay() {
        store = HelperClass.dateFormat(calender.time)
        binding.monthSet.text = store
        dayExpenseIncome()
    }

    private fun dateFormatWeek() {
        val sdf = SimpleDateFormat("dd MMM", Locale.getDefault())

        calender.set(Calendar.DAY_OF_WEEK, calender.firstDayOfWeek)
        val fN = sdf.format(calender.time)
        calender.add(Calendar.DAY_OF_WEEK, 6)
        val lN = sdf.format(calender.time)

        weekNumber = calender.get(Calendar.WEEK_OF_YEAR)
        val stores = "$fN - $lN"
        binding.monthSet.text = stores
        weekExpenseIncome()

    }


    private fun dateFormatMonth() {
        val getFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        store = getFormat.format(calender.time)
        binding.monthSet.text = store
        monthExpenseIncome()
    }

    private fun dateFormatYear() {
        val dateFormat = SimpleDateFormat("yyyy", Locale.getDefault())
        store = dateFormat.format(calender.time)
        binding.monthSet.text = store
        yearExpenseIncome()
    }

    private fun dateFormatAll() {
        binding.monthSet.setText(R.string.all_transaction)
        databaseTow.getAllDaoTow().getDataId(stock).observe(requireActivity()) { it ->
            it.forEach {
                myViewModel.getDataM(it.id).observe(viewLifecycleOwner) {
                    allCodeExpense(it)
                    allCodeIncome(it)
                }
            }

        }



    }


    // come data from view-model........
    private fun dayExpenseIncome() {

        if (isViewCreated) {
            databaseTow.getAllDaoTow().getDataId(stock).observe(viewLifecycleOwner) { it ->
                it.forEach {
                    myViewModel.getDataDaily(store!!, it.id).observe(viewLifecycleOwner) {
                        allCodeIncome(it)
                        allCodeExpense(it)
                    }
                }

            }
        }

    }

    private fun weekExpenseIncome() {
        if (isViewCreated) {
            databaseTow.getAllDaoTow().getDataId(stock).observe(viewLifecycleOwner) { it ->
                it.forEach {
                    myViewModel.getDataBetweenDates(weekNumber!!, it.id)
                        .observe(viewLifecycleOwner) {
                            allCodeIncome(it)
                            allCodeExpense(it)
                        }
                }

            }
        }

    }

    private fun monthExpenseIncome() {
        if (isViewCreated) {
            databaseTow.getAllDaoTow().getDataId(stock).observe(viewLifecycleOwner) { it ->
                it.forEach {
                    myViewModel.getMonth(store!!, it.id).observe(viewLifecycleOwner) {
                        allCodeIncome(it)
                        allCodeExpense(it)
                    }
                }

            }
        }

    }

    private fun yearExpenseIncome() {

        if (isViewCreated) {
            databaseTow.getAllDaoTow().getDataId(stock).observe(viewLifecycleOwner) { it ->
                it.forEach {
                    myViewModel.getDataYear(store!!, it.id).observe(viewLifecycleOwner) {
                        allCodeIncome(it)
                        allCodeExpense(it)
                    }
                }

            }
        }


    }


    // all income expense .............
    private fun allCodeIncome(it: List<ModelM>) {

        var total:Long = 0
        var income:Long = 0
        var expense:Long= 0
        var home = 0.0
        var business = 0.0
        var loan = 0.0
        var investment = 0.0
        var planing = 0.0
        var rent = 0.0
        var other = 0.0

        var ills = 0.0
        var lothing = 0.0
        var ducation = 0.0
        var ntertainment = 0.0
        var itness = 0.0
        var ood = 0.0
        var ifts = 0.0
        var ealth = 0.0
        var urniture = 0.0
        var hopping = 0.0
        var ransportation = 0.0
        var ravel = 0.0

        it.forEach { data ->
            total += data.amount
            if (data.type == HelperClass.INCOME) {
                Log.d("month", data.amount.toString())
                income += data.amount

                if (data.category == HelperClass.Home) {
                    home += data.amount
                }
                if (data.category == HelperClass.Business) {
                    business += data.amount
                }
                if (data.category == HelperClass.Loan) {
                    loan += data.amount
                }
                if (data.category == HelperClass.Investment) {
                    investment += data.amount
                }
                if (data.category == HelperClass.Planing) {
                    planing += data.amount
                }
                if (data.category == HelperClass.Rent) {
                    rent += data.amount
                }
                if (data.category == HelperClass.Other) {
                    other += data.amount
                }

                if (data.category == HelperClass.Bills) {
                    ills += data.amount
                }
                if (data.category == HelperClass.Clothing) {
                    lothing += data.amount
                }
                if (data.category == HelperClass.Education) {
                    ducation += data.amount
                }
                if (data.category == HelperClass.Entertainment) {
                    ntertainment += data.amount
                }
                if (data.category == HelperClass.Fitness) {
                    itness += data.amount
                }
                if (data.category == HelperClass.Food) {
                    ood += data.amount
                }
                if (data.category == HelperClass.Gifts) {
                    ifts += data.amount
                }

                if (data.category == HelperClass.Health) {
                    ealth += data.amount
                }
                if (data.category == HelperClass.Furniture) {
                    urniture += data.amount
                }
                if (data.category == HelperClass.Shopping) {
                    hopping += data.amount
                }
                if (data.category == HelperClass.Transportation) {
                    ransportation += data.amount
                }
                if (data.category == HelperClass.Travel) {
                    ravel += data.amount
                }
            } else if (data.type == HelperClass.EXPENSE) {
                expense += data.amount


            }

        }


        val homeP = ((home / income) * 100).toInt()
        val businessP = ((business / income) * 100).toInt()
        val loanP = ((loan / income) * 100).toInt()
        val investmentP = ((investment / income) * 100).toInt()
        val planingP = ((planing / income) * 100).toInt()
        val rentP = ((rent / income) * 100).toInt()
        val otherP = ((other / income) * 100).toInt()

        val illsP = ((ills / income) * 100).toInt()
        val loathingP = ((lothing/ income) * 100).toInt()
        val ducationP = ((ducation / income) * 100).toInt()
        val ntertainmentP = ((ntertainment / income) * 100).toInt()
        val itnessP = ((itness / income) * 100).toInt()
        val oodP = ((ood / income) * 100).toInt()
        val iftsP = ((ifts / income) * 100).toInt()

        val ealthP = ((ealth / income) * 100).toInt()
        val urnitureP = ((urniture/ income) * 100).toInt()
        val hoppingP = ((hopping / income) * 100).toInt()
        val ransportationP = ((ransportation / income) * 100).toInt()
        val ravelP = ((ravel / income) * 100).toInt()



        entries = ArrayList()
        entries.add(PieEntry(homeP.toFloat(), "Home"))
        entries.add(PieEntry(businessP.toFloat(), "Business"))
        entries.add(PieEntry(loanP.toFloat(), "Loan"))
        entries.add(PieEntry(investmentP.toFloat(), "Investment"))
        entries.add(PieEntry(planingP.toFloat(), "Planing"))
        entries.add(PieEntry(rentP.toFloat(), "Rent"))
        entries.add(PieEntry(otherP.toFloat(), "Other"))

        entries.add(PieEntry(illsP.toFloat(), "Bills"))
        entries.add(PieEntry(loathingP.toFloat(), "Clothing"))
        entries.add(PieEntry(ducationP.toFloat(), "Education"))
        entries.add(PieEntry(ntertainmentP.toFloat(), "Entertainment"))
        entries.add(PieEntry(itnessP.toFloat(), "Fitness"))
        entries.add(PieEntry(oodP.toFloat(), "Food"))
        entries.add(PieEntry(iftsP.toFloat(), "Gifts"))

        entries.add(PieEntry(ealthP.toFloat(), "Health"))
        entries.add(PieEntry(urnitureP.toFloat(), "Furniture"))
        entries.add(PieEntry(hoppingP.toFloat(), "Shopping"))
        entries.add(PieEntry(ransportationP.toFloat(), "Transportation"))
        entries.add(PieEntry(ravelP.toFloat(), "Travel"))

        val chart = binding.pieChart2

        val dataSet = PieDataSet(entries, "Data Description")
        dataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()

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


        databaseTow.getAllDaoTow().getDataId(stock).observe(viewLifecycleOwner) { it ->

            if (it.isNullOrEmpty()) {
                symble="$"

                binding.incomeStaI.text = "$symble $income"
                val stores = " -$symble $expense"
                binding.expenseStaI.text = stores
                binding.totalStaI.text = "$symble $total"

                chart.centerText = "Income\n $symble $income"

            } else {
                it.forEach {
                    symble = it.currencySymbol

                    binding.incomeStaI.text = "$symble $income"
                    val stores = " -$symble $expense"
                    binding.expenseStaI.text = stores
                    binding.totalStaI.text = "$symble $total"

                    chart.centerText = "Income\n $symble $income"
                }

            }

        }




        val l = chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.CENTER
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.xEntrySpace = 70f
        l.yEntrySpace = 20f
        l.yOffset = 10f

        // entry label styling
        chart.setEntryLabelColor(Color.WHITE)
        chart.setEntryLabelTextSize(1f)


        // Manually format the labels and values
        val formattedEntries = entries.map { entry ->
            val formattedLabel = "${entry.label}\n   (${entry.value}%)"
            PieEntry(entry.value, formattedLabel)
        }

        val customDataSet = PieDataSet(formattedEntries, "")
        customDataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()

        val customPieData = PieData(customDataSet)
        customPieData.setValueTextSize(0f) // Hide value labels
        customPieData.setValueTextColor(Color.WHITE)

        chart.data = customPieData



        chart.setHoleColor(Color.TRANSPARENT)
        chart.setTransparentCircleColor(Color.TRANSPARENT)
        chart.transparentCircleRadius = 60f
        chart.holeRadius = 75f

        val layoutParams = chart.layoutParams
        layoutParams.height = 400 // Set the desired height in pixels
//        chart.layoutParams = layoutParams
    }

    private fun allCodeExpense(it: List<ModelM>) {

        var total:Long = 0
        var income:Long = 0
        var expense:Long = 0

        var home = 0.0
        var business = 0.0
        var loan = 0.0
        var investment = 0.0
        var planing = 0.0
        var rent = 0.0
        var other = 0.0

        var ills = 0.0
        var lothing = 0.0
        var ducation = 0.0
        var ntertainment = 0.0
        var itness = 0.0
        var ood = 0.0
        var ifts = 0.0
        var ealth = 0.0
        var urniture = 0.0
        var hopping = 0.0
        var ransportation = 0.0
        var ravel = 0.0

        it.forEach { data ->
            total += data.amount
            if (data.type == HelperClass.EXPENSE) {
                Log.d("month", data.amount.toString())
                expense += data.amount

                if (data.category == HelperClass.Home) {
                    home += data.amount
                }
                if (data.category == HelperClass.Business) {
                    business += data.amount
                }
                if (data.category == HelperClass.Loan) {
                    loan += data.amount
                }
                if (data.category == HelperClass.Investment) {
                    investment += data.amount
                }
                if (data.category == HelperClass.Planing) {
                    planing += data.amount
                }
                if (data.category == HelperClass.Rent) {
                    rent += data.amount
                }
                if (data.category == HelperClass.Other) {
                    other += data.amount
                }


                if (data.category == HelperClass.Bills) {
                    ills += data.amount
                }
                if (data.category == HelperClass.Clothing) {
                    lothing += data.amount
                }
                if (data.category == HelperClass.Education) {
                    ducation += data.amount
                }
                if (data.category == HelperClass.Entertainment) {
                    ntertainment += data.amount
                }
                if (data.category == HelperClass.Fitness) {
                    itness += data.amount
                }
                if (data.category == HelperClass.Food) {
                    ood += data.amount
                }
                if (data.category == HelperClass.Gifts) {
                    ifts += data.amount
                }

                if (data.category == HelperClass.Health) {
                    ealth += data.amount
                }
                if (data.category == HelperClass.Furniture) {
                    urniture += data.amount
                }
                if (data.category == HelperClass.Shopping) {
                    hopping += data.amount
                }
                if (data.category == HelperClass.Transportation) {
                    ransportation += data.amount
                }
                if (data.category == HelperClass.Travel) {
                    ravel += data.amount
                }
            } else if (data.type == HelperClass.INCOME) {
                income += data.amount

            }

        }

        binding.incomeStaI.text = income.toString()
        val stores = "- $expense"
        binding.expenseStaI.text = stores
        binding.totalStaI.text = total.toString()



        val homeP = ((home / expense) * 100).toInt()
        val businessP = ((business / expense) * 100).toInt()
        val loanP = ((loan / expense) * 100).toInt()
        val investmentP = ((investment / expense) * 100).toInt()
        val planingP = ((planing / expense) * 100).toInt()
        val rentP = ((rent / expense) * 100).toInt()
        val otherP = ((other / expense) * 100).toInt()

        val illsP = ((ills / expense) * 100).toInt()
        val loathingP = ((lothing/ expense) * 100).toInt()
        val ducationP = ((ducation / expense) * 100).toInt()
        val ntertainmentP = ((ntertainment / expense) * 100).toInt()
        val itnessP = ((itness / expense) * 100).toInt()
        val oodP = ((ood / expense) * 100).toInt()
        val iftsP = ((ifts / expense) * 100).toInt()

        val ealthP = ((ealth / expense) * 100).toInt()
        val urnitureP = ((urniture/ expense) * 100).toInt()
        val hoppingP = ((hopping / expense) * 100).toInt()
        val ransportationP = ((ransportation / expense) * 100).toInt()
        val ravelP = ((ravel / expense) * 100).toInt()



        entries = ArrayList()
        entries.add(PieEntry(homeP.toFloat(), "Home"))
        entries.add(PieEntry(businessP.toFloat(), "Business"))
        entries.add(PieEntry(loanP.toFloat(), "Loan"))
        entries.add(PieEntry(investmentP.toFloat(), "Investment"))
        entries.add(PieEntry(planingP.toFloat(), "Planing"))
        entries.add(PieEntry(rentP.toFloat(), "Rent"))
        entries.add(PieEntry(otherP.toFloat(), "Other"))

        entries.add(PieEntry(illsP.toFloat(), "Bills"))
        entries.add(PieEntry(loathingP.toFloat(), "Clothing"))
        entries.add(PieEntry(ducationP.toFloat(), "Education"))
        entries.add(PieEntry(ntertainmentP.toFloat(), "Entertainment"))
        entries.add(PieEntry(itnessP.toFloat(), "Fitness"))
        entries.add(PieEntry(oodP.toFloat(), "Food"))
        entries.add(PieEntry(iftsP.toFloat(), "Gifts"))

        entries.add(PieEntry(ealthP.toFloat(), "Health"))
        entries.add(PieEntry(urnitureP.toFloat(), "Furniture"))
        entries.add(PieEntry(hoppingP.toFloat(), "Shopping"))
        entries.add(PieEntry(ransportationP.toFloat(), "Transportation"))
        entries.add(PieEntry(ravelP.toFloat(), "Travel"))

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


        databaseTow.getAllDaoTow().getDataId(stock).observe(viewLifecycleOwner) {

            if (it.isNullOrEmpty()) {
                symble="$"
                chart.centerText = "Expense\n $symble $expense"

            } else {
                it.forEach {
                    symble = it.currencySymbol

                    chart.centerText = "Expense\n $symble $expense"

                }
            }

        }

        val l = chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.CENTER
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.xEntrySpace = 70f
        l.yEntrySpace = 20f
        l.yOffset = 10f

        // entry label styling
        chart.setEntryLabelColor(Color.WHITE)
        chart.setEntryLabelTextSize(1f)


        // Manually format the labels and values
        val formattedEntries = entries.map { entry ->
            val formattedLabel = "${entry.label}\n   (${entry.value}%)"
            PieEntry(entry.value, formattedLabel)
        }

        val customDataSet = PieDataSet(formattedEntries, "")
        customDataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()

        val customPieData = PieData(customDataSet)
        customPieData.setValueTextSize(0f) // Hide value labels
        customPieData.setValueTextColor(Color.WHITE)

        chart.data = customPieData



        chart.setHoleColor(Color.TRANSPARENT)
        chart.setTransparentCircleColor(Color.TRANSPARENT)
        chart.transparentCircleRadius = 60f
        chart.holeRadius = 75f

        val layoutParams = chart.layoutParams
        layoutParams.height = 400
        // Set the desired height in pixels

    }
}
