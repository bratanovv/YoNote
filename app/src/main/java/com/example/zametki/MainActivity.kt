package com.example.zametki

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.zametki.db.DbManager


class MainActivity : AppCompatActivity() {

    val dbManager = DbManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onResume() {
        super.onResume()
        dbManager.openDb()
//        val dataList = dbManager.readDbData()
//        val tv= findViewById<TextView>(R.id.tvText)
//        for(item in dataList){
//
//            tv.append(item)
//            tv.append("\n")
//        }
    }
    fun onClickNew(view: View) {
        val i = Intent(this,EditActivity::class.java)
        startActivity(i)
//        val tv= findViewById<TextView>(R.id.tvText)
//        tv.text=""
//        val edt = findViewById<EditText>(R.id.edTitle)
//        val edc = findViewById<EditText>(R.id.edContent)
//        dbManager.insertToDb(edt.text.toString(),edc.text.toString())
//        val dataList = dbManager.readDbData()
//        for(item in dataList){
//            tv.append(item)
//            tv.append("\n")
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dbManager.closeDB()
    }
}