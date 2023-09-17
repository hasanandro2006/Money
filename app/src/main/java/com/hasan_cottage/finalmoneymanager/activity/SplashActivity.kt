package com.hasan_cottage.finalmoneymanager.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.roomDatabaseNot.DatabaseTow

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        val sharedPreferencesC = getSharedPreferences("Currency", Context.MODE_PRIVATE)
        val cName = sharedPreferencesC.getString("cName", "Hasan")

            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                if (cName == "Hasan") {
                    startActivity(Intent(this, SignupActivity::class.java))
                    finish()
                } else {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            },100)

    }
}