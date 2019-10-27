package com.busango.nonofficialbutsemi

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.webkit.CookieManager
import android.webkit.URLUtil
import android.webkit.WebSettings.LayoutAlgorithm
import android.webkit.WebView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.schoolboard.*
import kotlin.system.exitProcess


class SchoolBoard :Activity() {

    //TODO: 꼭 앱 죽이기 전에 먼저 반응받아서 죽는 놈들 정리할 것.

    private lateinit var scwebview: WebView

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
        setContentView(R.layout.schoolboard)

        viewcards()

        sbtv_date.setOnClickListener{
            scwebview.visibility = VISIBLE
            removecards()
            scwebview.loadUrl("http://busan.hs.kr/")
        }

        boarding01.setOnClickListener{
            scwebview.visibility = VISIBLE
            removecards()
            scwebview.loadUrl("http://busan.hs.kr/page/board/BOARD_1001/main.html?siteid=busanhs&boardid=BUSANHS_007&uid=&category=")
        }
        boarding02.setOnClickListener{
            scwebview.visibility = VISIBLE
            removecards()
            scwebview.loadUrl("http://busan.hs.kr/page/board/BOARD_1001/main.html?siteid=busanhs&boardid=BUSANHS_040&uid=&category=")
        }
        boarding03 .setOnClickListener{
            scwebview.visibility = VISIBLE
            removecards()
            scwebview.loadUrl("http://busan.hs.kr/asp/PLAN/PLAN_1001/main.html?siteid=busanhs&boardid=PLAN&uid=&category=&board_color=blue")
        }
        boarding04.setOnClickListener{
            scwebview.visibility = VISIBLE
            removecards()
            scwebview.loadUrl("http://busan.hs.kr/page/board/BOARD_1001/main.html?siteid=busanhs&boardid=BUSANHS_146&uid=&category=")
        }
        special01.setOnClickListener{
            scwebview.visibility = VISIBLE
            removecards()
            scwebview.loadUrl("http://busan.hs.kr/page/board/BOARD_1001/main.html?siteid=busanhs&boardid=BUSANHS_149&uid=&category=")
        }
        special02.setOnClickListener{
            scwebview.visibility = VISIBLE
            removecards()
            scwebview.loadUrl("http://busan.hs.kr/page/board/BOARD_1001/main.html?siteid=busanhs&boardid=BUSANHS_158&uid=&category=")
        }
        scboard01.setOnClickListener{
            scwebview.visibility = VISIBLE
            removecards()
            scwebview.loadUrl("http://busan.hs.kr/page/board/BOARD_1001/main.html?siteid=busanhs&boardid=BUSANHS_039&uid=&category=")
        }
        scboard02.setOnClickListener{
            scwebview.visibility = VISIBLE
            removecards()
            scwebview.loadUrl("http://busan.hs.kr/page/board/BOARD_1001/main.html?siteid=busanhs&boardid=BUSANHS_144&uid=&category=")
        }
        scboard03.setOnClickListener{
            scwebview.visibility = VISIBLE
            removecards()
            scwebview.loadUrl("http://busan.hs.kr/page/board/BOARD_1001/main.html?siteid=busanhs&boardid=BUSANHS_085&uid=&category=")
        }

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

        scwebview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN)

        scwebview.loadUrl("file:///android_asset/loading.html")
        scwebview.webViewClient = MainActivity.MyClient()
        scwebview.setDownloadListener { url, userAgent, contentDisposition, mimeType, _ ->
            val request = DownloadManager.Request(Uri.parse(url))
            request.setMimeType(mimeType)
            request.addRequestHeader("cookie", CookieManager.getInstance().getCookie(url))
            request.addRequestHeader("User-Agent", userAgent)
            request.setDescription("파일을 다운로드 하고 있습니다.")
            request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType))
            request.allowScanningByMediaScanner()
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalFilesDir(this@SchoolBoard, Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimeType))
            val dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(request)
            Toast.makeText(applicationContext, "파일을 다운로드 하고 있습니다.", Toast.LENGTH_LONG).show()
        }

        sc_nav_view.getMenu().getItem(2).setChecked(true)
        sc_nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private fun removecards() {
        sccard01.visibility = INVISIBLE
        sccard02.visibility = INVISIBLE
        sccard03.visibility = INVISIBLE
        scdatecard.visibility = INVISIBLE
        sbrecyclers.visibility = INVISIBLE
        Toast.makeText(applicationContext, "다른 항목으로 이동하려면 학교 공지사항 탭을 누르어 이동하십시오.", Toast.LENGTH_LONG).show()
    }

    private fun viewcards() {
        sccard01.visibility = VISIBLE
        sccard02.visibility = VISIBLE
        sccard03.visibility = VISIBLE
        scdatecard.visibility = VISIBLE
        sbrecyclers.visibility = VISIBLE
    }

//    class  : WebViewClient() {
//        override fun shouldOverrideUrlLoading(view: WebView, Url: String): Boolean {
//            view.loadUrl(Url)
//            return true
//        }
//    }

    private var time:Long = 0
    override fun onBackPressed() {
        if (System.currentTimeMillis() - time >= 2000)
        {
            if(scwebview.canGoBack()){
                scwebview.goBack()} else {
                time = System.currentTimeMillis()
                Toast.makeText(applicationContext, "뒤로가기 버튼을 한번 더 눌러 종료하십시오.", Toast.LENGTH_SHORT).show()
            }
        }
        else if (System.currentTimeMillis() - time < 2000)
        {
            ActivityCompat.finishAffinity(this)
            finish()
            exitProcess(0)
        }
    }
}