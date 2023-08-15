package com.hasan_cottage.finalmoneymanager.BottomFragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hasan_cottage.finalmoneymanager.activity.ExpenseIncomeChart
import com.hasan_cottage.finalmoneymanager.Fragment.CalendarFragment
import com.hasan_cottage.finalmoneymanager.Fragment.StatasFragment
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.databinding.FragmentBottomShetCoseNameBinding


class BottomShetFragmentCoseName(var come:Int): BottomSheetDialogFragment() {
  lateinit var binding:FragmentBottomShetCoseNameBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=FragmentBottomShetCoseNameBinding.inflate(inflater)

        val contextA=requireContext() as AppCompatActivity

       val sharedPreferences=contextA.getSharedPreferences("Time",Context.MODE_PRIVATE)
        val editor=sharedPreferences.edit()

        val autocome=sharedPreferences.getInt("Daily",1)

        binding.dailyT.setOnClickListener {

            editor.putInt("Daily",1)
            editor.apply()
            refreshFragment(contextA)
           dismiss()

        }
        binding.weeklyT.setOnClickListener {
            editor.putInt("Daily",2)
            editor.apply()
            refreshFragment(contextA)
           dismiss()

        }
        binding.monthlyT.setOnClickListener {
            editor.putInt("Daily",3)
            editor.apply()
            refreshFragment(contextA)
           dismiss()
        }
        binding.yearlyT.setOnClickListener {
            editor.putInt("Daily",4)
            editor.apply()
            refreshFragment(contextA)
            dismiss()
        }
        binding.allT.setOnClickListener {
            editor.putInt("Daily",5)
            editor.apply()
            refreshFragment(contextA)
           dismiss()
        }
        if (autocome == 1){
            binding.redio1.isChecked=autocome==1
        }else if (autocome == 2){
            binding.redio2.isChecked=autocome==2
        }else if (autocome==3){
            binding.redio3.isChecked=autocome==3
        }else if (autocome == 4){
            binding.redio4.isChecked=autocome==4
        }else if (autocome==5){
            binding.redio5.isChecked=autocome==5
        }


        return binding.root
    }

    private fun refreshFragment(contextA: AppCompatActivity) {
        if (come == 1) {
            contextA.supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, CalendarFragment())
                .commit()
        }else if(come == 2){
            contextA.supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout,StatasFragment())
                .commit()
        }else{
            val intent=Intent(contextA,ExpenseIncomeChart::class.java)
            intent.putExtra("work",1)
            startActivity(intent)
        }
    }

}