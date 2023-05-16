package com.example.chatkgb.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatkgb.R
import com.example.chatkgb.activity.Chatact
import com.example.chatkgb.model.Amis
import com.google.android.material.imageview.ShapeableImageView
import java.text.SimpleDateFormat
import java.util.*

class Amisrecycleadapter: RecyclerView.Adapter<Amisrecycleadapter.Viewolder>(){
    var items: MutableList<Amis> = mutableListOf()
        set(value) {
            field=value
            notifyDataSetChanged()
        }
    class Viewolder(itemview: View):RecyclerView.ViewHolder(itemview) {
        val imageamis:ShapeableImageView=itemView.findViewById(R.id.avatar)
        val nomamis:TextView=itemView.findViewById(R.id.name)
        val msgamis:TextView=itemView.findViewById(R.id.msg)
        val heuremsg:TextView=itemView.findViewById(R.id.heure)
        fun bin(amis:Amis){
            nomamis.text=amis.nom
            msgamis.text=amis.lastmeg
            val sdf=SimpleDateFormat("HH:mm", Locale.getDefault())
            heuremsg.text=sdf.format(amis.heure)
            itemView.setOnClickListener{
                Intent(itemView.context, Chatact::class.java).also {
                    it.putExtra("amis",amis.nom)
                    itemView.context.startActivity(it)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewolder {
        val itemview=LayoutInflater.from(parent.context).inflate(R.layout.listes_amis,parent,false)
        return Viewolder(itemview)
    }

    override fun getItemCount()=items.size

    override fun onBindViewHolder(holder: Viewolder, position: Int) {
        val amis=items[position]
        holder.bin(amis)
    }

}