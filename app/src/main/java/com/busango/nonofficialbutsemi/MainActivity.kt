package com.busango.nonofficialbutsemi

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Looper
import android.util.Log
import android.view.View.*
import android.webkit.CookieManager
import android.webkit.URLUtil
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException
import java.util.*
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    private lateinit var mywebview: WebView
    private lateinit var searchpage: WebView

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                val intent = Intent(this, MainActivity::class.java)
//                Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                mywebview.visibility = INVISIBLE
                searchpagepar.visibility = INVISIBLE
                screcyclers.visibility = INVISIBLE
                my_recycler_view01.visibility = INVISIBLE
                my_recycler_view02.visibility = INVISIBLE
                my_recycler_view03.visibility = INVISIBLE
                val intent = Intent(this, DoCenter::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                mywebview.visibility = INVISIBLE
                searchpagepar.visibility = INVISIBLE
                screcyclers.visibility = INVISIBLE
                my_recycler_view01.visibility = INVISIBLE
                my_recycler_view02.visibility = INVISIBLE
                my_recycler_view03.visibility = INVISIBLE
//                mywebview.loadUrl("http://busan.hs.kr/page/board/BOARD_1001/main.html?siteid=busanhs&boardid=BUSANHS_007&uid=&category=")
                val intent = Intent(this, SchoolBoard::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        sc_nav_view.getMenu().getItem(0).setChecked(true)

        val pref = getSharedPreferences("isFirst", Activity.MODE_PRIVATE)
        val first = pref.getBoolean("isFirst", false)
        if (!first)
        {
            Log.d("Is first Time?", "first")
            val editor = pref.edit()
            editor.putBoolean("isFirst", true)
            editor.apply()
            firstrunbg.visibility = VISIBLE
            firstrunbg.bringToFront()
            firstrunbg.setOnClickListener {
                firstrunbg.visibility = GONE
            }
        }
        else
        {
            Log.d("Is first Time?", "not first")
        }

        my_recycler_view01.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        my_recycler_view01.setHasFixedSize(true)
        my_recycler_view02.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        my_recycler_view02.setHasFixedSize(true)
        my_recycler_view03.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        my_recycler_view03.setHasFixedSize(true)

        //tomorrow
        tommy_recycler_view01.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        tommy_recycler_view01.setHasFixedSize(true)
        tommy_recycler_view02.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        tommy_recycler_view02.setHasFixedSize(true)
        tommy_recycler_view03.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        tommy_recycler_view03.setHasFixedSize(true)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        //recycler view first run
        screcyclers.visibility = VISIBLE
        my_recycler_view01.visibility = VISIBLE
        my_recycler_view02.visibility = VISIBLE
        my_recycler_view03.visibility = VISIBLE
        tommy_recycler_view01.visibility = GONE
        tommy_recycler_view02.visibility = GONE
        tommy_recycler_view03.visibility = GONE

        //tomorrow recycler view first run

        //searchpage - custom
        searchpagepar.visibility = INVISIBLE

        //cal setting
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH) + 1
        val date = cal.get(Calendar.DATE)
        val dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)

        var korDayOfWeek = ""
        when (dayOfWeek) {
            1 -> korDayOfWeek = "일요일"
            2 -> korDayOfWeek = "월요일"
            3 -> korDayOfWeek = "화요일"
            4 -> korDayOfWeek = "수요일"
            5 -> korDayOfWeek = "목요일"
            6 -> korDayOfWeek = "금요일"
            7 -> korDayOfWeek = "토요일"
        }
        val yr = "년 "
        val mn = "월 "
        val dy = "일 "

        //tomorrow
        var tomkorDayOfWeek = ""
        when (dayOfWeek) {
            1 -> tomkorDayOfWeek = "월요일"
            2 -> tomkorDayOfWeek = "화요일"
            3 -> tomkorDayOfWeek = "수요일"
            4 -> tomkorDayOfWeek = "목요일"
            5 -> tomkorDayOfWeek = "금요일"
            6 -> tomkorDayOfWeek = "토요일"
            7 -> tomkorDayOfWeek = "일요일"
        }

        cal.add(Calendar.DAY_OF_MONTH, +1)
        val tomdate = cal.get(Calendar.DAY_OF_MONTH)
        val tommonth = cal.get(Calendar.MONTH)+1

        val tomtvtext:String = "$year$yr$tommonth$mn$tomdate$dy$tomkorDayOfWeek"
        val todtvtext:String = "$year$yr$month$mn$date$dy$korDayOfWeek"

        tv_date.text = todtvtext
        tomtv_date.text = tomtvtext

        //오늘의 속성을 담음
        tv_date.setOnClickListener{
            tv_date.visibility = GONE
            tomtv_date.visibility = VISIBLE
            my_recycler_view01.visibility = GONE
            my_recycler_view02.visibility = GONE
            my_recycler_view03.visibility = GONE
            tommy_recycler_view01.visibility = VISIBLE
            tommy_recycler_view02.visibility = VISIBLE
            tommy_recycler_view03.visibility = VISIBLE
        }

        //내일의 속성을 담음
        tomtv_date.setOnClickListener{
            tv_date.visibility = VISIBLE
            tomtv_date.visibility = GONE
            my_recycler_view01.visibility = VISIBLE
            my_recycler_view02.visibility = VISIBLE
            my_recycler_view03.visibility = VISIBLE
            tommy_recycler_view01.visibility = GONE
            tommy_recycler_view02.visibility = GONE
            tommy_recycler_view03.visibility = GONE
        }

        mywebview = findViewById(R.id.scwebview)
        mywebview.settings.javaScriptEnabled = true
        mywebview.settings.domStorageEnabled = true
        mywebview.loadUrl("file:///android_asset/loading.html")
        mywebview.webViewClient = MyClient()
        mywebview.setDownloadListener { url, userAgent, contentDisposition, mimeType, _ ->
            val request = DownloadManager.Request(Uri.parse(url))
            request.setMimeType(mimeType)
            request.addRequestHeader("cookie", CookieManager.getInstance().getCookie(url))
            request.addRequestHeader("User-Agent", userAgent)
            request.setDescription("파일을 다운로드 하고 있습니다.")
            request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType))
            request.allowScanningByMediaScanner()
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalFilesDir(this@MainActivity, Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimeType))
            val dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(request)
            Toast.makeText(applicationContext, "파일을 다운로드 하고 있습니다.", Toast.LENGTH_LONG).show()
        }

        fetchJson()

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    class MyClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, Url: String): Boolean {
            view.loadUrl(Url)
            return true
        }
    }

    private fun fetchJson(){
        println("데어터를 가져 오는 중...")
//        val key = "6c49687a476b687332385a59476d4d"

        val url1 = "http://khskeb0513.pw/time1"
        val url2 = "http://khskeb0513.pw/time2"
        val url3 = "http://khskeb0513.pw/time3"
        //tomorrow
        val tomurl1 = "http://khskeb0513.pw/time1tom"
        val tomurl2 = "http://khskeb0513.pw/time2tom"
        val tomurl3 = "http://khskeb0513.pw/time3tom"

        val request1 = Request.Builder().url(url1).build()
        val client1 = OkHttpClient()
        val request2 = Request.Builder().url(url2).build()
        val client2 = OkHttpClient()
        val request3 = Request.Builder().url(url3).build()
        val client3 = OkHttpClient()

        //tomorrow
        val tomrequest1 = Request.Builder().url(tomurl1).build()
        val tomclient1 = OkHttpClient()
        val tomrequest2 = Request.Builder().url(tomurl2).build()
        val tomclient2 = OkHttpClient()
        val tomrequest3 = Request.Builder().url(tomurl3).build()
        val tomclient3 = OkHttpClient()

        client1.newCall(request1).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                println(body)
                //파싱 - 이렇게 가져온 데이터를 모델오브젝트로 변환해 줘야 한다.
                val gson = GsonBuilder().create()
                val parser = JsonParser()
                //루트 ob
                //루트 object와 경로를 찾아서 설정해 주는데 이부분에서 개념이 안 잡혀서 헤메다. 원하는 데이터가 속에 속에 들어 있어서...
                val rootObj = parser.parse(body.toString())
                    .asJsonObject.get("searchresult")
                val books =  gson.fromJson(rootObj, RentInfo::class.java)
                //썸네일을 위한 추가 작업
                println(books.row[0].menu)

                //백그라운드에서 돌기 때문에 메인UI로 접근할 수 있도록 해줘야 한다.
                runOnUiThread {
                    //어답터 설정
                    my_recycler_view01.adapter = RecyclerViewAdapter(books)
                }
            }
            override fun onFailure(call: Call, e: IOException) {
//                Looper.prepare()
//                val toasttext = "네트워크 상태를 확인하신 후 이용하여 주십시오."
//                Toast.makeText(applicationContext, toasttext, Toast.LENGTH_SHORT).show()
//                Looper.loop()
                println("리퀘스트 실패")
            }
        })
        client2.newCall(request2).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                println(body)
                //파싱 - 이렇게 가져온 데이터를 모델오브젝트로 변환해 줘야 한다.
                val gson = GsonBuilder().create()
                val parser = JsonParser()
                //루트 object와 경로를 찾아서 설정해 주는데 이부분에서 개념이 안 잡혀서 헤메다. 원하는 데이터가 속에 속에 들어 있어서...
                val rootObj = parser.parse(body.toString())
                    .asJsonObject.get("searchresult")
                val books =  gson.fromJson(rootObj, RentInfo::class.java)
                //썸네일을 위한 추가 작업
                println(books.row[0].menu)

                //백그라운드에서 돌기 때문에 메인UI로 접근할 수 있도록 해줘야 한다.
                runOnUiThread {
                    //어답터 설정
                    my_recycler_view02.adapter = RecyclerViewAdapter(books)
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                println("리퀘스트 실패")
            }
        })
        client3.newCall(request3).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                println(body)

                //파싱 - 이렇게 가져온 데이터를 모델오브젝트로 변환해 줘야 한다.
                val gson = GsonBuilder().create()
                val parser = JsonParser()
                //루트 ob
                //루트 object와 경로를 찾아서 설정해 주는데 이부분에서 개념이 안 잡혀서 헤메다. 원하는 데이터가 속에 속에 들어 있어서...
                val rootObj = parser.parse(body.toString())
                    .asJsonObject.get("searchresult")
                val books =  gson.fromJson(rootObj, RentInfo::class.java)
                //썸네일을 위한 추가 작업
                println(books.row[0].menu)

                //백그라운드에서 돌기 때문에 메인UI로 접근할 수 있도록 해줘야 한다.
                runOnUiThread {
                    //어답터 설정
                    my_recycler_view03.adapter = RecyclerViewAdapter(books)
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                println("리퀘스트 실패")
            }
        })

        //tomorrow
        tomclient1.newCall(tomrequest1).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                println(body)
                //파싱 - 이렇게 가져온 데이터를 모델오브젝트로 변환해 줘야 한다.
                val gson = GsonBuilder().create()
                val parser = JsonParser()
                //루트 ob
                //루트 object와 경로를 찾아서 설정해 주는데 이부분에서 개념이 안 잡혀서 헤메다. 원하는 데이터가 속에 속에 들어 있어서...
                val rootObj = parser.parse(body.toString())
                    .asJsonObject.get("searchresult")
                val books =  gson.fromJson(rootObj, RentInfo::class.java)
                //썸네일을 위한 추가 작업
                println(books.row[0].menu)

                //백그라운드에서 돌기 때문에 메인UI로 접근할 수 있도록 해줘야 한다.
                runOnUiThread {
                    //어답터 설정
                    tommy_recycler_view01.adapter = RecyclerViewAdapter(books)
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                Looper.prepare()
                val toasttext = "서버와의 연결상태가 불안정합니다. 네트워크 연결상태를 확인하여 주십시오."
                Toast.makeText(applicationContext, toasttext, Toast.LENGTH_SHORT).show()
                Looper.loop()
                println("리퀘스트 실패")
            }
        })
        tomclient2.newCall(tomrequest2).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                println(body)
                //파싱 - 이렇게 가져온 데이터를 모델오브젝트로 변환해 줘야 한다.
                val gson = GsonBuilder().create()
                val parser = JsonParser()
                //루트 object와 경로를 찾아서 설정해 주는데 이부분에서 개념이 안 잡혀서 헤메다. 원하는 데이터가 속에 속에 들어 있어서...
                val rootObj = parser.parse(body.toString())
                    .asJsonObject.get("searchresult")
                val books =  gson.fromJson(rootObj, RentInfo::class.java)
                //썸네일을 위한 추가 작업
                println(books.row[0].menu)

                //백그라운드에서 돌기 때문에 메인UI로 접근할 수 있도록 해줘야 한다.
                runOnUiThread {
                    //어답터 설정
                    tommy_recycler_view02.adapter = RecyclerViewAdapter(books)
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                println("리퀘스트 실패")
            }
        })
        tomclient3.newCall(tomrequest3).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                println(body)

                //파싱 - 이렇게 가져온 데이터를 모델오브젝트로 변환해 줘야 한다.
                val gson = GsonBuilder().create()
                val parser = JsonParser()
                //루트 ob
                //루트 object와 경로를 찾아서 설정해 주는데 이부분에서 개념이 안 잡혀서 헤메다. 원하는 데이터가 속에 속에 들어 있어서...
                val rootObj = parser.parse(body.toString())
                    .asJsonObject.get("searchresult")
                val books =  gson.fromJson(rootObj, RentInfo::class.java)
                //썸네일을 위한 추가 작업
                println(books.row[0].menu)

                //백그라운드에서 돌기 때문에 메인UI로 접근할 수 있도록 해줘야 한다.
                runOnUiThread {
                    //어답터 설정
                    tommy_recycler_view03.adapter = RecyclerViewAdapter(books)
                    atloading.visibility = INVISIBLE
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                println("리퀘스트 실패")
            }
        })
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

    data class RentInfo(val requesttype: String, val row: List<Book> )
    data class Book(val menu: String, val allergyInfo : String)
}
