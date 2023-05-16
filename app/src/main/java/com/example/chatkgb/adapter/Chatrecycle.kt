package com.example.chatkgb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatkgb.R
import com.example.chatkgb.model.Message
import java.text.SimpleDateFormat
import java.util.*

class Chatrecycle: RecyclerView.Adapter<Chatrecycle.ViewHolder>() {

    var items: MutableList<Message> =mutableListOf()
        set(value) {
            field=value
            notifyDataSetChanged()
        }
    inner class ViewHolder(itemview:View):RecyclerView.ViewHolder(itemview){
        val mes:TextView=itemView.findViewById(R.id.idmsg)
        val heur:TextView=itemView.findViewById(R.id.idheure)

        fun bind(msg: Message){
            mes.text=msg.text
            val sdf= SimpleDateFormat("HH:mm", Locale.getDefault())
            heur.text=sdf.format(msg.hr)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemview= LayoutInflater.from(parent.context).inflate(viewType,parent,false)
        return ViewHolder(itemview)
    }

    override fun getItemViewType(position: Int): Int {
        return when(items[position].isrec){
            true->R.layout.item_left
            false->R.layout.item_right
        }
    }

    override fun getItemCount()=items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message=items[position]
        holder.bind(message)
    }
}