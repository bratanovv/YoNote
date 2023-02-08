package com.example.zametki

//import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        initSearchView()
    }

    override fun onResume() {
        super.onResume()
        dbManager.openDb()


        var dbList = dbManager.readDbData("")

        isEmptyDb(dbList)

        fillViewAdapter(dbList)
    }

    override fun onDestroy() {
        super.onDestroy()
        dbManager.closeDB()
    }

    fun isEmptyDb(list:List<DbItem>){
        if (list.size!=0) tvNoElements.visibility = View.INVISIBLE
        else tvNoElements.visibility = View.VISIBLE
    }

    fun onClickNew(view: View) {
        val i = Intent(this,EditActivity::class.java)
        startActivity(i)
    }

    fun init(){
        rcView.layoutManager = LinearLayoutManager(this)
        val swipeHelper = getSwipeMg()
        swipeHelper.attachToRecyclerView(rcView)
        rcView.adapter = dbViewAdapter
    }

    private fun initSearchView(){
        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                val dbList = dbManager.readDbData(p0!!)
                fillViewAdapter(dbList)
                return true
            }
        })
    }

    fun fillViewAdapter(listLtems:List<DbItem>){

        dbViewAdapter.upgradeAdapter(listLtems)

    }

    private fun getSwipeMg() : ItemTouchHelper{
        return ItemTouchHelper(object:ItemTouchHelper.
        SimpleCallback(0,ItemTouchHelper.RIGHT){

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                dbViewAdapter.removeItem(viewHolder.adapterPosition,dbManager)
                isEmptyDb(dbManager.readDbData(""))
                Toast.makeText(this@MainActivity,"Заметка удалена", Toast.LENGTH_SHORT).show()
            }
            // swipe decor
//            val backgroundCol =  ContextCompat.getColor(
//                this@MainActivity,
//                R.color.colourForRV)
//            val icoCol =  ContextCompat.getColor(
//                this@MainActivity,
//                R.color.colourForRed)
//            override fun onChildDraw(
//                c: Canvas,
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                dX: Float,
//                dY: Float,
//                actionState: Int,
//                isCurrentlyActive: Boolean
//            ) {
//                RecyclerViewSwipeDecorator.Builder(
//                    c,
//                    recyclerView,
//                    viewHolder,
//                    dX,
//                    dY,
//                    actionState,
//                    isCurrentlyActive
//                )
//                    .addBackgroundColor(icoCol)
//                    .addActionIcon(R.drawable.ic_delete)
//                   // .setActionIconTint(backgroundCol)
//                    .addCornerRadius(1,3)
//                    .addPadding(1, 3F,4F,3F)
//                    .create()
//                    .decorate()
//                super.onChildDraw(
//                    c,
//                    recyclerView,
//                    viewHolder,
//                    dX,
//                    dY,
//                    actionState,
//                    isCurrentlyActive
//                )
//            }
            // swipe decor
        })
    }


}

