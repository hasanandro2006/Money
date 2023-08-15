package com.hasan_cottage.finalmoneymanager.activity

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
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
    lateinit var binding: ActivityRecordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val daoM = DatabaseAll.getInstanceAll(this).getAllDaoM()
        val repository = Repostry(daoM)
        val myViewModel =
            ViewModelProvider(this, ViewmodelFactory(repository))[Appviewmodel::class.java]

        val id = intent.getIntExtra("Id", 0)



        myViewModel.getIdData(id).observe(this) { it ->
            it.forEach {
                binding.topCatagory.text = it.catagory
                binding.CategoryR.text = it.catagory
                binding.DateR.text = it.date
                binding.AccountR.text = it.account
                binding.NoteR.text = it.note
                if (it.type == HelperClass.EXPENSE) {
                    val amountText = getString(R.string.amount_text, it.amount.toString())
                    binding.AmountR.text = amountText
                    binding.AmountR.setTextColor(ContextCompat.getColor(this, R.color.red))
                    binding.TypeR.setTextColor(ContextCompat.getColor(this, R.color.red))

                } else {
                    binding.AmountR.text = it.amount.toString()
                    binding.AmountR.setTextColor(ContextCompat.getColor(this, R.color.blue))
                    binding.TypeR.setTextColor(ContextCompat.getColor(this, R.color.blue))

                }
                binding.TypeR.text = it.type
                val image = HelperClass.getColorCatogory(it.catagory)
                binding.imageCatagory.setImageResource(image!!.image)
                binding.imageCatagory.backgroundTintList = getColorStateList(image.color)
            }
        }


        binding.delete.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("DELETE THIS")
                .setMessage("Are you sure delete this item")
                .setPositiveButton("OK") { _, _ ->
                    GlobalScope.launch {
                        myViewModel.deletDataId(id)
                        finish()
                        Toast.makeText(this@RecordActivity, "Deleted", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("NO") { _, _ ->
                    Toast.makeText(this, "Not Deleted", Toast.LENGTH_SHORT).show()
                }
                .create()
                .show()
        }
        binding.edit.setOnClickListener {
            val bottomSheetFragment = BottomSheetFragment(id)
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
        binding.back.setOnClickListener {
            onBackPressed()
        }


    }
}