package com.example.zametki

import android.os.Bundle
import android.view.View
import android.widget.Toast
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
    var id = -1;
    val dbManager = DbManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_activity)
        edDesc.isEnabled = true
        edName.isEnabled = true
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
        fbSave.visibility = View.GONE
        edDesc.isEnabled = true
        edName.isEnabled = true

    }

    fun onClickHideMenue(view: View) {

        menueLayout.visibility = View.GONE;
        fbAddMenue.visibility = View.VISIBLE
        fbSave.visibility = View.VISIBLE
        edDesc.isEnabled = false
        edName.isEnabled = false

    }


    fun onClickStar(view: View) {
        if (!marcked) {
            ibStar.setImageResource(R.drawable.ic_star)
            marcked = true
        } else {
            marcked = false
            ibStar.setImageResource(R.drawable.ic_star_half)
        }

    }

    fun onClickLock(view: View) {   //locking logix
        if (!locked) {
            locked = true
            password = "not empty"
            ibLock.setImageResource(R.drawable.ic_lock_close)
        } else {
            locked = false
            password = "empty"
            ibLock.setImageResource(R.drawable.ic_lock_open)
        }
    }

    fun onClickSave(view: View) {
        var titleText = edName.text.toString()
        val t = titleText.trim()
        val descText = edDesc.text.toString()
        descText.trim()
        var star = 0
        if (marcked) star = 1

        if (!t.isEmpty()) {
            if (id == -1)
                dbManager.insertToDb(titleText, descText, star, password, getDate())
            else
                dbManager.updateItemtoDb(
                    titleText,
                    descText,
                    star,
                    password,
                    getDate(),
                    id.toString()
                )

            Toast.makeText(this, "Заметка cохранена", Toast.LENGTH_SHORT).show()
            finish()
        } else Toast.makeText(this, "Заполните название", Toast.LENGTH_SHORT).show()
    }

    fun getDate(): String {
        val calendar = Calendar.getInstance()
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
        val formattedDate: String = formatter.format(calendar.time)
        return formattedDate
    }


    fun getIntents() {
        val i = intent

        if (i != null) {

            if (i.getStringExtra(IntentConstants.I_TITLE_KEY) != null) {
                edName.setText(i.getStringExtra(IntentConstants.I_TITLE_KEY))
                edDesc.setText(i.getStringExtra(IntentConstants.I_CONTENT_KEY))

                menueLayout.visibility = View.GONE
                fbAddMenue.visibility = View.VISIBLE
                fbSave.visibility = View.VISIBLE
                edDesc.isEnabled = false
                edName.isEnabled = false




                id = i.getIntExtra(IntentConstants.I_ID_KEY, -1)

                if (i.getIntExtra(IntentConstants.I_STAR_KEY, -1) > 0) {
                    ibStar.setImageResource(R.drawable.ic_star)
                    marcked = true
                }
                if (!i.getStringExtra(IntentConstants.I_PASS_KEY).equals("empty")) {
                    locked = true
                    password = "not empty"
                    ibLock.setImageResource(R.drawable.ic_lock_close)
                }

            }
        }
    }

    fun onClickDelete(view: View) {

        if (id != -1) dbManager.removeItemFromDb(id.toString())

        Toast.makeText(this, "Заметка удалена", Toast.LENGTH_SHORT).show()
        finish()
    }

}