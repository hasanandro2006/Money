package com.hasan_cottage.finalmoneymanager.BottomFragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class BottomSheetFragment : BottomSheetDialogFragment(), Adapter_catogory.CatagoryClick,Adapter_acount.ClickItem{


    lateinit var binding:FragmentBottomSheetBinding
    lateinit var alertDialog:AlertDialog
    lateinit var alertDialogAccount:AlertDialog
//     var catagorImage:Int?=null
     var catagorName:String?=null
     var accountName:String?=null
     var getDate:String?=null
     var getDateMonth:String?=null
    var getAmount:Double?=null
    var dataType:String?=null



    lateinit var viewmodelM:Appviewmodel

    @SuppressLint("ResourceAsColor", "SuspiciousIndentation")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=FragmentBottomSheetBinding.inflate(inflater)

        // For ViewModelM
        val applicationContext = requireContext().applicationContext
        val dao = DatabaseAll.getInstanceAll(applicationContext).getAllDao()
        val daoM = DatabaseAll.getInstanceAll(applicationContext).getAllDaoM()
        val repostryM = Repostry(dao, daoM)
        viewmodelM = ViewModelProvider(this, ViewmodelFactory(repostryM)).get(Appviewmodel::class.java)




        binding.incomeBtn.setOnClickListener {
            binding.incomeBtn.background = context?.getDrawable(R.drawable.income_value)
            binding.expensivBtn.background = context?.getDrawable(R.drawable.diffult_value)
            dataType=binding.incomeBtn.text.toString()
        }
        binding.expensivBtn.setOnClickListener {
            binding.incomeBtn.background = context?.getDrawable(R.drawable.diffult_value)
            binding.expensivBtn.background = context?.getDrawable(R.drawable.expanse_value)
            dataType=binding.expensivBtn.text.toString()

        }

        // date pick .....
        binding.date.setOnClickListener {

            var datePickerDialog= context?.let { it1 -> DatePickerDialog(it1) }
            datePickerDialog!!.setOnDateSetListener { datepick,i,i1,i2 ->

                val calendar=Calendar.getInstance()
                calendar.set(Calendar.DAY_OF_MONTH,datepick.dayOfMonth)
                calendar.set(Calendar.MONTH,datepick.month)
                calendar.set(Calendar.YEAR,datepick.year)

                val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
                getDateMonth=dateFormat.format(calendar.time)



                getDate=HelperClass.dateFormet(calendar.time)

                binding.date.setText(getDate)
            }
            datePickerDialog!!.show()
        }

        // Category Click Lisner ...........
        binding.category.setOnClickListener {
            var random=RandomRecyclerviewBinding.inflate(inflater) // any single layout call
            val adpterr= context?.let { it1 -> Adapter_catogory(it1,HelperClass.catagoryItem(),this)}
            random.randomRecyclerview.adapter=adpterr
            random.randomRecyclerview.setHasFixedSize(true)
            random.randomRecyclerview.layoutManager=GridLayoutManager(context,3)


            alertDialog = AlertDialog.Builder(context)
                .setView(random.root)
                .create()
            alertDialog.show()

        }

        // Account Click Lisener .........
        binding.account.setOnClickListener {

            val random=RandomRecyclerviewBinding.inflate(inflater)

            val adapterAcount= context?.let { it1 -> Adapter_acount(it1,HelperClass.accountItem(),this) }
            random.randomRecyclerview.adapter=adapterAcount
            random.randomRecyclerview.layoutManager=LinearLayoutManager(context)
            random.randomRecyclerview.setHasFixedSize(true)

            alertDialogAccount= AlertDialog.Builder(context)
                .setView(random.root)
                .create()
            alertDialogAccount.window?.setGravity(Gravity.CENTER)
            alertDialogAccount.show()


        }


        // Save Data in room database ...
        binding.button.setOnClickListener {

         val getEditext= binding.amount.text.toString()
            getAmount=getEditext.toDoubleOrNull()

            GlobalScope.launch {
                viewmodelM.insertM(ModelM(dataType!!,catagorName!!,accountName!!,getDate!!,getAmount!!,getDateMonth!!
                ))
            }
            dismiss()
        }


        return binding.root
    }

    // catagory data come
    override fun clck(category: Catagory_model) {
        binding.category.setText(category.name)
        catagorName=category.name
//        catagorImage=category.image
        alertDialog.dismiss()

    }

    // account data come
    override fun getItemData(accountModel: Account_model) {
        binding.account.setText(accountModel.account)
        accountName=accountModel.account
        alertDialogAccount.dismiss()
    }


}