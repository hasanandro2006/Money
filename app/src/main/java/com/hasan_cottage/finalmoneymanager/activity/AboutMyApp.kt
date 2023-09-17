package com.hasan_cottage.finalmoneymanager.activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.hasan_cottage.finalmoneymanager.databinding.ActivityAboutMyAppBinding

class AboutMyApp : AppCompatActivity() {

    val binding by lazy {
        ActivityAboutMyAppBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.back2.setOnClickListener {
            onBackPressed()
        }
        // item_tow
        binding.provacy.setOnClickListener {
            val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val nf = cm.activeNetworkInfo
            if (nf != null && nf.isConnected) {
                startActivity(Intent(this, PrivacyPolicyActivity::class.java))
            } else {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show()
            }
        }

    }
}