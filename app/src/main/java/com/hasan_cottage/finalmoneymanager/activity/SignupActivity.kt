package com.hasan_cottage.finalmoneymanager.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.roomDatabaseNot.DataSignup
import com.hasan_cottage.finalmoneymanager.roomDatabaseNot.DatabaseTow
import com.hasan_cottage.finalmoneymanager.databinding.ActivitySignupBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class SignupActivity : AppCompatActivity() {

    private lateinit var dataBinding: ActivitySignupBinding

    private var cName :String? = null
     private var cCode :String? = null
    private var cSymbols :String? = null
    var name :String? = null
    private var databaseTow:DatabaseTow?=null


    private var radioPosition: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup)

       databaseTow= DatabaseTow.getInstanceAllTow(this)

        automaticFocusEdittext()
        getDataFromRoom()
        passNameSelectedPosition()
        nextButtonClick()

        dataBinding.skip.setOnClickListener {
            val sharedPreferencesC = getSharedPreferences("Currency", Context.MODE_PRIVATE)
            val editor = sharedPreferencesC.edit()
            editor.putString("cName","")
            editor.putString("cCode","")
            editor.putString("cSymbol", "")
            editor.putString("name", "Transaction")
            editor.apply()

            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }

    }

    private fun automaticFocusEdittext() {
        dataBinding.appCompatEditText.requestFocus()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

    private fun passNameSelectedPosition() {
        dataBinding.spinnerFb.setOnClickListener {

            val userInput = dataBinding.appCompatEditText.text.toString()

            val intent = Intent(this, SelectCurrency::class.java)
            intent.putExtra("CURRENCY_DATA_KEY", userInput)
            intent.putExtra("CURRENCY_DATA_KEY_two", radioPosition)
            startActivity(intent)
        }
    }

    private fun getDataFromRoom() {
        val sharedPreferencesC =getSharedPreferences("Currency", Context.MODE_PRIVATE)

        dataBinding.text2.text = sharedPreferencesC.getString("cName", "Select your country currency")
        dataBinding.text1.text  = sharedPreferencesC.getString("cCode", "")

        val currencySymbol = sharedPreferencesC.getString("cSymbol", "")
        val formattedString = getString(R.string.currency_with_symbol, currencySymbol)
        dataBinding.steText2.text = formattedString

        val name = sharedPreferencesC.getString("name", "Transaction")
        dataBinding.appCompatEditText.setText(name)
        val oldPosition = sharedPreferencesC.getInt("oldPosition", 21)
        radioPosition = oldPosition
    }
    @OptIn(DelicateCoroutinesApi::class)
    private fun nextButtonClick() {
        dataBinding.linearlayout.setOnClickListener {

            name= dataBinding.appCompatEditText.text.toString()
            cName= dataBinding.text2.text.toString()
            cCode= dataBinding.text1.text.toString()
            cSymbols= dataBinding.steText2.text.toString()

            GlobalScope.launch {
                databaseTow!!.getAllDaoTow().insert(DataSignup(cName!!,cCode!!,cSymbols!!,name!!))

            }

            startActivity(Intent(this, MainActivity::class.java))
            finish()

            val sharedPreferencesC = getSharedPreferences("Currency", Context.MODE_PRIVATE)
            val editor = sharedPreferencesC.edit()
            editor.putString("cName","")
            editor.putString("cCode","")
            editor.putString("cSymbol", "")
            editor.putString("name", "Transaction")
            editor.apply()
        }
    }

}
