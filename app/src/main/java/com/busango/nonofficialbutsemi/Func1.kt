package com.busango.nonofficialbutsemi

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Spinner
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.digitalid.*
import kotlinx.android.synthetic.main.docenter_layout.*
import kotlinx.android.synthetic.main.docenter_layout.do_nav_view
import kotlinx.android.synthetic.main.schedule.*
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.webkit.WebSettings
import java.util.*


class Func1 :Activity() {

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
        setContentView(R.layout.schedule)
        do_nav_view.menu.getItem(1).isChecked = true

        wv_schedule.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wv_schedule.loadUrl("http://dev.khskeb0513.pw/schget/y/2020/01/01")

        spn02.setOnClickListener { view ->
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            when(rdg01.checkedRadioButtonId) {
                R.id.rb_ymd -> {
                    val date_listener  = object : DatePickerDialog.OnDateSetListener {
                        @SuppressLint("SetTextI18n")
                        override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                            val datedata = "$year/${month + 1}/$dayOfMonth"

                            wv_schedule.loadUrl("http://dev.khskeb0513.pw/schget/ymd/$datedata")
                        }
                    }
                    val builder = DatePickerDialog(this, date_listener, year, month, day)
                    builder.show()
                }
                R.id.rb_ym -> {
                    val date_listener  = object : DatePickerDialog.OnDateSetListener {
                        @SuppressLint("SetTextI18n")
                        override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                            val datedata = "$year/${month + 1}/$dayOfMonth"

                            wv_schedule.loadUrl("http://dev.khskeb0513.pw/schget/ym/$datedata")
                        }
                    }
                    val builder = DatePickerDialog(this, date_listener, year, month, day)
                    builder.show()
                }
                R.id.rb_y -> {
                    val date_listener  = object : DatePickerDialog.OnDateSetListener {
                        @SuppressLint("SetTextI18n")
                        override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                            val datedata = "$year/${month + 1}/$dayOfMonth"

                            wv_schedule.loadUrl("http://dev.khskeb0513.pw/schget/y/$datedata")
                        }
                    }
                    val builder = DatePickerDialog(this, date_listener, year, month, day)
                    builder.show()
                }
                R.id.rb_today -> {
                    wv_schedule.loadUrl("http://dev.khskeb0513.pw/schget/today/2019/01/01")
                }
                R.id.rb_tomorrow -> {
                    wv_schedule.loadUrl("http://dev.khskeb0513.pw/schget/tomorrow/2019/01/01")
                }
            }
        }

        do_nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    override fun onBackPressed() {
        finish()
    }
}