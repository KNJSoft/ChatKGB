package com.example.chatkgb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatkgb.R
import com.example.chatkgb.model.User

class UserRecycle: RecyclerView.Adapter<UserRecycle.Viewolder>(),Filterable {
    var items: MutableList<User> = mutableListOf()
        set(value) {
            field=value
            listefiltrer=value
            notifyDataSetChanged()
        }
    inner class Viewolder(itemview:View) : RecyclerView.ViewHolder(itemview){
        val nomrech:TextView=itemView.findViewById(R.id.idliste)
        val amisrech:TextView=itemView.findViewById(R.id.idamis)

        fun bind(user: User) {
            nomrech.text=user.nom[0].toString().uppercase()
            amisrech.text=user.nom

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewolder {
        val itemview= LayoutInflater.from(parent.context).inflate(R.layout.liste,parent,false)
        return Viewolder(itemview)
    }

    override fun getItemCount()=listefiltrer.size

    override fun onBindViewHolder(holder: Viewolder, position: Int) {
        val usr=listefiltrer[position]
        holder.bind(usr)
    }
    private var listefiltrer: MutableList<User> = mutableListOf()
    override fun getFilter(): Filter {
        return object  : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val charrech=p0.toString()
                if (charrech.isEmpty()){
                    listefiltrer=items
                }else{
                    val resultlist=items.filter { it.nom.lowercase().contains(charrech.lowercase()) }
                    listefiltrer=resultlist as MutableList<User>
                }
                val filterresul=FilterResults()
                filterresul.values=listefiltrer
                return filterresul
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                listefiltrer=p1?.values as MutableList<User>
                notifyDataSetChanged()
            }
        }
    }
}