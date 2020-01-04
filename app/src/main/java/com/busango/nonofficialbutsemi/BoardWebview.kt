package com.busango.nonofficialbutsemi

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.webkit.*
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.schoolwebview.sc_nav_view



class BoardWebview : Activity() {

    private lateinit var scwebview: WebView

    private var mURL: String? = null

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                val intent = Intent(this, MainActivity::class.java)
                Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent,null)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                val intent = Intent(this, DoCenter::class.java)
                Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent,null)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                val intent = Intent(this, SchoolBoard::class.java)
                Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent,null)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        TODO 캡쳐 및 보안 방지 설비
//        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        setContentView(R.layout.schoolwebview)
        mURL = intent.getStringExtra("url")

        scwebview = findViewById(R.id.scwebview)
        scwebview.settings.javaScriptEnabled = true
        scwebview.settings.domStorageEnabled = true
        scwebview.isVerticalScrollBarEnabled = false

        scwebview.setBackgroundColor(Color.WHITE)
        scwebview.settings.setSupportMultipleWindows(false)
        scwebview.settings.builtInZoomControls = false
        scwebview.settings.setSupportZoom(false)
        scwebview.settings.setAppCacheEnabled(true)
        scwebview.clearHistory()
        scwebview.isHorizontalScrollBarEnabled = false
        scwebview.clearCache(false)

        scwebview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN)

        scwebview.loadUrl("file:///android_asset/loading.html")
//        scwebview.webViewClient = BoardWebview.MyClient()
        scwebview.setDownloadListener { url, userAgent, contentDisposition, mimeType, _ ->
            val request = DownloadManager.Request(Uri.parse(url))
            request.setMimeType(mimeType)
            request.addRequestHeader("cookie", CookieManager.getInstance().getCookie(url))
            request.addRequestHeader("User-Agent", userAgent)
            request.setDescription("파일을 다운로드 하고 있습니다.")
            request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType))
            request.allowScanningByMediaScanner()
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalFilesDir(this@BoardWebview, Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimeType))
            val dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(request)
            Toast.makeText(applicationContext, "파일을 다운로드 하고 있습니다.", Toast.LENGTH_LONG).show()
        }

        scwebview.webViewClient = object: WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, Url: String): Boolean {
                view.loadUrl(Url)
                return true
            }
        }

        scwebview.loadUrl(mURL)

        sc_nav_view.getMenu().getItem(2).setChecked(true)
        sc_nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    override fun onBackPressed() {
        if(scwebview.canGoBack()){
            scwebview.goBack()} else {
            val intent = Intent(this, SchoolBoard::class.java)
            Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent,null)
        }
    }
}