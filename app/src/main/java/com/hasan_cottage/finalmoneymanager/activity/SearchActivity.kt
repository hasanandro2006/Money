package com.hasan_cottage.finalmoneymanager.activity

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.adapter.AdapterCategory
import com.hasan_cottage.finalmoneymanager.adapter.AdapterMainRecyclerview
import com.hasan_cottage.finalmoneymanager.databinding.ActivitySearchBinding
import com.hasan_cottage.finalmoneymanager.databinding.RandomRecyclerviewBinding
import com.hasan_cottage.finalmoneymanager.helper.HelperClass
import com.hasan_cottage.finalmoneymanager.model.CategoryModel
import com.hasan_cottage.finalmoneymanager.roomDatabase.DatabaseAll
import com.hasan_cottage.finalmoneymanager.roomDatabase.Repository
import com.hasan_cottage.finalmoneymanager.viewModelClass.AppViewModel
import com.hasan_cottage.finalmoneymanager.viewModelClass.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import java.util.Calendar
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity(), AdapterCategory.CategoryClick {
    lateinit var binding: ActivitySearchBinding
    private lateinit var categoryName: String
    private lateinit var alertDialog: AlertDialog
    lateinit var myViewModel: AppViewModel

    // LiveData for observing selected date changes
    private val selectedDateLiveData = MutableLiveData<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val daoM = DatabaseAll.getInstanceAll(this).getAllDaoM()
        val repository = Repository(daoM)
        myViewModel =
            ViewModelProvider(this, ViewModelFactory(repository))[AppViewModel::class.java]

        val calendar = Calendar.getInstance()
        // Back Event
        binding.back.setOnClickListener { onBackPressed() }

        // date pick .....
        MainScope().launch(Dispatchers.Default){
            binding.Date.setOnClickListener {
                val datePickerDialog = DatePickerDialog(this@SearchActivity)
                datePickerDialog.setOnDateSetListener { datePicker, _, _, _ ->
                    calendar.set(Calendar.DAY_OF_MONTH, datePicker.dayOfMonth)
                    calendar.set(Calendar.MONTH, datePicker.month)
                    calendar.set(Calendar.YEAR, datePicker.year)

                    val getDate = HelperClass.dateFormat(calendar.time)
                    binding.date.text = getDate

                    binding.close.setImageResource(R.drawable.baseline_close)
                    selectedDateLiveData.value = getDate
                }
                datePickerDialog.show()
            }
        }


        // Initialize LiveData with default value
        val calendar1 = Calendar.getInstance()
        val getDate1 = HelperClass.dateFormat(calendar1.time)
        selectedDateLiveData.value = getDate1
        binding.date.text = getDate1

        // Observe LiveData changes
        selectedDateLiveData.observe(this) { selectedDate ->
            myViewModel.getDataDaily(selectedDate).observe(this) { data ->
                if (data.isEmpty()) {
                    binding.empty.visibility = View.VISIBLE
                    val adapter = AdapterMainRecyclerview(this, data)
                    binding.recyclerView.adapter = adapter
                    binding.recyclerView.setHasFixedSize(true)
                    binding.recyclerView.addItemDecoration(
                        DividerItemDecoration(
                            this, DividerItemDecoration.VERTICAL
                        )
                    )
                    binding.recyclerView.layoutManager = LinearLayoutManager(this)
                } else {
                    binding.empty.visibility = View.GONE
                    val adapter = AdapterMainRecyclerview(this, data)
                    binding.recyclerView.adapter = adapter
                    binding.recyclerView.setHasFixedSize(true)
                    binding.recyclerView.addItemDecoration(
                        DividerItemDecoration(
                            this, DividerItemDecoration.VERTICAL
                        )
                    )
                    binding.recyclerView.layoutManager = LinearLayoutManager(this)
                }
            }
        }

        // Category Click listener ...........
        binding.Catagory.setOnClickListener {

            val random = RandomRecyclerviewBinding.inflate(layoutInflater) // any single layout call
            val adapter = AdapterCategory(this, HelperClass.categoryItem(), this)
            random.randomRecyclerview.adapter = adapter
            random.randomRecyclerview.setHasFixedSize(true)
            random.randomRecyclerview.layoutManager = GridLayoutManager(this, 3)


            alertDialog = AlertDialog.Builder(this).setView(random.root).create()
            alertDialog.show()

        }

    }

    override fun click(category: CategoryModel) {
        binding.catagory.text = category.name
        categoryName = category.name
        binding.close1.setImageResource(R.drawable.baseline_close)
        alertDialog.dismiss()
    }

    override fun onResume() {
        super.onResume()

        // Automatically request focus when the activity resumes
        binding.searceViewSs.requestFocus()

        // Show the keyboard
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(binding.searceViewSs, InputMethodManager.SHOW_IMPLICIT)
    }
}


