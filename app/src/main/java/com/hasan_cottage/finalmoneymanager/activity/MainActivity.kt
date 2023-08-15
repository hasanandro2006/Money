package com.hasan_cottage.finalmoneymanager.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.hasan_cottage.finalmoneymanager.BottomFragment.BottomSheetFragment
import com.hasan_cottage.finalmoneymanager.Fragment.CalendarFragment
import com.hasan_cottage.finalmoneymanager.Fragment.MainFragment
import com.hasan_cottage.finalmoneymanager.Fragment.MoreFragment
import com.hasan_cottage.finalmoneymanager.Fragment.StatasFragment
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.floatingButton.setOnClickListener {
            BottomSheetFragment(-1).show(supportFragmentManager, null)
        }

        supportFragmentManager.beginTransaction().add(R.id.frameLayout, MainFragment()).commit()

        binding.bottomNevagation.setOnItemSelectedListener{ item ->
            val fragmentManger = supportFragmentManager
            val beginTransactionVal = fragmentManger.beginTransaction()

            when (item.itemId) {
                R.id.one -> {
                    beginTransactionVal.replace(R.id.frameLayout, MainFragment())
                    fragmentManger.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    beginTransactionVal.addToBackStack(null)
                    beginTransactionVal.commit()
                    true
                }

                R.id.tow -> {
                    beginTransactionVal.replace(R.id.frameLayout, CalendarFragment())
                    fragmentManger.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    beginTransactionVal.addToBackStack(null)
                    beginTransactionVal.commit()
                    true
                }

                R.id.three -> {
                    beginTransactionVal.replace(R.id.frameLayout, StatasFragment())
                    fragmentManger.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    beginTransactionVal.addToBackStack(null)
                    beginTransactionVal.commit()
                    true
                }

                R.id.four -> {
                    beginTransactionVal.replace(R.id.frameLayout, MoreFragment())
                    fragmentManger.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    beginTransactionVal.addToBackStack(null)
                    beginTransactionVal.commit()
                    true
                }

                else -> false
            }
        }

    }
}