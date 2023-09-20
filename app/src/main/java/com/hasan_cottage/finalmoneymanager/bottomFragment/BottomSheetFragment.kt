package com.hasan_cottage.finalmoneymanager.bottomFragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.adapter.AdapterAccount
import com.hasan_cottage.finalmoneymanager.adapter.AdapterCategory
import com.hasan_cottage.finalmoneymanager.databinding.FragmentBottomSheetBinding
import com.hasan_cottage.finalmoneymanager.databinding.RandomRecyclerviewBinding
import com.hasan_cottage.finalmoneymanager.helper.HelperClass
import com.hasan_cottage.finalmoneymanager.model.AccountModel
import com.hasan_cottage.finalmoneymanager.model.CategoryModel
import com.hasan_cottage.finalmoneymanager.roomDatabase.DatabaseAll
import com.hasan_cottage.finalmoneymanager.roomDatabase.ModelM
import com.hasan_cottage.finalmoneymanager.roomDatabase.Repository
import com.hasan_cottage.finalmoneymanager.roomDatabaseNot.DatabaseTow
import com.hasan_cottage.finalmoneymanager.viewModelClass.AppViewModel
import com.hasan_cottage.finalmoneymanager.viewModelClass.ViewModelFactory
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BottomSheetFragment(private val intId: Int, private  val string: String) : BottomSheetDialogFragment(),
    AdapterCategory.CategoryClick, AdapterAccount.ClickItem {


    val binding by lazy {
      FragmentBottomSheetBinding.inflate(layoutInflater)
    }
    private lateinit var alertDialog: AlertDialog
    private lateinit var alertDialogAccount: AlertDialog

    //     var categoryImage:Int?=null
    private var categoryName: String? = null
    private var accountName: String? = null
    private var getDate: String? = null
    private var getDateMonth: String? = null
    private var getAmount: Double? = null
    private var dataType: String? = null
    private var getMonth: String? = null
    private var yearC: String? = null
    private var weekNumber: Int? = null
    private var storeAccountId:Int?=null

    private lateinit var databaseTow:DatabaseTow
    private  var stock=0

    private lateinit var myViewModel: AppViewModel
    private val calendar: Calendar = Calendar.getInstance()

    @SuppressLint("ResourceAsColor", "SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        val applicationContext = requireContext().applicationContext

        
         databaseTow = DatabaseTow.getInstanceAllTow(applicationContext)
        // set name .......
        val sharedPreferences = applicationContext.getSharedPreferences("Name", Context.MODE_PRIVATE)
         stock = sharedPreferences.getInt("oldPosition", 0)//come from (adapter_name)

        databaseTow.getAllDaoTow().getDataId(stock).observe(viewLifecycleOwner) { it ->

            if (stock==0){
                binding.symble.text = "$"
            }else{
                it.forEach {
                    binding.symble.text = it.currencySymbol
                    storeAccountId=it.id
                }
            }

        }


        val daoM = DatabaseAll.getInstanceAll(applicationContext).getAllDaoM()
        val repository = Repository(daoM)
        myViewModel =
            ViewModelProvider(this, ViewModelFactory(repository))[AppViewModel::class.java]


        // income expense button click
        binding.incomeBtn.setOnClickListener {
            incomeSetButton(applicationContext)
        }
        binding.expensivBtn.setOnClickListener {
            expenseSetButton(applicationContext)
        }
        binding.incomeBtn.background =
            AppCompatResources.getDrawable(applicationContext, R.drawable.diffult_value)
        binding.expensivBtn.background =
            AppCompatResources.getDrawable(applicationContext, R.drawable.expanse_value)
        dataType = binding.expensivBtn.text.toString()


        // date pick .....
        binding.date.setOnClickListener {

            val datePickerDialog = context?.let { it1 -> DatePickerDialog(it1) }
            datePickerDialog!!.setOnDateSetListener { datePicker, _, _, _ ->

                calendar.set(Calendar.DAY_OF_MONTH, datePicker.dayOfMonth)
                calendar.set(Calendar.MONTH, datePicker.month)
                calendar.set(Calendar.YEAR, datePicker.year)

                val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
                getDateMonth = dateFormat.format(calendar.time)

                val yearFormat = SimpleDateFormat("yyyy", Locale.getDefault())
                yearC = yearFormat.format(calendar.time)

                getDate = HelperClass.dateFormat(calendar.time)
                binding.date.setText(getDate)

                // Get the week number
                weekNumber = calendar.get(Calendar.WEEK_OF_YEAR)


            }
            datePickerDialog.show()
        }
        val currentDate = Calendar.getInstance().time
        val yearFormat = SimpleDateFormat("yyyy", Locale.getDefault())
        yearC = yearFormat.format(currentDate.time)
        val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        getDateMonth = dateFormat.format(currentDate.time)
        weekNumber = calendar.get(Calendar.WEEK_OF_YEAR)

        getDate = if (string == "t") {
            HelperClass.dateFormat(currentDate)
        }else{
            string
        }
        binding.date.setText(getDate)


        // Category Click listener ...........
        binding.category.setOnClickListener {
            val random = RandomRecyclerviewBinding.inflate(inflater) // any single layout call
            val adapter = context?.let { it1 -> AdapterCategory(it1, HelperClass.categoryItem(), this) }
            random.randomRecyclerview.adapter = adapter
            random.randomRecyclerview.setHasFixedSize(true)
            random.randomRecyclerview.layoutManager = GridLayoutManager(context, 3)


            alertDialog = AlertDialog.Builder(context).setView(random.root).create()
            alertDialog.show()

        }


        // Account Click Listener .........
        binding.account.setOnClickListener {

            val random = RandomRecyclerviewBinding.inflate(inflater)

            val adapterAccount =
                context?.let { it1 -> AdapterAccount(it1, HelperClass.accountItem(), this) }
            random.randomRecyclerview.adapter = adapterAccount
            random.randomRecyclerview.layoutManager = LinearLayoutManager(context)
            random.randomRecyclerview.setHasFixedSize(true)

            alertDialogAccount = AlertDialog.Builder(context).setView(random.root).create()
            alertDialogAccount.window?.setGravity(Gravity.CENTER)
            alertDialogAccount.show()


        }
        

        clickButtonSev(applicationContext)
        if (intId != -1) {
            updateAllWork(applicationContext)
        }

        // Automatic not come keyboard ......
        binding.amount.post {
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.amount.windowToken, 0)
        }


        return binding.root
    }
    
    private fun updateAllWork(applicationContext: Context) {
        databaseTow.getAllDaoTow().getDataId(stock).observe(this) { it ->
            it.forEach { it ->
                myViewModel.getIdData(intId,it.id).observe(viewLifecycleOwner) { it ->
                    it.forEach {
                        binding.date.setText(it.date)
                        binding.amount.setText(it.amount.toString())
                        binding.category.setText(it.category)
                        binding.account.setText(it.account)
                        if (it.note != "Not any note") {
                            binding.note.setText(it.note)
                        }
                        if (it.type == HelperClass.INCOME) {
                            incomeSetButton(applicationContext)
                        } else {
                            expenseSetButton(applicationContext)
                        }
                        getMonth = it.dateMonth

                        // come update data set
                        dataType = it.type
                        categoryName = it.category
                        accountName = it.account
                        getDate = it.date
                        getAmount = it.amount
                        getDateMonth = it.dateMonth
                        yearC = it.year
                    }
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun clickButtonSev(applicationContext: Context) {
        binding.button.setOnClickListener {
            // set update .........
            if (intId != -1) {
                val getEditText = binding.amount.text.toString()
                getAmount = getEditText.toDoubleOrNull()
                var getNote = binding.note.text.toString()
                if (getNote.isEmpty()) {
                    getNote = "Not any note"
                }
                GlobalScope.launch {
                    myViewModel.updateAllData(
                        dataType!!,
                        categoryName!!,
                        accountName!!,
                        getDate!!,
                        getAmount!!,
                        getDateMonth!!,
                        intId,
                        getNote,
                        yearC!!
                    )
                }
                dismiss()
            }
            // Add item
            else {

                val getEditText = binding.amount.text.toString()
                getAmount = getEditText.toDoubleOrNull()
                var getNote = binding.note.text.toString()
                if (getNote.isEmpty()) {
                    getNote = "Empty note"
                }

                if (categoryName == null || accountName == null || getAmount == null) {
                    Toast.makeText(applicationContext, "Please add Value", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    GlobalScope.launch(Dispatchers.IO){
                        myViewModel.insertM(
                            ModelM(
                                dataType!!,
                                categoryName!!,
                                accountName!!,
                                getDate!!,
                                getAmount!!,
                                getDateMonth!!,
                                getNote,
                                yearC!!,
                                weekNumber!!,
                                storeAccountId!!
                                )
                        )
                    }
                    dismiss()
                }
            }
        }
    }


    // category data come
    override fun click(category: CategoryModel) {
        binding.category.setText(category.name)
        categoryName = category.name
        alertDialog.dismiss()

    }

    // account data come
    override fun getItemData(accountModel: AccountModel) {
        binding.account.setText(accountModel.account)
        accountName = accountModel.account
        alertDialogAccount.dismiss()
    }

    private fun incomeSetButton(applicationContext: Context) {
        binding.incomeBtn.background =
            AppCompatResources.getDrawable(applicationContext, R.drawable.income_value)
        binding.expensivBtn.background =
            AppCompatResources.getDrawable(applicationContext, R.drawable.diffult_value)
        dataType = binding.incomeBtn.text.toString()
    }

    private fun expenseSetButton(applicationContext: Context) {
        binding.incomeBtn.background =
            AppCompatResources.getDrawable(applicationContext, R.drawable.diffult_value)
        binding.expensivBtn.background =
            AppCompatResources.getDrawable(applicationContext, R.drawable.expanse_value)
        dataType = binding.expensivBtn.text.toString()
    }

}