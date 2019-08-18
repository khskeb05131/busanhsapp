package com.busango.nonofficialbutsemi

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class SearchPage :Activity() {

    var mURL: String? = null
    var mTitle: String? = null

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                scwebview.visibility = View.VISIBLE
                searchpagepar.visibility = INVISIBLE
                screcyclers.visibility = INVISIBLE
                my_recycler_view01.visibility = INVISIBLE
                my_recycler_view02.visibility = INVISIBLE
                my_recycler_view03.visibility = INVISIBLE
                scwebview.loadUrl("https://busanhs.win/kotlin/tommeal.php")
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                scwebview.visibility = View.VISIBLE
                searchpagepar.visibility = INVISIBLE
                screcyclers.visibility = INVISIBLE
                my_recycler_view01.visibility = INVISIBLE
                my_recycler_view02.visibility = INVISIBLE
                my_recycler_view03.visibility = INVISIBLE
                scwebview.loadUrl("http://busan.hs.kr/page/board/BOARD_1001/main.html?siteid=busanhs&boardid=BUSANHS_007&uid=&category=")
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
        }

        searchpagepar.visibility = VISIBLE
        searchpage.visibility = VISIBLE
        searchpage.settings.javaScriptEnabled = true
//        searchpage.loadUrl("http://khskeb0513.pw/time1")
        nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        init()
    }

    fun init() {

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