package com.busango.nonofficialbutsemi

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class RecyclerViewAdapter(private val bookList: MainActivity.RentInfo) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    lateinit var linstener: AdapterView.OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bookList.row.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(bookList.row[position])
    }

    //뷰홀더는 여러개 존재 할 수 있다.
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener {
                val ttkr = "에 대한 검색결과입니다."
                val tt = itemView.book_title01.text
                Toast.makeText(it.context, "$tt$ttkr", Toast.LENGTH_SHORT).show()

                //새 액티비티를 연다(3편에서 작업 예정)
                val intent = Intent(view.context, SearchPage::class.java)
                val googlecom = "https://www.google.com/search?q="
                intent.putExtra("title", "title")
                println(itemView.book_title01.text)
                intent.putExtra("url", googlecom+itemView.book_title01.text)
                view.context.startActivity(intent)
            }
        }

        fun bindItems(data : MainActivity.Book){
            //책 썸네일이미지를 어디서 가져 오지?
            //데이터 표시

            itemView.book_title01.text = data.menu
//            itemView.author01.text = data.allergyInfo
        }


    }


}