package com.example.zametki

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zametki.db.DbItem
import com.example.zametki.db.DbManager
import com.example.zametki.db.DbViewAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val dbManager = DbManager(this)
    val dbViewAdapter = DbViewAdapter(ArrayList(),this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    override fun onResume() {
        super.onResume()
        dbManager.openDb()


        var dbList = dbManager.readDbData()

        if (dbList.size!=0) tvNoElements.visibility = View.INVISIBLE
        else tvNoElements.visibility = View.VISIBLE

        fillViewAdapter(dbList)
    }

    override fun onDestroy() {
        super.onDestroy()
        dbManager.closeDB()
    }

    fun onClickNew(view: View) {
        val i = Intent(this,EditActivity::class.java)
        startActivity(i)
    }

    fun init(){
        rcView.layoutManager = LinearLayoutManager(this)
        rcView.adapter = dbViewAdapter
    }

    fun fillViewAdapter(listLtems:List<DbItem>){

        dbViewAdapter.upgradeAdapter(listLtems)

    }




}

