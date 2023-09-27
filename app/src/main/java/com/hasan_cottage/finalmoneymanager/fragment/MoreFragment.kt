package com.hasan_cottage.finalmoneymanager.fragment

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.activity.AboutMyApp
import com.hasan_cottage.finalmoneymanager.activity.PrivacyPolicyActivity
import com.hasan_cottage.finalmoneymanager.activity.SearchActivity
import com.hasan_cottage.finalmoneymanager.activity.TakeCalender
import com.hasan_cottage.finalmoneymanager.databinding.FragmentMoreBinding
import com.hasan_cottage.finalmoneymanager.roomDatabase.DatabaseAll
import com.hasan_cottage.finalmoneymanager.roomDatabase.Repository
import com.hasan_cottage.finalmoneymanager.viewModelClass.AppViewModel
import com.hasan_cottage.finalmoneymanager.viewModelClass.ViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MoreFragment : Fragment() {
    private lateinit var binding: FragmentMoreBinding
    private lateinit var reviewManager: ReviewManager
    private var reviewInfo: ReviewInfo? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoreBinding.inflate(layoutInflater)

        val daoM = DatabaseAll.getInstanceAll(requireContext()).getAllDaoM()
        val repository = Repository(daoM)
        val myViewModel =
            ViewModelProvider(this, ViewModelFactory(repository))[AppViewModel::class.java]

        // Click search button
        binding.searce.setOnClickListener {
            startActivity(Intent(requireContext(), SearchActivity::class.java))
        }
        binding.dittles.setOnClickListener {
            startActivity(Intent(context, TakeCalender::class.java))
        }

        // item_one
        binding.itemOne.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, MainFragment()).addToBackStack(null).commit()
        }

        // item_tow
        binding.itemTow.setOnClickListener {
            val cm = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val nf = cm.activeNetworkInfo
            if (nf != null && nf.isConnected) {
                startActivity(Intent(requireContext(), PrivacyPolicyActivity::class.java))
            } else {
                Toast.makeText(requireContext(), "No Internet Connection", Toast.LENGTH_SHORT).show()
            }
        }

        // item_four
        binding.itemFour.setOnClickListener {
            val packageName = requireActivity().packageName
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Download App: https://play.google.com/store/apps/details?id=$packageName"
            )
            requireActivity().startActivity(Intent.createChooser(intent, "Share via"))
        }

        // item_five
        binding.itemFive.setOnClickListener {
            val developerName = "HasanCottage"
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://search?q=pub:$developerName")
                    )
                )
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$developerName")
                    )
                )
            }
        }

        // item eight
        binding.itemEight.setOnClickListener {
            val alertDialog =AlertDialog.Builder(requireContext())
                .setTitle("Delete All Transaction")
                .setMessage("Are you sure delete all transaction ?")
                .setPositiveButton("OK") { _, _ ->
                    GlobalScope.launch {
                        myViewModel.deleteAllItems()
                        requireActivity().runOnUiThread {
                            Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                .setNegativeButton("NO") { _, _ ->
                    Toast.makeText(requireContext(), "Not Deleted", Toast.LENGTH_SHORT).show()
                }
                .create()
            val messageTextView = alertDialog.findViewById(android.R.id.message) as? TextView
            messageTextView?.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            alertDialog.show()
        }

        // item nine
        binding.itemNine.setOnClickListener {
            startActivity(Intent(requireContext(),AboutMyApp::class.java))
        }

        // item_three
        binding.itemThree.setOnClickListener {
            reviewApp()
        }

        // Initialize the ReviewManager
        reviewManager = ReviewManagerFactory.create(requireContext())

        // Request review flow and handle the result
        val reviewInfoTask = reviewManager.requestReviewFlow()
        reviewInfoTask.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                reviewInfo = task.result
            } else {
                Toast.makeText(requireContext(), "Failed to request review flow", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun reviewApp() {
        reviewInfo?.let { info ->
            val flow = reviewManager.launchReviewFlow(requireActivity(), info)
            flow.addOnCompleteListener { _ ->
                Toast.makeText(requireContext(), "Review completed", Toast.LENGTH_SHORT).show()
            }
        } ?: run {
            Toast.makeText(requireContext(), "Review info not available yet", Toast.LENGTH_SHORT).show()
        }
    }
}
