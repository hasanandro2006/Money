package com.hasan_cottage.finalmoneymanager.bottomFragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.activity.ExpIncRecyclerItemClick
import com.hasan_cottage.finalmoneymanager.activity.ExpenseIncomeChart
import com.hasan_cottage.finalmoneymanager.databinding.FragmentBottomShetCoseNameBinding
import com.hasan_cottage.finalmoneymanager.fragment.CalendarFragment
import com.hasan_cottage.finalmoneymanager.fragment.StatsFragment


class BottomSheetFragmentName(private var come: Int) : BottomSheetDialogFragment() {
    val binding by lazy {
        FragmentBottomShetCoseNameBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {


        val contextA = requireContext() as AppCompatActivity

        val sharedPreferences = contextA.getSharedPreferences("Time", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val autoCome = sharedPreferences.getInt("Daily", 1)

        binding.dailyT.setOnClickListener {

            editor.putInt("Daily", 1)
            editor.apply()
            refreshFragment(contextA)
            dismiss()

        }
        binding.weeklyT.setOnClickListener {
            editor.putInt("Daily", 2)
            editor.apply()
            refreshFragment(contextA)
            dismiss()

        }
        binding.monthlyT.setOnClickListener {
            editor.putInt("Daily", 3)
            editor.apply()
            refreshFragment(contextA)
            dismiss()
        }
        binding.yearlyT.setOnClickListener {
            editor.putInt("Daily", 4)
            editor.apply()
            refreshFragment(contextA)
            dismiss()
        }
        binding.allT.setOnClickListener {
            editor.putInt("Daily", 5)
            editor.apply()
            refreshFragment(contextA)
            dismiss()
        }
        binding.redio1.isChecked = autoCome == 1
        binding.redio2.isChecked = autoCome == 2
        binding.redio3.isChecked = autoCome == 3
        binding.redio4.isChecked = autoCome == 4
        binding.redio5.isChecked = autoCome == 5


        return binding.root
    }

    private fun refreshFragment(contextA: AppCompatActivity) {
        when (come) {
            1 -> {
                contextA.supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, CalendarFragment()).commit()
            }

            2 -> {
                contextA.supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, StatsFragment()).commit()
            }
            3 ->{
                val intent = Intent(contextA, ExpenseIncomeChart::class.java)
                intent.putExtra("work", 1)
                startActivity(intent)
            }

            else -> {
                val intent = Intent(contextA, ExpIncRecyclerItemClick::class.java)
                startActivity(intent)
            }
        }
    }

}