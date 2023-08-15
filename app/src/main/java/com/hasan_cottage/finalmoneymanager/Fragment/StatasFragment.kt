package com.hasan_cottage.finalmoneymanager.Fragment

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.hasan_cottage.finalmoneymanager.activity.ExpenseIncomeChart
import com.hasan_cottage.finalmoneymanager.BottomFragment.BottomShetFragmentCoseName
import com.hasan_cottage.finalmoneymanager.Helper.HelperClass
import com.hasan_cottage.finalmoneymanager.Roomdatabase.DatabaseAll
import com.hasan_cottage.finalmoneymanager.Roomdatabase.ModelM
import com.hasan_cottage.finalmoneymanager.Roomdatabase.Repostry
import com.hasan_cottage.finalmoneymanager.databinding.FragmentStatasBinding
import com.hasan_cottage.finalmoneymanager.viewmodelclass.Appviewmodel.Appviewmodel
import com.hasan_cottage.finalmoneymanager.viewmodelclass.ViewmodelFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class StatasFragment : Fragment() {
    lateinit var binding: FragmentStatasBinding
    lateinit var viewmodelM: Appviewmodel

    private var calender=Calendar.getInstance()
     var store:String?=null
     var weekNumber:Int?=null
    lateinit var  entries:ArrayList<PieEntry>



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentStatasBinding.inflate(inflater, container, false)


        val context = requireContext().applicationContext
        val daoM = DatabaseAll.getInstanceAll(context).getAllDaoM()
        val repostryM = Repostry(daoM)
        viewmodelM =ViewModelProvider(this, ViewmodelFactory(repostryM)).get(Appviewmodel::class.java)

        val sharedPreferences = context.getSharedPreferences("Time", Context.MODE_PRIVATE)
        val daily: Int = sharedPreferences.getInt("Daily", 1)


        // when chose time .....
        when(daily){
            1 ->{

                dateFormetDay()
            }
            2 ->{
                dateFormetWeek()
            }
            3 ->{
                dateFormetMonth()
            }
            4 ->{
                dateFormetYear()
            }

            5 ->{
                dateFormetAll()
            }


        }



        // click previous
        binding.monthS1.setOnClickListener {

            when(daily){
                1 ->{
                    calender.add(Calendar.DAY_OF_MONTH,-1)
                    binding.pieChart.notifyDataSetChanged()
                    dateFormetDay()
                }
                2 ->{
                    calender.add(Calendar.WEEK_OF_YEAR,-1)
                    binding.pieChart.notifyDataSetChanged()
                    dateFormetWeek()
                }
                3 ->{
                    calender.add(Calendar.MONTH,-1)
                    binding.pieChart.notifyDataSetChanged()
                    dateFormetMonth()
                }
                4 -> {
                    calender.add(Calendar.YEAR, -1)
                    binding.pieChart.notifyDataSetChanged()
                    dateFormetYear()
                }
            }


        }

        // click next ...
        binding.monthS2.setOnClickListener {
            when(daily){
                1 ->{
                    calender.add(Calendar.DAY_OF_MONTH,1)
                    binding.pieChart.notifyDataSetChanged()
                    dateFormetDay()
                }
                2 ->{
                    calender.add(Calendar.WEEK_OF_YEAR,1)
                    binding.pieChart.notifyDataSetChanged()
                    dateFormetWeek()
                }
                3 ->{
                    calender.add(Calendar.MONTH,1)
                    binding.pieChart.notifyDataSetChanged()
                    dateFormetMonth()
                }
                4 ->{
                    calender.add(Calendar.YEAR,1)
                    binding.pieChart.notifyDataSetChanged()
                    dateFormetYear()
                }
            }


        }

        // pass data in chart activity ......

        binding.linearLayout2.setOnClickListener{
            val intent=Intent(context,ExpenseIncomeChart::class.java)

            if (store == null || weekNumber == null){
                intent.putExtra("nowData","select all")
                intent.putExtra("isFalse",false)
            }else if(weekNumber != null){
                intent.putExtra("week",weekNumber)
                intent.putExtra("isFalse",false)
            } else{
                intent.putExtra("nowData",store)
                intent.putExtra("isFalse",false)
            }


            startActivity(intent)

        }
        binding.linearLayout3.setOnClickListener {
            val intent=Intent(context,ExpenseIncomeChart::class.java)
            if (store == null || weekNumber == null){
                intent.putExtra("nowData","select all")
            }else if(weekNumber != null){
                intent.putExtra("week",weekNumber)

            } else{
                intent.putExtra("nowData",store)
            }
            startActivity(intent)
        }

        // click and come bottom fragment .......
        binding.moreItem.setOnClickListener {
            val bottomSheetFragment=BottomShetFragmentCoseName(2)
            bottomSheetFragment.show(requireFragmentManager(),bottomSheetFragment.tag)

        }
        binding.choseChange2.setOnClickListener {
            val bottomSheetFragment=BottomShetFragmentCoseName(2)
            bottomSheetFragment.show(requireFragmentManager(),bottomSheetFragment.tag)
        }

        return binding.root
    }


    // set date formed ..........
    private fun dateFormetDay() {
        store = HelperClass.dateFormet(calender.time)
        binding.monthSet.text = store
       dayExpenseIncome()
    }

    private fun dateFormetWeek() {
        val sdf = SimpleDateFormat("dd MMM", Locale.getDefault())

        calender.set(Calendar.DAY_OF_WEEK,calender.firstDayOfWeek)
        val fN=sdf.format(calender.time)
        calender.add(Calendar.DAY_OF_WEEK,6)
        val lN=sdf.format(calender.time)

        weekNumber=calender.get(Calendar.WEEK_OF_YEAR)
        binding.monthSet.text=fN+" - "+lN
        weekExpenseIncome()

    }



    private fun dateFormetMonth() {
        val getformet=SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        store=getformet.format(calender.time)
        binding.monthSet.text=store
        monthExpenseIncome()
    }
    private fun dateFormetYear() {
        val dateFormat = SimpleDateFormat("yyyy", Locale.getDefault())
        store = dateFormat.format(calender.time)
        binding.monthSet.text = store
        yearExpenseIncome()
    }
    private fun dateFormetAll() {
        binding.monthSet.text = "All Transaction"
        viewmodelM.getDataM().observe(viewLifecycleOwner, Observer {
            allCodeExpense(it)
            allCodeIncome(it)
        })

    }





    // come data from view-model........
    private fun dayExpenseIncome() {
        viewmodelM.getDataDily(store!!).observe(viewLifecycleOwner, Observer {
            allCodeIncome(it)
            allCodeExpense(it)
        })
    }

    private fun weekExpenseIncome() {
        viewmodelM.getDataBetweenDates(weekNumber!!).observe(viewLifecycleOwner){
            allCodeIncome(it)
            allCodeExpense(it)
        }
    }
    private fun monthExpenseIncome() {
        viewmodelM.getMOnth(store!!).observe(viewLifecycleOwner, Observer {
            allCodeIncome(it)
            allCodeExpense(it)
        })
    }
    private fun yearExpenseIncome() {
        viewmodelM.getDataYear(store!!).observe(viewLifecycleOwner, Observer {
            allCodeIncome(it)
            allCodeExpense(it)
        })
    }


    // all income expense .............
    private fun allCodeIncome(it: List<ModelM>) {

        var Total=0.0
        var income=0.0
        var expense=0.0
        var Home = 0.0
        var Business =0.0
        var Loan = 0.0
        var Investment = 0.0
        var Planing = 0.0
        var Rent = 0.0
        var Other = 0.0

        it.forEach { data ->
            Total += data.amount
            if (data.type.equals(HelperClass.INCOME)) {
                Log.d("month",data.amount.toString())
                income +=data.amount

                if (data.catagory == HelperClass.Home) {
                    Home += data.amount
                }
                if (data.catagory == HelperClass.Business) {
                    Business += data.amount
                }
                if (data.catagory == HelperClass.Loan) {
                    Loan += data.amount
                }
                if (data.catagory == HelperClass.Investment) {
                    Investment += data.amount
                }
                if (data.catagory == HelperClass.Planing) {
                    Planing += data.amount
                }
                if (data.catagory == HelperClass.Rent) {
                    Rent += data.amount
                }
                if (data.catagory == HelperClass.Other) {
                    Other += data.amount
                }
            }else if (data.type.equals(HelperClass.EXPENSE)){
                expense +=data.amount


            }

        }

        Log.d("all","$Home=$Business=$Loan=$Investment=$Planing =$Rent =$Other")


        binding.incomeStaI.text = income.toString()
        binding.expenseStaI.text = "- " + expense.toString()
        binding.totalStaI.text = Total.toString()


        val HomeP =( (Home / income) * 100).toInt()
        val BusinessP = ((Business / income) * 100).toInt()
        val LoanP = ((Loan / income) * 100).toInt()
        val InvestmentP =( (Investment / income) * 100).toInt()
        val PlaningP = ((Planing / income) * 100).toInt()
        val RentP = ((Rent / income) * 100).toInt()
        val OtherP = ((Other / income) * 100).toInt()


        Log.d("allP", "$HomeP=$BusinessP=$LoanP=$InvestmentP=$PlaningP=$RentP=$OtherP")



        entries = ArrayList<PieEntry>()
        entries.add(PieEntry(HomeP.toFloat(), "Home"))
        entries.add(PieEntry(BusinessP.toFloat(), "Business"))
        entries.add(PieEntry(LoanP.toFloat(), "Loan"))
        entries.add(PieEntry(InvestmentP.toFloat(), "Investment"))
        entries.add(PieEntry(PlaningP.toFloat(), "Planing"))
        entries.add(PieEntry(RentP.toFloat(), "Rent"))
        entries.add(PieEntry(OtherP.toFloat(), "Other"))

        val chart = binding.pieChart2

        val dataSet = PieDataSet(entries, "Data Description")
        dataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()

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
        chart.holeRadius =75f

        val layoutParams = chart.layoutParams
        layoutParams.height = 400 // Set the desired height in pixels
        chart.layoutParams = layoutParams
    }
    private fun allCodeExpense(it:List<ModelM>) {

        var dataSet:PieDataSet
        var Total=0.0
        var income=0.0
        var expense=0.0

        var Home = 0.0
        var Business =0.0
        var Loan = 0.0
        var Investment = 0.0
        var Planing = 0.0
        var Rent = 0.0
        var Other = 0.0

        it.forEach { data ->
            Total += data.amount
            if (data.type.equals(HelperClass.EXPENSE)) {
                Log.d("month", data.amount.toString())
                expense += data.amount

                if (data.catagory == HelperClass.Home) {
                    Home += data.amount
                }
                if (data.catagory == HelperClass.Business) {
                    Business += data.amount
                }
                if (data.catagory == HelperClass.Loan) {
                    Loan += data.amount
                }
                if (data.catagory == HelperClass.Investment) {
                    Investment += data.amount
                }
                if (data.catagory == HelperClass.Planing) {
                    Planing += data.amount
                }
                if (data.catagory == HelperClass.Rent) {
                    Rent += data.amount
                }
                if (data.catagory == HelperClass.Other) {
                    Other += data.amount
                }
            }
            else if (data.type.equals(HelperClass.INCOME)) {
                income += data.amount

            }

        }

        binding.incomeStaI.text = income.toString()
        binding.expenseStaI.text = "- " + expense.toString()
        binding.totalStaI.text = Total.toString()


        Log.d("all", "$Home=$Business=$Loan=$Investment=$Planing =$Rent =$Other")


        val HomeP = ((Home / expense) * 100).toInt()
        val BusinessP = ((Business / expense) * 100).toInt()
        val LoanP = ((Loan / expense) * 100).toInt()
        val InvestmentP = ((Investment / expense) * 100).toInt()
        val PlaningP = ((Planing / expense) * 100).toInt()
        val RentP = ((Rent / expense) * 100).toInt()
        val OtherP = ((Other / expense) * 100).toInt()


        Log.d("allP", "$HomeP=$BusinessP=$LoanP=$InvestmentP=$PlaningP=$RentP=$OtherP")



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

        dataSet.colors=colors

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
