package com.example.chatkgb.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager

import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatkgb.R
import com.example.chatkgb.adapter.Chatrecycle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.chatkgb.model.Message

class Chatact : AppCompatActivity() {
    lateinit var send:FloatingActionButton
    lateinit var edit:EditText
    lateinit var recycleviewchat:RecyclerView
    lateinit var chatrecycle:Chatrecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatact)
        send=findViewById(R.id.send)
        edit=findViewById(R.id.edit)
        recycleviewchat=findViewById(R.id.recycleviewchat)
        chatrecycle= Chatrecycle()
        val nom=intent.getStringExtra("amis")
        supportActionBar?.title=nom ?:"ChatKGB"

        val mes= mutableListOf(
            Message("Joel NG","Dr KNJ","salut lopm",87654333,false),
            Message("Joel NG","Dr KNJ","salut lmp",8767854333,false),
            Message("Joel NG","KNJ Soft","salut lo",854333,true),
            Message("Joel NG","Dr KNJ","salut nkj",87689054333,false),
            Message("Joel NG","Hack er","salut knj",876540987333,true),
        )
        send.setOnClickListener{
            //envoyer le msg
            val msg=edit.text.toString()
            if(msg.isNotEmpty()){
                mes.add(Message("Joel NG","Dr KNJ",msg,System.currentTimeMillis(),false))
                chatrecycle.notifyDataSetChanged()
                edit.setText("")
                //fermer le clavier
                val input=getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                input.hideSoftInputFromWindow(edit.windowToken,0)
            }
        }

        val layoutmanager=LinearLayoutManager(this)
        recycleviewchat.apply {
            layoutManager=LinearLayoutManager(this@Chatact)
            adapter=chatrecycle
        }
        chatrecycle.items=mes
    }
}