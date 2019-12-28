package com.busango.nonofficialbutsemi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.schoolboard.*
import kotlinx.android.synthetic.main.schoolboard.sccard01
import kotlinx.android.synthetic.main.schoolboard.scdatecard
import kotlin.system.exitProcess




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

        removecards()

        viewcards()

        sbtv_date.setOnClickListener{
            val intent = Intent(applicationContext, BoardWebview::class.java)
            intent.putExtra("url","http://school.busanedu.net/busan-h/main.do")
            startActivity(intent)
        }

        boarding01.setOnClickListener{
            val intent = Intent(applicationContext, BoardWebview::class.java)
            intent.putExtra("url","http://school.busanedu.net/busan-h/na/ntt/selectNttList.do?mi=611183&bbsId=1006717")
            startActivity(intent)
        }
        boarding02.setOnClickListener{
            val intent = Intent(applicationContext, BoardWebview::class.java)
            intent.putExtra("url","http://school.busanedu.net/busan-h/na/ntt/selectNttList.do?mi=611184&bbsId=1006718")
            startActivity(intent)
        }
        boarding03 .setOnClickListener{
            val intent = Intent(applicationContext, BoardWebview::class.java)
            intent.putExtra("url","http://school.busanedu.net/busan-h/sv/schdulView/schdulCalendarView.do?mi=611191")
            startActivity(intent)
        }
        boarding04.setOnClickListener{
            val intent = Intent(applicationContext, BoardWebview::class.java)
            intent.putExtra("url","http://school.busanedu.net/busan-h/na/ntt/selectNttList.do?mi=611192&bbsId=1006724")
            startActivity(intent)
        }
        special01.setOnClickListener{
            val intent = Intent(applicationContext, BoardWebview::class.java)
            intent.putExtra("url","http://school.busanedu.net/busan-h/na/ntt/selectNttList.do?mi=611216&bbsId=1006737")
            startActivity(intent)
        }
        special02.setOnClickListener{
            val intent = Intent(applicationContext, BoardWebview::class.java)
            intent.putExtra("url","http://school.busanedu.net/busan-h/na/ntt/selectNttList.do?mi=611217&bbsId=1006738")
            startActivity(intent)
        }
        special03.setOnClickListener{
            val intent = Intent(applicationContext, BoardWebview::class.java)
            intent.putExtra("url","http://school.busanedu.net/busan-h/na/ntt/selectNttList.do?mi=611218&bbsId=1006739")
            startActivity(intent)
        }
        special04.setOnClickListener{
            val intent = Intent(applicationContext, BoardWebview::class.java)
            intent.putExtra("url","http://school.busanedu.net/busan-h/na/ntt/selectNttList.do?mi=611224&bbsId=1006744")
            startActivity(intent)
        }
        scboard01.setOnClickListener{
            val intent = Intent(applicationContext, BoardWebview::class.java)
            intent.putExtra("url","http://school.busanedu.net/busan-h/na/ntt/selectNttList.do?mi=611237&bbsId=1006755")
            startActivity(intent)
        }
        scboard02.setOnClickListener{
            val intent = Intent(applicationContext, BoardWebview::class.java)
            intent.putExtra("url","http://school.busanedu.net/busan-h/na/ntt/selectNttList.do?mi=611244&bbsId=1006761")
            startActivity(intent)
        }
        scboard03.setOnClickListener{
            val intent = Intent(applicationContext, BoardWebview::class.java)
            intent.putExtra("url","http://school.busanedu.net/busan-h/na/ntt/selectNttList.do?mi=611268&bbsId=1006772")
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
//        Toast.makeText(applicationContext, "다른 항목으로 이동하려면 학교 공지사항 탭을 누르어 이동하십시오.", Toast.LENGTH_LONG).show()
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