package com.hasan_cottage.finalmoneymanager.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.adapter.AdapterMainRecyclerview
import com.hasan_cottage.finalmoneymanager.bottomFragment.BottomSheetFragmentName
import com.hasan_cottage.finalmoneymanager.databinding.ActivityExpIncRecyclerItemClickBinding
import com.hasan_cottage.finalmoneymanager.helper.HelperClass
import com.hasan_cottage.finalmoneymanager.roomDatabase.DatabaseAll
import com.hasan_cottage.finalmoneymanager.roomDatabase.ModelM
import com.hasan_cottage.finalmoneymanager.roomDatabase.Repository
import com.hasan_cottage.finalmoneymanager.viewModelClass.AppViewModel
import com.hasan_cottage.finalmoneymanager.viewModelClass.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ExpIncRecyclerItemClick : AppCompatActivity() {

    val binding by lazy {
        ActivityExpIncRecyclerItemClickBinding.inflate(layoutInflater)
    }

    private lateinit var myViewModel: AppViewModel
    private val arrayListRecyclerview: ArrayList<ModelM> = ArrayList()
    private var store: String? = null
    private var weekNumber: Int? = null
    private val calendar = Calendar.getInstance()
    private val calendar7 = Calendar.getInstance()
    private var type: String? = null
    private var category: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.searce.setOnClickListener {
            MainScope().launch(Dispatchers.Default) {
                startActivity(Intent(this@ExpIncRecyclerItemClick, SearchActivity::class.java))
            }
        }


        val date = intent.getStringExtra("date")
        weekNumber = intent.getIntExtra("week", 0)
        val categorySharedPreferences = getSharedPreferences("Time", Context.MODE_PRIVATE)
        category = categorySharedPreferences.getString("category", "All")
        type = categorySharedPreferences.getString("type", HelperClass.EXPENSE)

        binding.Name.text = category
        binding.textView12.text = type


        if (type == HelperClass.INCOME) {
            binding.active.setBackgroundResource(R.drawable.degine_for_active)
            binding.textView12.setTextColor(ContextCompat.getColor(this, R.color.blue))
            binding.expanseS.setTextColor(ContextCompat.getColor(this, R.color.blue))

        } else {
            binding.active.setBackgroundResource(R.drawable.degine_for_nagative)
            binding.textView12.setTextColor(ContextCompat.getColor(this, R.color.red))
            binding.expanseS.setTextColor(ContextCompat.getColor(this, R.color.red))
        }

        binding.MoreOption.setOnClickListener {
            val bottomSheetFragment = BottomSheetFragmentName(4)
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
        binding.choseChange.setOnClickListener {
            val bottomSheetFragment = BottomSheetFragmentName(4)
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }


        val sharedPreferences = getSharedPreferences("Time", Context.MODE_PRIVATE)
        val daily: Int = sharedPreferences.getInt("Daily", 1)


        // room database
        val daoM = DatabaseAll.getInstanceAll(this).getAllDaoM()
        val repository = Repository(daoM)
        myViewModel =
            ViewModelProvider(this, ViewModelFactory(repository))[AppViewModel::class.java]

        binding.searce.setOnClickListener {
            MainScope().launch(Dispatchers.Default) {
                startActivity(Intent(this@ExpIncRecyclerItemClick, SearchActivity::class.java))
            }
        }


        when (daily) {
            1 -> {
                val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                if (date != null) {
                    val t = outputFormat.parse(date.toString())!!
                    calendar.time = t
                }
                dateFormatDay()
                setForDay(this)

            }

            2 -> {
                if (weekNumber != 0) {
                    calendar7.set(Calendar.WEEK_OF_YEAR, weekNumber!!)
                    weekNumber = 0
                }
                dateFormatWeek()
                setForWeek(this)
            }

            3 -> {
                val outputFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
                if (date != null) {
                    val t = outputFormat.parse(date.toString())!!
                    calendar.time = t
                }
                dateFormatMonth()
                setForMonth(this)
            }

            4 -> {
                val outputFormat = SimpleDateFormat("yyyy", Locale.getDefault())
                if (date != null) {
                    val t = outputFormat.parse(date.toString())!!
                    calendar.time = t
                }
                dateFormatYear()
                setForYear(this)

            }

            5 -> {
                setForAll(this)
            }
        }


        binding.monthS1.setOnClickListener {


            when (daily) {
                1 -> {
                    calendar.add(Calendar.DAY_OF_MONTH, -1)
                    dateFormatDay()
                    setForDay(this)
                }

                2 -> {
                    calendar7.add(Calendar.WEEK_OF_YEAR, -1)
                    dateFormatWeek()
                    setForWeek(this)
                }

                3 -> {
                    calendar.add(Calendar.MONTH, -1)
                    dateFormatMonth()
                    setForMonth(this)
                }

                4 -> {
                    calendar.add(Calendar.YEAR, -1)
                    dateFormatYear()
                    setForYear(this)
                }

            }

        }
        binding.monthS2.setOnClickListener {

            when (daily) {
                1 -> {
                    calendar.add(Calendar.DAY_OF_MONTH, 1)
                    dateFormatDay()
                    setForDay(this)
                }

                2 -> {
                    calendar7.add(Calendar.WEEK_OF_YEAR, 1)
                    dateFormatWeek()
                    setForWeek(this)
                }

                3 -> {
                    calendar.add(Calendar.MONTH, 1)
                    dateFormatMonth()
                    setForMonth(this)
                }

                4 -> {
                    calendar.add(Calendar.YEAR, 1)
                    dateFormatYear()
                    setForYear(this)
                }

            }

        }


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

        myViewModel.getDataDaily2(store!!, category!!, type!!).observe(this) { it ->
            if (it.isEmpty()) {
                binding.noDataI.visibility = View.VISIBLE
                binding.noDataT.visibility = View.VISIBLE
                sameCodeSet(context, it)
            } else {
                binding.noDataI.visibility = View.GONE
                binding.noDataT.visibility = View.GONE
                sameCodeSet(context, it)
            }
        }

    }

    private fun setForMonth(context: Context) {
        myViewModel.getDataMonth2(store!!, category!!, type!!).observe(this) { it ->

            if (it.isEmpty()) {
                binding.noDataI.visibility = View.VISIBLE
                binding.noDataT.visibility = View.VISIBLE
                sameCodeSet(context, it)
            } else {
                binding.noDataI.visibility = View.GONE
                binding.noDataT.visibility = View.GONE
                sameCodeSet(context, it)
            }

        }

    }

    private fun setForWeek(context: Context) {
        myViewModel.getDataBetweenDates2(weekNumber!!, category!!, type!!).observe(this) { it ->

            if (it.isEmpty()) {
                binding.noDataI.visibility = View.VISIBLE
                binding.noDataT.visibility = View.VISIBLE
                sameCodeSet(context, it)
            } else {
                binding.noDataI.visibility = View.GONE
                binding.noDataT.visibility = View.GONE
                sameCodeSet(context, it)
            }

        }
    }

    private fun setForYear(context: Context) {
        myViewModel.getDataYear2(store!!, category!!, type!!).observe(this) { it ->
            if (it.isEmpty()) {
                binding.noDataI.visibility = View.VISIBLE
                binding.noDataT.visibility = View.VISIBLE
                sameCodeSet(context, it)
            } else {
                binding.noDataI.visibility = View.GONE
                binding.noDataT.visibility = View.GONE
                sameCodeSet(context, it)
            }

        }
    }

    private fun setForAll(context: Context?) {
        binding.monthSet.setText(R.string.all_transaction)
        myViewModel.getDataM2(category!!).observe(this) { it ->
            if (it.isEmpty()) {
                binding.noDataI.visibility = View.VISIBLE
                binding.noDataT.visibility = View.VISIBLE
                sameCodeSet(context, it)
            } else {
                binding.noDataI.visibility = View.GONE
                binding.noDataT.visibility = View.GONE
                sameCodeSet(context, it)
            }

        }
    }

    private fun sameCodeSet(context: Context?, it: List<ModelM>) {
        arrayListRecyclerview.addAll(it)
        var storeT = 0.0

        it.forEach { data ->

            storeT += data.amount

        }
        Log.d("main2", it.toString())


        val stores = "$storeT"
        binding.expanseS.text = stores


        val adapter = AdapterMainRecyclerview(this, it)
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