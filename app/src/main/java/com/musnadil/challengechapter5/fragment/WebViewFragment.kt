package com.musnadil.challengechapter5.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.musnadil.challengechapter5.databinding.FragmentWebViewBinding


class WebViewFragment : Fragment() {
    private var _binding: FragmentWebViewBinding? = null
    private val binding get() = _binding!!
    private lateinit var webView : WebView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebViewBinding.inflate(inflater,container,false)
        val url = arguments?.getString("url").toString()
        webView = binding.wvDetailNews
        webView.webViewClient = WebViewClient()
        webView.loadUrl(url)
        webView.canGoBack()
        val webSettings : WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true

        return binding.root
    }
}