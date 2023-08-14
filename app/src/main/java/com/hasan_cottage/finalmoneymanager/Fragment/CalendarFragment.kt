package com.hasan_cottage.finalmoneymanager.Fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hasan_cottage.finalmoneymanager.Adapter.Adapter_mainrecyclerview
import com.hasan_cottage.finalmoneymanager.BottomFragment.BottomShetFragmentCoseName
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


    lateinit var viewmodelM: Appviewmodel
    val arrayListRecyclerview: ArrayList<ModelM> = ArrayList()
    var store: String? = null
    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCalendarBinding.inflate(inflater)

        val context = requireContext().applicationContext// get Context
        val sharedPreferences = context.getSharedPreferences("Time", Context.MODE_PRIVATE)
        val daily: Int = sharedPreferences.getInt("Daily", 1)


        // room database
        val daoM = DatabaseAll.getInstanceAll(context).getAllDaoM()
        val repostryM = Repostry(daoM)
        viewmodelM =
            ViewModelProvider(this, ViewmodelFactory(repostryM)).get(Appviewmodel::class.java)


        when (daily) {
            1 -> {
                dateFormetDay()
                setForDay(context)

            }

            2 -> {

            }

            3 -> {
                dateFormetMonth()
                setForMonth(context)
            }

            4 -> {
                dateFormetYear()
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
                    dateFormetDay()
                    setForDay(context)
                }

                2 -> {

                }

                3 -> {
                    calendar.add(Calendar.MONTH, -1)
                    dateFormetMonth()
                    setForMonth(context)
                }

                4 -> {
                    calendar.add(Calendar.YEAR, -1)
                    dateFormetYear()
                    setForYear()
                }

            }

        }
        binding.month2.setOnClickListener {

            when (daily) {
                1 -> {
                    calendar.add(Calendar.DAY_OF_MONTH, 1)
                    dateFormetDay()
                    setForDay(context)
                }

                2 -> {

                }

                3 -> {
                    calendar.add(Calendar.MONTH, 1)
                    dateFormetMonth()
                    setForMonth(context)
                }

                4 -> {
                    calendar.add(Calendar.YEAR, 1)
                    dateFormetYear()
                    setForYear()
                }

            }

        }

        binding.MoreOption.setOnClickListener {
            val bottomSheetFragment = BottomShetFragmentCoseName(1)
            bottomSheetFragment.show(requireFragmentManager(), bottomSheetFragment.tag)
        }
        binding.choseChange.setOnClickListener {
            val bottomSheetFragment = BottomShetFragmentCoseName(1)
            bottomSheetFragment.show(requireFragmentManager(), bottomSheetFragment.tag)
        }

        return binding.root
    }


    // set date format---------
    fun dateFormetDay() {
        store = HelperClass.dateFormet(calendar.time)
        binding.monthSet.text = store
    }

    fun dateFormetMonth() {
        val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        store = dateFormat.format(calendar.time)
        binding.monthSet.text = store
    }

    private fun dateFormetYear() {
        val dateFormat = SimpleDateFormat("yyyy", Locale.getDefault())
        store = dateFormat.format(calendar.time)
        binding.monthSet.text = store
    }


    // set data with livedata---------
    private fun setForDay(context: Context) {

        viewmodelM.getDataDily(store!!).observe(viewLifecycleOwner, Observer {
            sameCodeSet(context, it)
        })

    }

    fun setForMonth(context: Context) {

        viewmodelM.getMOnth(store!!).observe(viewLifecycleOwner, Observer {
            sameCodeSet(context, it)
        })

    }

    private fun setForYear() {
        viewmodelM.getDataYear(store!!).observe(viewLifecycleOwner, Observer {
            sameCodeSet(context, it)
        })
    }

    private fun setForAll(context: Context?) {
        binding.monthSet.text = "All Transaction"
        viewmodelM.getDataM().observe(viewLifecycleOwner, Observer {
            sameCodeSet(context, it)
        })
    }

    private fun sameCodeSet(context: Context?, it: List<ModelM>) {
        arrayListRecyclerview.addAll(it)
        var storeT: Double = 0.0
        var incomeT: Double = 0.0
        var expenseT: Double = 0.0
        it.forEach { data ->

            storeT += data.amount

            if (data.type.equals(HelperClass.INCOME)) {
                incomeT += data.amount
            } else if (data.type.equals(HelperClass.EXPENSE)) {
                expenseT += data.amount
            }
        }
        Log.d("main2", it.toString())

        binding.totalS.text = storeT.toString()
        binding.incomeS.text = incomeT.toString()
        binding.expanseS.text = "- " + expenseT.toString()


        val adapter = Adapter_mainrecyclerview(requireActivity() as AppCompatActivity, it)
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
}
