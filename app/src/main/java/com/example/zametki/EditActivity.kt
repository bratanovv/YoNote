package com.example.zametki

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.zametki.db.DbManager
import com.example.zametki.db.IntentConstants
import kotlinx.android.synthetic.main.edit_activity.*
import java.text.SimpleDateFormat
import java.util.*

class EditActivity : AppCompatActivity() {

    var marcked = false    //STAR is on/off
    var locked = false     //LOCK is on/off
    var password = "empty"

    val dbManager = DbManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_activity)
        getIntents()
    }

    override fun onResume() {
        super.onResume()
        dbManager.openDb()
    }

    override fun onDestroy() {
        super.onDestroy()
        dbManager.closeDB()
    }


    fun onClickAddMenue(view: View) {
        menueLayout.visibility = View.VISIBLE;
        fbAddMenue.visibility = View.GONE
        fbSave.visibility= View.GONE

    }

    fun onClickHideMenue(view: View) {

        menueLayout.visibility=View.GONE;
        fbAddMenue.visibility = View.VISIBLE
        fbSave.visibility= View.VISIBLE

    }


    fun onClickStar(view: View) {
        if (!marcked){
            ibStar.setImageResource(R.drawable.ic_star)
            marcked = true
        }
        else{
            marcked=false
            ibStar.setImageResource(R.drawable.ic_star_half)
        }

    }

    fun onClickLock(view: View) {   //locking logix
        if (!locked){
            locked = true
            password="not empty"
            ibLock.setImageResource(R.drawable.ic_lock_close)
        }
        else{
            locked = false
            password ="empty"
            ibLock.setImageResource(R.drawable.ic_lock_open)
        }
    }

    fun onClickSave(view: View){
        var titleText = edName.text.toString()
        val t =titleText.trim()
        val descText = edDesc.text.toString()
        descText.trim()
        var star = 0
        if(marcked) star=1



        if(!t.isEmpty()){
          dbManager.insertToDb(titleText, descText, star, password,getDate())
        }
    }

    fun getDate():String{
        val calendar = Calendar.getInstance()
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
        val formattedDate: String = formatter.format(calendar.time)
        return formattedDate
    }
    //andr

    fun getIntents(){
        val i = intent

        if(i!=null){

            if(i.getStringExtra(IntentConstants.I_TITLE_KEY)!= null){
                edName.setText(i.getStringExtra(IntentConstants.I_TITLE_KEY))
                edDesc.setText(i.getStringExtra(IntentConstants.I_CONTENT_KEY))

                if(i.getIntExtra(IntentConstants.I_STAR_KEY,-1)>0){
                    ibStar.setImageResource(R.drawable.ic_star)
                    marcked = true
                }
                if(!i.getStringExtra(IntentConstants.I_PASS_KEY).equals("empty")){
                    locked = true
                    password = "not empty"
                    ibLock.setImageResource(R.drawable.ic_lock_close)
                }

            }
        }
    }

}