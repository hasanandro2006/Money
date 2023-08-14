package com.hasan_cottage.finalmoneymanager.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.RoomdataNot.DataSignup
import com.hasan_cottage.finalmoneymanager.RoomdataNot.DatabaseTow
import com.hasan_cottage.finalmoneymanager.databinding.ActivitySignupBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class Signup_Activity : AppCompatActivity() {
    companion object {
        lateinit var dataBinding: ActivitySignupBinding

    }

    var cName :String? = null
     var cCode :String? = null
    var cSymbols :String? = null
    var name :String? = null


    var redioposition: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup)


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
        var sharedPreferencesC =getSharedPreferences("Currency", Context.MODE_PRIVATE)

        dataBinding.text2.text = sharedPreferencesC.getString("cName", "select_your")
        dataBinding.text1.text  = sharedPreferencesC.getString("cCode", "_country")
        dataBinding.steText2.text =  "(" +sharedPreferencesC.getString("cSymble", "_currency")+ ")"
        var name = sharedPreferencesC.getString("name", "Hasan")
        dataBinding.appCompatEditText.setText(name)
        var oldposition = sharedPreferencesC.getInt("oldPosition", 21)
        redioposition = oldposition
    }
    private fun nextButtonClick() {
        dataBinding.linearlayout.setOnClickListener {

            name= dataBinding.appCompatEditText.text.toString()
            cName= dataBinding.text2.text.toString()
            cCode= dataBinding.text1.text.toString()
            cSymbols= dataBinding.steText2.text.toString()

            val databaseTow= DatabaseTow.getInstanceAllTow(this)
            GlobalScope.launch {
                databaseTow.getAllDaoTow().insert(DataSignup(cName!!,cCode!!,cSymbols!!,name!!))

            }
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
        }
    }

}
