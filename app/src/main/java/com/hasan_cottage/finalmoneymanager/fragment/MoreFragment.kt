package com.hasan_cottage.finalmoneymanager.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.databinding.FragmentMainBinding
import com.hasan_cottage.finalmoneymanager.databinding.FragmentMoreBinding

class MoreFragment : Fragment() {
    lateinit var binding:FragmentMoreBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
       binding=FragmentMoreBinding.inflate(inflater,container,false)
        
        return binding.root

    }
}