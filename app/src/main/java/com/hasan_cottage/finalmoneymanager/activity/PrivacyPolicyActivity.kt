package com.hasan_cottage.finalmoneymanager.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.hasan_cottage.finalmoneymanager.R

class PrivacyPolicyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)

        val webView:WebView=findViewById(R.id.webView)

        webView.loadUrl("https://sites.google.com/view/hasancottage/home")
    }
}