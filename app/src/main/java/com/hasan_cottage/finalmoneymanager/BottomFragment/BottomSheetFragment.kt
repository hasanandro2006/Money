package com.hasan_cottage.finalmoneymanager.BottomFragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hasan_cottage.finalmoneymanager.Adapter.Adapter_acount
import com.hasan_cottage.finalmoneymanager.Adapter.Adapter_catogory
import com.hasan_cottage.finalmoneymanager.Helper.HelperClass
import com.hasan_cottage.finalmoneymanager.Model.Account_model
import com.hasan_cottage.finalmoneymanager.Model.Catagory_model
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.Roomdatabase.DatabaseAll
import com.hasan_cottage.finalmoneymanager.Roomdatabase.ModelM
import com.hasan_cottage.finalmoneymanager.Roomdatabase.Repostry
import com.hasan_cottage.finalmoneymanager.databinding.FragmentBottomSheetBinding
import com.hasan_cottage.finalmoneymanager.databinding.RandomRecyclerviewBinding
import com.hasan_cottage.finalmoneymanager.viewmodelclass.Appviewmodel.Appviewmodel
import com.hasan_cottage.finalmoneymanager.viewmodelclass.ViewmodelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BottomSheetFragment(val intId: Int) : BottomSheetDialogFragment(),
    Adapter_catogory.CatagoryClick, Adapter_acount.ClickItem {


    lateinit var binding: FragmentBottomSheetBinding
    lateinit var alertDialog: AlertDialog
    lateinit var alertDialogAccount: AlertDialog

    //     var catagorImage:Int?=null
    var catagorName: String? = null
    var accountName: String? = null
    var getDate: String? = null
    var getDateMonth: String? = null
    var getAmount: Double? = null
    var dataType: String? = null
    var getmonth: String? = null
    var yearC: String? = null
    var weekNumber: Int? = null



    lateinit var viewmodelM: Appviewmodel
    val calendar = Calendar.getInstance()

    @SuppressLint("ResourceAsColor", "SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomSheetBinding.inflate(inflater)

        val applicationContext = requireContext().applicationContext

        val daoM = DatabaseAll.getInstanceAll(applicationContext).getAllDaoM()
        val repostryM = Repostry(daoM)
        viewmodelM =
            ViewModelProvider(this, ViewmodelFactory(repostryM)).get(Appviewmodel::class.java)


        // income expense button click
        binding.incomeBtn.setOnClickListener {
            incomeSetButton()
        }
        binding.expensivBtn.setOnClickListener {
            expenseSetButton()
        }
        binding.incomeBtn.background = context?.getDrawable(R.drawable.diffult_value)
        binding.expensivBtn.background = context?.getDrawable(R.drawable.expanse_value)
        dataType = binding.expensivBtn.text.toString()



        // date pick .....
        binding.date.setOnClickListener {

            var datePickerDialog = context?.let { it1 -> DatePickerDialog(it1) }
            datePickerDialog!!.setOnDateSetListener { datepick, i, i1, i2 ->

                calendar.set(Calendar.DAY_OF_MONTH, datepick.dayOfMonth)
                calendar.set(Calendar.MONTH, datepick.month)
                calendar.set(Calendar.YEAR, datepick.year)

                val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
                getDateMonth = dateFormat.format(calendar.time)

                val yearFormat = SimpleDateFormat("yyyy", Locale.getDefault())
                yearC = yearFormat.format(calendar.time)

                getDate = HelperClass.dateFormet(calendar.time)
                binding.date.setText(getDate)

                // Get the week number
                weekNumber = calendar.get(Calendar.WEEK_OF_YEAR)




            }
            datePickerDialog!!.show()
        }
        val currentDate = Calendar.getInstance().time
        val yearFormat = SimpleDateFormat("yyyy", Locale.getDefault())
        yearC = yearFormat.format(currentDate.time)
        val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        getDateMonth = dateFormat.format(currentDate.time)
        weekNumber = calendar.get(Calendar.WEEK_OF_YEAR)
        getDate = HelperClass.dateFormet(currentDate)
        binding.date.setText(getDate)



        // Category Click Lisner ...........
        binding.category.setOnClickListener {
            var random = RandomRecyclerviewBinding.inflate(inflater) // any single layout call
            val adpterr =
                context?.let { it1 -> Adapter_catogory(it1, HelperClass.catagoryItem(), this) }
            random.randomRecyclerview.adapter = adpterr
            random.randomRecyclerview.setHasFixedSize(true)
            random.randomRecyclerview.layoutManager = GridLayoutManager(context, 3)


            alertDialog = AlertDialog.Builder(context).setView(random.root).create()
            alertDialog.show()

        }


        // Account Click Lisener .........
        binding.account.setOnClickListener {

            val random = RandomRecyclerviewBinding.inflate(inflater)

            val adapterAcount =
                context?.let { it1 -> Adapter_acount(it1, HelperClass.accountItem(), this) }
            random.randomRecyclerview.adapter = adapterAcount
            random.randomRecyclerview.layoutManager = LinearLayoutManager(context)
            random.randomRecyclerview.setHasFixedSize(true)

            alertDialogAccount = AlertDialog.Builder(context).setView(random.root).create()
            alertDialogAccount.window?.setGravity(Gravity.CENTER)
            alertDialogAccount.show()


        }




        clickButtonSev(applicationContext)
        if (intId != -1) {
            updateAllWork()
        }
        return binding.root
    }


    fun updateAllWork() {

        viewmodelM.getIdData(intId!!).observe(viewLifecycleOwner, Observer {
            it.forEach {
                binding.date.setText(it.date)
                binding.amount.setText(it.amount.toString())
                binding.category.setText(it.catagory)
                binding.account.setText(it.account)
                if (!it.note.equals("Not any note")) {
                    binding.note.setText(it.note)
                }
                if (it.type.equals(HelperClass.INCOME)) {
                    incomeSetButton()
                } else {
                    expenseSetButton()
                }
                getmonth = it.dateMonth

                // come updata data set
                dataType = it.type
                catagorName = it.catagory
                accountName = it.account
                getDate = it.date
                getAmount = it.amount
                getDateMonth = it.dateMonth
                yearC = it.year
            }
        })

    }

    fun clickButtonSev(applictioncontext: Context) {
        binding.button.setOnClickListener {
            // set update .........
            if (intId != -1) {
                val getEditext = binding.amount.text.toString()
                getAmount = getEditext.toDoubleOrNull()
                var getNote = binding.note.text.toString()
                if (getNote.isEmpty()) {
                    getNote = "Not any note"
                }
                GlobalScope.launch {
                    viewmodelM.updataAllData(
                        dataType!!,
                        catagorName!!,
                        accountName!!,
                        getDate!!,
                        getAmount!!,
                        getDateMonth!!,
                        intId,
                        getNote,
                        yearC!!
                    )
                }
            }
            // Add item
            else {
                val getEditext = binding.amount.text.toString()
                getAmount = getEditext.toDoubleOrNull()
                var getNote = binding.note.text.toString()
                if (getNote.isEmpty()) {
                    getNote = "Not any note"
                }

                if (catagorName == null || accountName == null || getAmount == null) {
                    Toast.makeText(applictioncontext, "Please add Value", Toast.LENGTH_SHORT).show()
                } else {
                    GlobalScope.launch {
                        viewmodelM.insertM(
                            ModelM(
                                dataType!!,
                                catagorName!!,
                                accountName!!,
                                getDate!!,
                                getAmount!!,
                                getDateMonth!!,
                                getNote!!,
                                yearC!!,
                                weekNumber!!,

                            )
                        )
                    }
                    dismiss()
                }


            }
        }
    }


    // catagory data come
    override fun clck(category: Catagory_model) {
        binding.category.setText(category.name)
        catagorName = category.name
        alertDialog.dismiss()

    }

    // account data come
    override fun getItemData(accountModel: Account_model) {
        binding.account.setText(accountModel.account)
        accountName = accountModel.account
        alertDialogAccount.dismiss()
    }

    fun incomeSetButton() {
        binding.incomeBtn.background = context?.getDrawable(R.drawable.income_value)
        binding.expensivBtn.background = context?.getDrawable(R.drawable.diffult_value)
        dataType = binding.incomeBtn.text.toString()
    }

    fun expenseSetButton() {
        binding.incomeBtn.background = context?.getDrawable(R.drawable.diffult_value)
        binding.expensivBtn.background = context?.getDrawable(R.drawable.expanse_value)
        dataType = binding.expensivBtn.text.toString()
    }

}