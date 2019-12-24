package com.busango.nonofficialbutsemi

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.webkit.CookieManager
import android.webkit.URLUtil
import android.webkit.WebSettings.LayoutAlgorithm
import android.webkit.WebView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.recyclerview_item.view.*
import kotlinx.android.synthetic.main.schoolboard.*
import kotlinx.android.synthetic.main.schoolboard.sccard01
import kotlinx.android.synthetic.main.schoolboard.bg_disable_site
import kotlinx.android.synthetic.main.schoolboard.scdatecard
import kotlin.system.exitProcess
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




class SchoolBoard :Activity() {

    //TODO: 꼭 앱 죽이기 전에 먼저 반응받아서 죽는 놈들 정리할 것.

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

        bg_disable_site.bringToFront()
        bg_disable_site.setOnClickListener {
            bg_disable_site.visibility = View.GONE
        }

        removecards()

        bg_disable_site.bringToFront()

        viewcards()

        sbtv_date.setOnClickListener{
            val intent = Intent(applicationContext, BoardWebview::class.java)
            intent.putExtra("url","http://busan.hs.kr")
            startActivity(intent)
        }

        boarding01.setOnClickListener{
            val intent = Intent(applicationContext, BoardWebview::class.java)
            intent.putExtra("url","http://busan.hs.kr/page/board/BOARD_1001/main.html?siteid=busanhs&boardid=BUSANHS_007&uid=&category=")
            startActivity(intent)
        }
        boarding02.setOnClickListener{
            val intent = Intent(applicationContext, BoardWebview::class.java)
            intent.putExtra("url","http://busan.hs.kr/page/board/BOARD_1001/main.html?siteid=busanhs&boardid=BUSANHS_040&uid=&category=")
            startActivity(intent)
        }
        boarding03 .setOnClickListener{
            val intent = Intent(applicationContext, BoardWebview::class.java)
            intent.putExtra("url","http://busan.hs.kr/asp/PLAN/PLAN_1001/main.html?siteid=busanhs&boardid=PLAN&uid=&category=&board_color=blue")
            startActivity(intent)
        }
        boarding04.setOnClickListener{
            val intent = Intent(applicationContext, BoardWebview::class.java)
            intent.putExtra("url","http://busan.hs.kr/page/board/BOARD_1001/main.html?siteid=busanhs&boardid=BUSANHS_146&uid=&category=")
            startActivity(intent)
        }
        special01.setOnClickListener{
            val intent = Intent(applicationContext, BoardWebview::class.java)
            intent.putExtra("url","http://busan.hs.kr/page/board/BOARD_1001/main.html?siteid=busanhs&boardid=BUSANHS_149&uid=&category=")
            startActivity(intent)
        }
        special02.setOnClickListener{
            val intent = Intent(applicationContext, BoardWebview::class.java)
            intent.putExtra("url","http://busan.hs.kr/page/board/BOARD_1001/main.html?siteid=busanhs&boardid=BUSANHS_158&uid=&category=")
            startActivity(intent)
        }
        scboard01.setOnClickListener{
            val intent = Intent(applicationContext, BoardWebview::class.java)
            intent.putExtra("url","http://busan.hs.kr/page/board/BOARD_1001/main.html?siteid=busanhs&boardid=BUSANHS_039&uid=&category=")
            startActivity(intent)
        }
        scboard02.setOnClickListener{
            val intent = Intent(applicationContext, BoardWebview::class.java)
            intent.putExtra("url","http://busan.hs.kr/page/board/BOARD_1001/main.html?siteid=busanhs&boardid=BUSANHS_144&uid=&category=")
            startActivity(intent)
        }
        scboard03.setOnClickListener{
            val intent = Intent(applicationContext, BoardWebview::class.java)
            intent.putExtra("url","http://busan.hs.kr/page/board/BOARD_1001/main.html?siteid=busanhs&boardid=BUSANHS_085&uid=&category=")
            startActivity(intent)
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

    private var time:Long = 0
    override fun onBackPressed() {
        if (System.currentTimeMillis() - time >= 2000)
        {
            time = System.currentTimeMillis()
            Toast.makeText(applicationContext, "뒤로가기 버튼을 한번 더 눌러 종료하십시오.", Toast.LENGTH_SHORT).show()
        }
        else if (System.currentTimeMillis() - time < 2000)
        {
            ActivityCompat.finishAffinity(this)
            finish()
            exitProcess(0)
        }
    }
}