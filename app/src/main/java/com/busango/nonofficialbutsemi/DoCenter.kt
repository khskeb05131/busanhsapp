package com.busango.nonofficialbutsemi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.GridLayout
import android.widget.Switch
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.docenter_layout.*
import kotlin.system.exitProcess

class DoCenter :Activity() {

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
        setContentView(R.layout.docenter_layout)
//        do_nav_view.menu.getItem(1).isChecked = true
        do_nav_view.menu.getItem(1).isChecked = true

        val mainGrid = findViewById<GridLayout>(R.id.mainGrid)
        //Set Event
        setSingleEvent(mainGrid)

        do_nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private fun setSingleEvent(mainGrid: GridLayout) {
        //Loop all child item of Main Grid
        for (i in 0 until mainGrid.childCount) {
            //You can see , all child item is CardView , so we just cast object to CardView
            val cardView = mainGrid.getChildAt(i) as CardView
            cardView.setOnClickListener {
//                val intent = Intent(thisthis@DoCenter, ActivityOne::class.java)
//                intent.putExtra("info", "This is activity from card item index  $i")
//                startActivity(intent)
                Toast.makeText(this@DoCenter, "Func$i clicked", Toast.LENGTH_SHORT).show()
                if ( i == 0) {
                    Toast.makeText(this@DoCenter, R.string.func0_msg, Toast.LENGTH_LONG).show()
                    val intent = Intent(this, Func0::class.java)
                    startActivity(intent,null)
                } else if (i == 1) {
                    val intent = Intent(this, Func1::class.java)
                    startActivity(intent,null)
                } else if (i == 2) {
                    Toast.makeText(this@DoCenter, "Func$i empty", Toast.LENGTH_SHORT).show()
                } else if (i == 3) {
                    Toast.makeText(this@DoCenter, "Func$i empty", Toast.LENGTH_SHORT).show()
                }
            }
        }
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