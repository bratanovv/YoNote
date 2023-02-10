package com.example.zametki.db

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zametki.EditActivity
import com.example.zametki.R

class DbViewAdapter(list: ArrayList<DbItem>, context: Context) :
    RecyclerView.Adapter<DbViewAdapter.MyHolder>() {

    var listItems = list
    var contextM = context

    class MyHolder(itemView: View, contextM: Context) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val ivStar: ImageView = itemView.findViewById(R.id.ivStar)
        val ivLock: ImageView = itemView.findViewById(R.id.ivLock)

        val contextV = contextM

        fun setData(item: DbItem) {

            tvTitle.text = item.title
            tvDate.text = item.date.dropLast(3)
            if (item.star == 0)
                ivStar.visibility = View.INVISIBLE
            else
                ivStar.visibility = View.VISIBLE
            if (item.pass.equals("empty"))
                ivLock.visibility = View.INVISIBLE
            else
                ivLock.visibility = View.VISIBLE
            itemView.setOnClickListener {
                val intent = Intent(contextV, EditActivity::class.java).apply {
                    putExtra(IntentConstants.I_TITLE_KEY, item.title)
                    putExtra(IntentConstants.I_CONTENT_KEY, item.content)
                    putExtra(IntentConstants.I_STAR_KEY, item.star)
                    putExtra(
                        IntentConstants.I_PASS_KEY,
                        item.pass
                    )          // лучше бы не сам пароль
                    putExtra(IntentConstants.I_ID_KEY, item.id)
                }
                contextV.startActivity(intent)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyHolder(inflater.inflate(R.layout.rc_item, parent, false), contextM)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.setData(listItems.get(position))
    }

    fun upgradeAdapter(listItems: List<DbItem>) {
        this.listItems.clear()
        this.listItems.addAll(listItems)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int, dbManager: DbManager) {
        dbManager.removeItemFromDb(this.listItems[position].id.toString())
        this.listItems.removeAt(position)
        notifyItemRangeChanged(0, listItems.size)
        notifyItemRemoved(position)
    }


}