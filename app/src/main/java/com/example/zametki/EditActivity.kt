package com.example.zametki

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.edit_activity.*

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_activity)

    }
    var flStar = false
    var flLock = false
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
        if (!flStar){
            ibStar.setImageResource(R.drawable.ic_star)
            flStar = true
        }
        else{
            flStar=false
            ibStar.setImageResource(R.drawable.ic_star_half)
        }

    }

    fun onClickLock(view: View) {
        if (!flLock){
            flLock = true
            ibLock.setImageResource(R.drawable.ic_lock_close)
        }
        else{
            flLock = false
            ibLock.setImageResource(R.drawable.ic_lock_open)
        }
    }
}