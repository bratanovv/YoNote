package com.example.zametki

//import android.R
import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zametki.db.DbItem
import com.example.zametki.db.DbManager
import com.example.zametki.db.DbViewAdapter
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    val dbManager = DbManager(this)
    val dbViewAdapter = DbViewAdapter(ArrayList(), this)
    var marcked = false
    var dbList = ArrayList<DbItem>()
    private var job : Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        initSearchView()
    }

    override fun onResume() {
        super.onResume()
        dbManager.openDb()
        tvAppTitle.visibility = View.VISIBLE
        isEmptyDb(dbList)

        fillViewAdapter("",false)
    }

    override fun onDestroy() {
        super.onDestroy()
        dbManager.closeDB()
    }

    fun isEmptyDb(list: List<DbItem>) {
        if (list.size != 0) tvNoElements.visibility = View.INVISIBLE
        else tvNoElements.visibility = View.VISIBLE
    }

    fun onClickNew(view: View) {
        val i = Intent(this, EditActivity::class.java)
        startActivity(i)
    }

    fun init() {
        rcView.layoutManager = LinearLayoutManager(this)
        val swipeHelper = getSwipeMg()
        swipeHelper.attachToRecyclerView(rcView)
        rcView.adapter = dbViewAdapter

        rcView.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                    fbNew.show();
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 || dy < 0 && fbNew.isShown())
                    fbNew.hide();
            }
        })

    }

    private fun initSearchView() {
        searchView.setOnCloseListener(object : SearchView.OnCloseListener{
            override fun onClose(): Boolean {
                tvAppTitle.visibility = View.VISIBLE
                return false
            }


        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                fillViewAdapter(p0!!,marcked)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                tvAppTitle.visibility = View.INVISIBLE
                fillViewAdapter(p0!!,marcked)
                return true
            }
        })
    }

    fun fillViewAdapter(str: String, marcked: Boolean) {
        job?.cancel()
        job = CoroutineScope(Dispatchers.Main).launch {
            dbList = dbManager.readDbData(str, marcked)
            isEmptyDb(dbList)
            dbViewAdapter.upgradeAdapter(dbList)
        }

    }
    fun fillViewAdapter(listItems: List<DbItem>) {
        isEmptyDb(listItems)
        dbViewAdapter.upgradeAdapter(listItems)

    }

    private fun getSwipeMg(): ItemTouchHelper {
        return ItemTouchHelper(object : ItemTouchHelper.
        SimpleCallback(0, ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                dbViewAdapter.removeItem(viewHolder.adapterPosition, dbManager)
                dbList.removeAt(direction)
                isEmptyDb(dbList)
                Toast.makeText(this@MainActivity, "Заметка удалена", Toast.LENGTH_SHORT).show()
            }

            // swipe decor
            val backgroundCol = ContextCompat.getColor(
                this@MainActivity,
                R.color.colourForRV
            )
            val icoCol = ContextCompat.getColor(
                this@MainActivity,
                R.color.colourForRed
            )

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addBackgroundColor(icoCol)
                    .addActionIcon(R.drawable.ic_delete)
                    // .setActionIconTint(backgroundCol)
                    .addCornerRadius(1, 3)
                    .addPadding(1, 3F, 4F, 3F)
                    .create()
                    .decorate()
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
            // swipe decor
        })
    }


    fun onClickStarFilter(view: View) {
        if (!marcked) {
            ibStarFilter.setImageResource(R.drawable.ic_star)
            marcked = true
            val corrDbList = ArrayList<DbItem>()
            for (item: DbItem in dbList) {
                if (item.star == 1)
                    corrDbList.add(item)
            }
            fillViewAdapter(corrDbList)
        } else {
            marcked = false
            ibStarFilter.setImageResource(R.drawable.ic_star_half)
            fillViewAdapter(dbList)
        }

    }

}

