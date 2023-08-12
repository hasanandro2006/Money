package com.hasan_cottage.finalmoneymanager.Activity

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.utils.ColorTemplate
import com.hasan_cottage.finalmoneymanager.BottomFragment.BottomSheetFragment
import com.hasan_cottage.finalmoneymanager.Helper.HelperClass
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.Roomdatabase.DatabaseAll
import com.hasan_cottage.finalmoneymanager.Roomdatabase.Repostry
import com.hasan_cottage.finalmoneymanager.databinding.ActivityRecordBinding
import com.hasan_cottage.finalmoneymanager.viewmodelclass.Appviewmodel.Appviewmodel
import com.hasan_cottage.finalmoneymanager.viewmodelclass.ViewmodelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
                binding.NoteR.text=it.note
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

        binding.delete.setOnClickListener {
            val alartdilog=AlertDialog.Builder(this)
                .setTitle("DELETE THIS")
                .setMessage("Are you sure delete this item")
                .setPositiveButton("OK"){ dilog,which->
                    GlobalScope.launch {
                        viewmodelM.deletDataId(Id)
                    }
                    finish()
                    Toast.makeText(this,"Deleted",Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("NO"){dilog,which->
                    Toast.makeText(this,"Not Deleted",Toast.LENGTH_SHORT).show()
                }
                .create()
                .show()
        }
        binding.edit.setOnClickListener {
            val bottomSheetFragment = BottomSheetFragment(Id)
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }


    }
}