package com.hasan_cottage.finalmoneymanager.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hasan_cottage.finalmoneymanager.Helper.HelperClass
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.Roomdatabase.DatabaseAll
import com.hasan_cottage.finalmoneymanager.Roomdatabase.Repostry
import com.hasan_cottage.finalmoneymanager.databinding.ActivityRecordBinding
import com.hasan_cottage.finalmoneymanager.viewmodelclass.Appviewmodel.Appviewmodel
import com.hasan_cottage.finalmoneymanager.viewmodelclass.ViewmodelFactory

class RecordActivity : AppCompatActivity() {
    lateinit var binding:ActivityRecordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val daoM = DatabaseAll.getInstanceAll(this).getAllDaoM()
        val repostryM = Repostry(daoM)
       val viewmodelM = ViewModelProvider(this, ViewmodelFactory(repostryM)).get(Appviewmodel::class.java)

        val Id=intent.getIntExtra("Id",0)


        
        viewmodelM.getIdData(Id).observe(this, Observer { it ->
            it.forEach {
                binding.topCatagory.text=it.catagory
                binding.CategoryR.text=it.catagory
                binding.DateR.text=it.date
                binding.AccountR.text=it.account
                if (it.type.equals(HelperClass.EXPENSE)){
                    binding.AmountR.text="-"+it.amount.toString()
                    binding.AmountR.setTextColor(ContextCompat.getColor(this, R.color.red))
                    binding.topCatagory.setTextColor(ContextCompat.getColor(this, R.color.red))
                }else{
                    binding.AmountR.text=it.amount.toString()
                    binding.AmountR.setTextColor(ContextCompat.getColor(this, R.color.blue))
                    binding.topCatagory.setTextColor(ContextCompat.getColor(this, R.color.blue))
                }
                binding.TypeR.text=it.type
                val image=HelperClass.getColorCatogory(it.catagory)
                binding.imageCatagory.setImageResource(image!!.image)
                binding.imageCatagory.backgroundTintList=getColorStateList(image!!.color)
            }
        })

    }
}