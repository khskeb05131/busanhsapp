package com.busango.nonofficialbutsemi

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.DatePicker
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.docenter_layout.do_nav_view
import kotlinx.android.synthetic.main.schedule.*
import java.text.SimpleDateFormat
import java.util.*


class Func1 :Activity() {

    private lateinit var webView: WebView

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

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.schedule)
        do_nav_view.menu.getItem(1).isChecked = true

        webView = findViewById(R.id.wv_schedule)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        webView.settings.javaScriptEnabled = true

        wv_schedule.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN;
        wv_schedule.loadUrl("http://dev.khskeb0513.pw/schget/nowyear/2020/01/01")

        spn02.setOnClickListener { view ->
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val oldFormat = SimpleDateFormat("yyyy/M/d") // 받은 데이터 형식
            oldFormat.setTimeZone(TimeZone.getTimeZone("KST"))
            val newFormat = SimpleDateFormat("yyyy/MM/dd") // 바꿀 데이터 형식
            val formatY = SimpleDateFormat("yyyy년 조회하였습니다.") // 바꿀 데이터 형식
            val formatYM = SimpleDateFormat("yyyy년 MM월 조회하였습니다.") // 바꿀 데이터 형식

            when(rdg01.checkedRadioButtonId) {
                R.id.rb_ymd -> {
                    val datelistener  = object : DatePickerDialog.OnDateSetListener {
                        @SuppressLint("SetTextI18n")
                        override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                            val datedata = "$year/${month + 1}/$dayOfMonth"
                            println("http://dev.khskeb0513.pw/schget/ymd/$datedata")
                            val oldDate = oldFormat.parse(datedata)
                            val newDate = newFormat.format(oldDate)
                            tv_query.text = "$newDate" + " 조회하였습니다."
                            wv_schedule.loadUrl("http://dev.khskeb0513.pw/schget/ymd/$newDate")
                        }
                    }
                    val builder = DatePickerDialog(this, datelistener, year, month, day)
                    builder.show()
                }
                R.id.rb_ym -> {
                    val date_listener  = object : DatePickerDialog.OnDateSetListener {
                        @SuppressLint("SetTextI18n")
                        override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                            val datedata = "$year/${month + 1}/$dayOfMonth"
                            println("http://dev.khskeb0513.pw/schget/ym/$datedata")
                            val oldDate = oldFormat.parse(datedata)
                            val newDate = newFormat.format(oldDate)
                            val expressDate = formatYM.format(oldDate)
                            tv_query.text = "$expressDate"
                            wv_schedule.loadUrl("http://dev.khskeb0513.pw/schget/ym/$newDate")
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
                            println("http://dev.khskeb0513.pw/schget/y/$datedata")
                            val oldDate = oldFormat.parse(datedata)
                            val newDate = newFormat.format(oldDate)
                            val expressDate = formatY.format(oldDate)
                            tv_query.text = "$expressDate"
                            wv_schedule.loadUrl("http://dev.khskeb0513.pw/schget/y/$newDate")
                        }
                    }
                    val builder = DatePickerDialog(this, date_listener, year, month, day)
                    builder.show()
                }
                R.id.rb_today -> {
                    println("Query Today")
                    tv_query.text = "오늘자 조회하였습니다."
                    wv_schedule.loadUrl("http://dev.khskeb0513.pw/schget/today/2019/01/01")
                }
                R.id.rb_tomorrow -> {
                    println("Query Tomorrow")
                    tv_query.text = "내일자 조회하였습니다."
                    wv_schedule.loadUrl("http://dev.khskeb0513.pw/schget/tomorrow/2019/01/01")
                }
                else -> {
                    Toast.makeText(this, "조회유형을 먼저 선택하여 주십시오.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        do_nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    override fun onBackPressed() {
        finish()
    }
}