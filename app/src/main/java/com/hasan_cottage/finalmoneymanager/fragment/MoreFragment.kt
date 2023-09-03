package com.hasan_cottage.finalmoneymanager.fragment

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.tasks.Task
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.hasan_cottage.finalmoneymanager.R
import com.hasan_cottage.finalmoneymanager.activity.PrivacyPolicyActivity
import com.hasan_cottage.finalmoneymanager.activity.SearchActivity
import com.hasan_cottage.finalmoneymanager.databinding.FragmentMoreBinding
import com.hasan_cottage.finalmoneymanager.roomDatabase.DatabaseAll
import com.hasan_cottage.finalmoneymanager.roomDatabase.Repository
import com.hasan_cottage.finalmoneymanager.viewModelClass.AppViewModel
import com.hasan_cottage.finalmoneymanager.viewModelClass.ViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MoreFragment : Fragment() {
    val binding by lazy {
        FragmentMoreBinding.inflate(layoutInflater)
    }
    var dilog: AlertDialog.Builder? = null
    private lateinit var reviewManager: ReviewManager
    private lateinit var reviewInfo: ReviewInfo

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val daoM = DatabaseAll.getInstanceAll(requireContext()).getAllDaoM()
        val repository = Repository(daoM)
        val myViewModel =
            ViewModelProvider(this, ViewModelFactory(repository))[AppViewModel::class.java]

        // Click search button =======
        binding.searce.setOnClickListener {
            startActivity(Intent(requireContext(), SearchActivity::class.java))
        }
        // item_one =============
        binding.itemOne.setOnClickListener(View.OnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, MainFragment()).addToBackStack(null).commit()
        })


        // item_tow =============
        binding.itemTow.setOnClickListener(View.OnClickListener {
            val cm =
                requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val nf = cm.activeNetworkInfo
            if (nf != null && nf.isConnected) {
                startActivity(Intent(requireContext(), PrivacyPolicyActivity::class.java))
            } else {
                Toast.makeText(requireContext(), "NO Internet Connection", Toast.LENGTH_SHORT)
                    .show()
            }

        })

        // item_four ==============
        binding.itemFour.setOnClickListener(View.OnClickListener {
            val pakegName = requireActivity().packageName
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Download App : https://play.google.com/store/apps/details?id=$pakegName"
            )
            requireActivity().startActivity(Intent.createChooser(intent, "Share via"))
        })

        // item_five ==============


        // item_five ==============
        binding.itemFive.setOnClickListener(View.OnClickListener {
            val developerName = "Atikul Software"
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
                        Uri.parse(" https://play.google.com/store/apps/details?id=$developerName")
                    )
                )
            }
        })


        // item eight =======

        binding.itemEight.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("DELETE THIS")
                .setMessage("Are you sure delete this item")
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
                .show()
        }

        promptInAppReview()
        binding.itemThree.setOnClickListener {
           revidw()
        }
        return binding.root

    }

    private fun promptInAppReview() {
        reviewManager = ReviewManagerFactory.create(requireContext())
        val reviewInfoTask: Task<ReviewInfo> = reviewManager.requestReviewFlow()
        reviewInfoTask.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                reviewInfo = task.result
            } else {
                Toast.makeText(requireContext(), "Not Task Complete", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun revidw() {
        if (::reviewInfo.isInitialized) { // Check if reviewInfo is initialized
            val flow: Task<Void> = reviewManager.launchReviewFlow(requireActivity(), reviewInfo)
            flow.addOnCompleteListener {
                Toast.makeText(requireContext(), "rating complete", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Review info not available yet", Toast.LENGTH_SHORT).show()
        }
    }

}