package com.phoncleaner.boosterapp.phonemaster.ui

import android.content.Intent
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.phoncleaner.boosterapp.phonemaster.databinding.FragmentWebViewBinding

class WebViewFragment : AppCompatActivity(){
    lateinit var binding: FragmentWebViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.mbtnCancel.setOnClickListener{

            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
        binding.mbtnAccept.setOnClickListener{
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
        binding.webviewPolicy.settings.domStorageEnabled = true
        binding.webviewPolicy.settings.javaScriptEnabled = true
        binding.webviewPolicy.webViewClient = object :WebViewClient(){}
        binding.webviewPolicy.webChromeClient = object :WebChromeClient(){}
        binding.webviewPolicy.settings.setSupportMultipleWindows(true)
        binding.webviewPolicy.settings.allowFileAccess = true
        binding.webviewPolicy.loadUrl("https://sites.google.com/view/super-cleaner-phone-booster/home")

    }


}