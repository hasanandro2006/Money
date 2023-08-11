package com.hasan_cottage.finalmoneymanager.Activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.hasan_cottage.finalmoneymanager.BottomFragment.BottomSheetFragment
import com.hasan_cottage.finalmoneymanager.Fragment.CalendarFragment
import com.hasan_cottage.finalmoneymanager.Fragment.MainFragment
import com.hasan_cottage.finalmoneymanager.Fragment.MoreFragment
import com.hasan_cottage.finalmoneymanager.Fragment.StatasFragment
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.RoomdataNot.DatabaseTow
import com.hasan_cottage.finalmoneymanager.Roomdatabase.DatabaseAll
import com.hasan_cottage.finalmoneymanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.floatingButton.setOnClickListener {
            BottomSheetFragment().show(supportFragmentManager, null)
        }

        supportFragmentManager.beginTransaction().add(R.id.frameLayout, MainFragment()).commit()

        binding.bottomNevagation.setOnNavigationItemSelectedListener { item ->
            val fragmentManger = supportFragmentManager
            val bingraanstion = fragmentManger.beginTransaction()

            when (item.itemId) {
                R.id.one -> {
                    bingraanstion.replace(R.id.frameLayout, MainFragment())
                    bingraanstion.addToBackStack(null)
                    bingraanstion.commit()
                    true
                }

                R.id.tow -> {
                    bingraanstion.replace(R.id.frameLayout, CalendarFragment())
                    bingraanstion.addToBackStack(null)
                    bingraanstion.commit()
                    true
                }

                R.id.three -> {
                    bingraanstion.replace(R.id.frameLayout, StatasFragment())
                    bingraanstion.addToBackStack(null)
                    bingraanstion.commit()
                    true
                }

                R.id.four -> {
                    bingraanstion.replace(R.id.frameLayout, MoreFragment())
                    bingraanstion.addToBackStack(null)
                    bingraanstion.commit()
                    true
                }

                else -> false
            }
        }

    }
}