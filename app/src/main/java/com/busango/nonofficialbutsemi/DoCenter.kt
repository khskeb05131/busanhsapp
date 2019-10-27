package com.busango.nonofficialbutsemi

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.docenter.*
import kotlin.system.exitProcess

class DoCenter :Activity() {

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

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        TODO 캡쳐 및 보안 설비
//        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        setContentView(R.layout.docenter)
        do_nav_view.menu.getItem(1).isChecked = true
        tv_docenter.text = "DoCenter 액티비티 준비"
        do_nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
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