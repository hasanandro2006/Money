package com.hasan_cottage.finalmoneymanager.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.Roomdatabase.DatabaseAll
import com.hasan_cottage.finalmoneymanager.Roomdatabase.DatabaseDao
import com.hasan_cottage.finalmoneymanager.Roomdatabase.Repostry
import com.hasan_cottage.finalmoneymanager.databinding.ActivitySignupBinding
import com.hasan_cottage.finalmoneymanager.viewmodelclass.Appviewmodel.Appviewmodel
import com.hasan_cottage.finalmoneymanager.viewmodelclass.ViewmodelFactory
import kotlinx.coroutines.launch


class Signup_Activity : AppCompatActivity() {
    companion object {
        lateinit var dataBinding: ActivitySignupBinding
        lateinit var viewmodelAll: Appviewmodel
        lateinit var dao: DatabaseDao
    }

    var redioposition: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup)

        // Initialize  database
        dao = DatabaseAll.getInstanceAll(application).getAllDao()
        val daoM = DatabaseAll.getInstanceAll(applicationContext).getAllDaoM()
        val repostry = Repostry(dao,daoM)
        viewmodelAll =
            ViewModelProvider(this, ViewmodelFactory(repostry)).get(Appviewmodel::class.java)


        automaticFocasinEditext()
        getDataFromRoom()
        pass_Name_SelectedPosition()
        nextButtonClick()


    }

    private fun automaticFocasinEditext() {
        dataBinding!!.appCompatEditText.requestFocus()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

    private fun pass_Name_SelectedPosition() {
        dataBinding.spinnerFb.setOnClickListener {

            var userInput = dataBinding.appCompatEditText.text.toString()

            if (userInput == null) {
                userInput = " "
            }
            val intent = Intent(this, Select_currency_Activity::class.java)
            intent.putExtra("CURRENCY_DATA_KEY", userInput)
            intent.putExtra("CURRENCY_DATA_KEY_two", redioposition)
            startActivity(intent)
        }
    }

    private fun getDataFromRoom() {
        lifecycleScope.launch {
            viewmodelAll.getData().observe(this@Signup_Activity, Observer { it ->

                if (it.isNullOrEmpty()) {

                } else {
                    dataBinding.text1.text = it[it.size - 1].currencyCode+" "
                    dataBinding.text2.text = it[it.size - 1].currencyName
                    dataBinding.steText2.text = "("+it[it.size - 1].currencySymbol+")"
                    dataBinding.myModel = it[it.size - 1].name
                    redioposition = it[it.size - 1].position

                    Log.d("show", it.toString())
                }
            })
        }
    }

    private fun nextButtonClick() {
        dataBinding.linearlayout.setOnClickListener {
            viewmodelAll.getData().observe(this, Observer {

                if (it.isNullOrEmpty()) {
                    Toast.makeText(this, "Full up ", Toast.LENGTH_SHORT)
                } else {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            })


        }
    }

}
