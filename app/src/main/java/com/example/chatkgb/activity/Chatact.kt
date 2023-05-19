package com.example.chatkgb.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager

import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatkgb.R
import com.example.chatkgb.adapter.Chatrecycle
import com.example.chatkgb.model.Amis
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.chatkgb.model.Message
import com.example.chatkgb.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class Chatact : AppCompatActivity() {
    lateinit var send:FloatingActionButton
    lateinit var edit:EditText
    lateinit var recycleviewchat:RecyclerView
    lateinit var chatrecycle:Chatrecycle
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var user: FirebaseUser? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatact)
        auth= Firebase.auth
        db= Firebase.firestore
        user=auth.currentUser
        send=findViewById(R.id.send)
        edit=findViewById(R.id.edit)
        recycleviewchat=findViewById(R.id.recycleviewchat)
        chatrecycle= Chatrecycle()
        val userid=intent.getStringExtra("amis")!!
        db.collection("users").document(userid).get().addOnSuccessListener {result ->
            if (result!=null){
                var usrs = result.toObject(User::class.java)
                usrs?.let {
                    usrs.uuid = userid
                    setuser(usrs)
                }
            }
        }.addOnFailureListener {
            Log.e("Chat", "Erreur", it)
        }

    }

    private fun setuser(usrs: User) {
        supportActionBar?.title=usrs.nom ?:"ChatKGB"

        /*val mes= mutableListOf(
            Message("Joel NG","Dr KNJ","salut lopm",87654333,false),
            Message("Joel NG","Dr KNJ","salut lmp",8767854333,false),
            Message("Joel NG","KNJ Soft","salut lo",854333,true),
            Message("Joel NG","Dr KNJ","salut nkj",87689054333,false),
            Message("Joel NG","Hack er","salut knj",876540987333,true),
        )*/
        val mess= mutableListOf<Message>()
        val layoutmanager=LinearLayoutManager(this)
        recycleviewchat.apply {
            layoutManager=LinearLayoutManager(this@Chatact)
            adapter=chatrecycle
        }
        send.setOnClickListener{
            //envoyer le msg
            val msg=edit.text.toString()
            if(msg.isNotEmpty()){
                val msg=Message(
                    sender=user!!.uid,
                    rec=usrs.uuid,
                    text=msg,
                    hr=System.currentTimeMillis(),
                    isrec=false
                )
                //chatrecycle.notifyDataSetChanged()
                edit.setText("")
                //fermer le clavier
                val input=getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                input.hideSoftInputFromWindow(edit.windowToken,0)
                db.collection("messages").add(msg).addOnSuccessListener {
                    recycleviewchat.scrollToPosition(mess.size-1)
                }.addOnFailureListener{
                    Log.e("Chat","erreur",it)
                }
                val amis=Amis("",usrs.nom,msg.text,System.currentTimeMillis(),usrs.image?: "")
                db.collection("users").document(user!!.uid).collection("amis").document(usrs.uuid).set(amis).addOnSuccessListener {
                    Log.d("Chat","amis ajouter avec succes")
                }.addOnFailureListener{
                    Log.e("Chat","Erreur d'ajout",it)
                }
            }
        }



        val send=db.collection("messages")
            .whereEqualTo("sender",user!!.uid)
            .whereEqualTo("rec",usrs.uuid)
            .orderBy("hr",Query.Direction.ASCENDING)
        val receive=db.collection("messages")
            .whereEqualTo("sender",usrs.uuid)
            .whereEqualTo("rec",user!!.uid)
            .orderBy("hr",Query.Direction.ASCENDING)
        send.addSnapshotListener{snap,except ->
            if (except !=null){
                Log.e("Chat","erreur",except)
                return@addSnapshotListener
            }
            for (doc in snap!!.documents){
                var mes=doc.toObject(Message::class.java)
                mes?.let {
                    mes.isrec=false
                    if (!mess.contains(mes)){
                        mess.add(mes)
                    }
                }
            }

            if (mess.isNotEmpty()){
                chatrecycle.items=mess.sortedBy { it.hr } as MutableList<Message>
                recycleviewchat.scrollToPosition(mess.size-1)
            }
        }
        receive.addSnapshotListener { snap, except ->
            if (except != null) {
                Log.e("Chat", "erreur", except)
                return@addSnapshotListener
            }
            for (doc in snap!!.documents) {
                var mes = doc.toObject(Message::class.java)
                mes?.let {
                    mes.isrec = true
                    if (!mess.contains(mes)) {
                        mess.add(mes)
                    }
                }
            }
            if (mess.isNotEmpty()){
                chatrecycle.items=mess.sortedBy { it.hr } as MutableList<Message>
                recycleviewchat.scrollToPosition(mess.size-1)
            }
        }
    }
}