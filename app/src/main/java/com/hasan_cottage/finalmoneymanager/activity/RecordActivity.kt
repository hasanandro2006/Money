package com.hasan_cottage.finalmoneymanager.activity

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.hasan_cottage.finalmoneymanager.bottomFragment.BottomSheetFragment
import com.hasan_cottage.finalmoneymanager.helper.HelperClass
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.roomDatabase.DatabaseAll
import com.hasan_cottage.finalmoneymanager.roomDatabase.Repository
import com.hasan_cottage.finalmoneymanager.databinding.ActivityRecordBinding
import com.hasan_cottage.finalmoneymanager.roomDatabaseNot.DatabaseTow
import com.hasan_cottage.finalmoneymanager.viewModelClass.AppViewModel
import com.hasan_cottage.finalmoneymanager.viewModelClass.ViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecordActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityRecordBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val daoM = DatabaseAll.getInstanceAll(this).getAllDaoM()
        val repository = Repository(daoM)
        val myViewModel =
            ViewModelProvider(this, ViewModelFactory(repository))[AppViewModel::class.java]

        val id = intent.getIntExtra("Id", 0)

       val databaseTow = DatabaseTow.getInstanceAllTow(this)
        val sharedPreferencesA =this.getSharedPreferences("Name", Context.MODE_PRIVATE)
       val stock = sharedPreferencesA.getInt("oldPosition", 0)//come from (adapter_name)

        databaseTow.getAllDaoTow().getDataId(stock).observe(this) { it ->
            it.forEach { pape ->

                myViewModel.getIdData(id,pape.id).observe(this) { it ->
                    it.forEach {
                        binding.topCatagory.text = it.category
                        binding.CategoryR.text = it.category
                        binding.DateR.text = it.date
                        binding.AccountR.text = it.account
                        binding.NoteR.text = it.note
                        if (it.type == HelperClass.EXPENSE) {
                            val stores = "-${pape.currencySymbol} ${it.amount}"
                            binding.AmountR.text = stores
                            binding.AmountR.setTextColor(ContextCompat.getColor(this, R.color.red))
                            binding.TypeR.setTextColor(ContextCompat.getColor(this, R.color.red))
                            binding.active.setBackgroundResource(R.drawable.degine_for_nagative)

                        } else {
                            binding.AmountR.text =" ${pape.currencySymbol} ${it.amount}"
                            binding.AmountR.setTextColor(ContextCompat.getColor(this, R.color.blue))
                            binding.TypeR.setTextColor(ContextCompat.getColor(this, R.color.blue))
                            binding.active.setBackgroundResource(R.drawable.degine_for_active)
                        }
                        binding.TypeR.text = it.type
                        val image = HelperClass.getColorCategory(it.category)
                        binding.imageCatagory.setImageResource(image!!.image)
                        binding.imageCatagory.backgroundTintList = getColorStateList(image.color)
                    }
                }
            }
        }



        binding.delete.setOnClickListener {
            val alertDialog =  AlertDialog.Builder(this)
                .setTitle("Delete Transaction")
                .setMessage("Are you sure delete this transaction ?")
                .setPositiveButton("OK") { _, _ ->
                    GlobalScope.launch {
                        myViewModel.deleteDataId(id)
                        finish()
                        Toast.makeText(this@RecordActivity, "Deleted", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("NO") { _, _ ->
                    Toast.makeText(this, "Not Deleted", Toast.LENGTH_SHORT).show()
                }
                .create()

            val messageTextView = alertDialog.findViewById(android.R.id.message) as? TextView
            messageTextView?.setTextColor(ContextCompat.getColor(this, R.color.black))

                alertDialog.show()
        }
        binding.edit.setOnClickListener {
            val bottomSheetFragment = BottomSheetFragment(id,"t")
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
        binding.back.setOnClickListener {
            onBackPressed()
        }


    }
}