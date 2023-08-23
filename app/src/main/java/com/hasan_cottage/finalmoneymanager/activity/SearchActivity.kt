package com.hasan_cottage.finalmoneymanager.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hasan_cottage.finalmoneymanager.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Back Event
        binding.back.setOnClickListener { onBackPressed() }



    }
}