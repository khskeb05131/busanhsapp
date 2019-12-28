package com.busango.nonofficialbutsemi

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.View.*
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class SearchPage :Activity() {

    private var mURL: String? = null
    private var mTitle: String? = null

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                val intent = Intent(this, MainActivity::class.java)
                Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                val intent = Intent(this, DoCenter::class.java)
                Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                val intent = Intent(this, SchoolBoard::class.java)
                Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        TODO 캡쳐 및 보안 방지 설비
//        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        setContentView(R.layout.activity_main)
        mTitle = intent.getStringExtra("title")
        mURL = intent.getStringExtra("url")
        atloading.visibility = INVISIBLE

        searchpage.webViewClient = object: WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, Url: String): Boolean {
                view.loadUrl(Url)
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                pb_par01.visibility = VISIBLE
                pb_par01.bringToFront()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                pb_par01.visibility = GONE
            }
        }

        searchpagepar.visibility = VISIBLE
        searchpage.visibility = VISIBLE
        searchpage.settings.javaScriptEnabled = true
//        searchpage.loadUrl("http://khskeb0513.pw/time1")
        nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        init()
    }

    private fun init() {

        searchpage.setBackgroundColor(Color.WHITE)
        searchpage.scrollBarStyle = WebView.SCROLLBARS_INSIDE_OVERLAY
        searchpage.settings.databaseEnabled = false
        searchpage.settings.setRenderPriority(WebSettings.RenderPriority.HIGH)
        searchpage.settings.javaScriptEnabled = true
        searchpage.settings.allowFileAccess = true
        searchpage.settings.domStorageEnabled = true
        searchpage.settings.setSupportMultipleWindows(false)
        searchpage.settings.builtInZoomControls = false
        searchpage.settings.setSupportZoom(false)
        searchpage.settings.setAppCacheEnabled(true)
        searchpage.clearHistory()
        searchpage.clearCache(false)

        sendRequests()
    }

    private fun sendRequests() {

        if (mURL != null || mURL.equals("", ignoreCase = true)) {
            searchpage.loadUrl(mURL)
        } else {
            Toast.makeText(this, "url이 정확하지 않습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }

    }

}