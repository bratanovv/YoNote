package com.example.zametki.db

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zametki.R

class DbViewAdapter(list:ArrayList<String>): RecyclerView.Adapter<DbViewAdapter.MyHolder>() {

    var listItems = list

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle : TextView = itemView.findViewById(R.id.tvTitle)

        fun setData(title:String){
            tvTitle.text=title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyHolder(inflater.inflate(R.layout.rc_item,parent,false))
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.setData(listItems.get(position))
    }

    fun upgradeAdapter(listItems:List<String>){
        this.listItems.clear()
        this.listItems.addAll(listItems)
        notifyDataSetChanged()
    }
}