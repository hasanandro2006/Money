package com.hasan_cottage.finalmoneymanager.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.databinding.ActivityExpIncRecyclerItemClickBinding

class ExpIncRecyclerItemClick : AppCompatActivity() {

     val binding by lazy {
        ActivityExpIncRecyclerItemClickBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)




    }
}