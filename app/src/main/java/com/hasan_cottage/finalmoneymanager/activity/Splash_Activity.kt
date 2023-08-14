package com.hasan_cottage.finalmoneymanager.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.hasan_cottage.finalmoneymanager.R

class Splash_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        val sharedPreferencesC = getSharedPreferences("Currency", Context.MODE_PRIVATE)
        var cName = sharedPreferencesC.getString("cName", "Hasan")

        Handler().postDelayed(Runnable {

            if (cName.equals("Hasan")) {
                startActivity(Intent(this, Signup_Activity::class.java))
                finish()
            }else{
            startActivity(Intent(this,MainActivity::class.java))
            finish()
            }


        },2000)




    }
}