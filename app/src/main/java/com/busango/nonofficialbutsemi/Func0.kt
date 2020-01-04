package com.busango.nonofficialbutsemi

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View.*
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.android.synthetic.main.digitalid.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import kotlin.system.exitProcess


class Func0 :Activity() {

    private lateinit var imgview: ImageView

    //TODO: 꼭 앱 죽이기 전에 먼저 반응받아서 죽는 놈들 정리할 것.

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

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        TODO 캡쳐 및 보안 설비
        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        setContentView(R.layout.digitalid)
        val iv = findViewById<ImageView>(R.id.iv)
        do_nav_view.menu.getItem(1).isChecked = true

        val pref0 = getSharedPreferences("docenter_first", MODE_PRIVATE)
        val editor0 = pref0.edit()
        val first0 = pref0.getBoolean("docenter_first", false)

        val regnum = getSharedPreferences("regnum", MODE_PRIVATE)
        val regnumEd = regnum.edit()

        if (!first0)
        {
            Log.d("docenter first Time?", "first")
            id_first.visibility = INVISIBLE
            id_second.visibility = INVISIBLE
            id_third.visibility = INVISIBLE
            id_reg.visibility = VISIBLE
            id_reg.bringToFront()

            btn_reg.setOnClickListener {
                if (edit_regnum.length() == 4){
                    val regnumEt = edit_regnum.text.toString().toInt()
                    regnumEd.putInt("regnum", regnumEt)
                    regnumEd.apply()
                    editor0.putBoolean("docenter_first", true)
                    editor0.apply()
                    Toast.makeText(applicationContext, "로그인하였습니다.", Toast.LENGTH_LONG).show()

                    val intent = Intent(this, Func0::class.java)
                    Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent,null)
                } else {
                    Toast.makeText(applicationContext, "개인부호번호는 4자리입니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
        else
        {
            Log.d("Is first Time?", "not first")
        }

        //Activities
        id_first.bringToFront()

        id_first.setOnClickListener {
            id_first.visibility = INVISIBLE
            id_second.visibility = VISIBLE
            id_third.visibility = INVISIBLE
            id_reg.visibility = GONE
        }

        id_second.setOnClickListener {
            id_second.visibility = INVISIBLE
            id_third.visibility = VISIBLE
            id_first.visibility = INVISIBLE
            id_reg.visibility = GONE
        }

        id_third.setOnClickListener {
            id_third.visibility = INVISIBLE
            id_first.visibility = VISIBLE
            id_second.visibility = INVISIBLE
            id_reg.visibility = GONE
        }

        fetchJson()
        qrgen()
        remregdata()

        do_nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private fun qrgen() {

        val iv = findViewById<ImageView>(R.id.iv)

        val pref1 = getSharedPreferences("regnum", MODE_PRIVATE)
        val regdata = pref1.getInt("regnum", 0).toString()
        id_regnum_debug.text = regdata

        val content = "http://dir$regdata.busanhs.xyz"

        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
            }
        }
        iv.setImageBitmap(bitmap)

    }

    private fun remregdata() {
        remregdata.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("개인부호번호 초기화")
            builder.setMessage("초기화하시겠습니까?")
            builder.setIcon(R.drawable.ic_delete_black_24dp)
            builder.setPositiveButton("초기화"
            ) { dialog, which -> Toast.makeText(applicationContext, "초기화하였습니다. 재시작하여 주십시오.", Toast.LENGTH_LONG).show()

                val pref0 = getSharedPreferences("docenter_first", MODE_PRIVATE)
                val editor0 = pref0.edit()
                editor0.putBoolean("docenter_first", false)
                editor0.apply()
            }

            builder.setNegativeButton("취소"
            ) { dialog, which -> Toast.makeText(applicationContext, "취소하셨습니다.", Toast.LENGTH_LONG).show() }
            val alertDialog = builder.create()
            alertDialog.show()
        }
    }

    private fun fetchJson() {
        println("fetch json start")

        val pref1 = getSharedPreferences("regnum", MODE_PRIVATE)
        val regdata = pref1.getInt("regnum", 0).toString()

        val url1 = "http://dir$regdata.busanhs.xyz"

        val request1 = Request.Builder().url(url1).build()
        val client1 = OkHttpClient()

        client1.newCall(request1).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                println(body)

                try
                {
                    val jarray = JSONArray(body)   // json 어레이
                    for (i in 0 until jarray.length())
                    {
                        val jObject = jarray.getJSONObject(i)  // JSONObject 추출
                        val name = jObject.getString("name")
                        val nowgrade = jObject.getString("nowgrade")
                        val g1cls = jObject.getString("g1cls")
                        val g1num = jObject.getString("g1num")
                        val g2cls = jObject.getString("g2cls")
                        val g2num = jObject.getString("g2num")
                        val g3cls = jObject.getString("g3cls")
                        val g3num = jObject.getString("g3num")
                        val email = jObject.getString("email")
                        val tel = jObject.getString("tel")
                        val birthdate = jObject.getString("birthdate")

                        val imagedir = resources.getIdentifier(nowgrade,"drawable", packageName)

                        id_name.text = name
                        id_g1b.text = "$g1cls 반"
                        id_g1c.text = "$g1num 번"
                        id_g2b.text = "$g2cls 반"
                        id_g2c.text = "$g2num 번"
                        id_g3b.text = "$g3cls 반"
                        id_g3c.text = "$g3num 번"
                        id_email_value.text = "$email"
                        id_tel_value.text = "$tel"
                        id_birth_value.text = "$birthdate"
                        id_school_pic.setImageResource(imagedir)

                    }
                }
                catch (e:JSONException) {
                    e.printStackTrace()
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
    }

    private var time:Long = 0
    override fun onBackPressed() {
        val pref0 = getSharedPreferences("docenter_first", MODE_PRIVATE)
        val first0 = pref0.getBoolean("docenter_first", false)
        if (!first0) {
            finish()
        } else {
            val intent = Intent(this, DoCenter::class.java)
            Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent,null)
        }
    }

}