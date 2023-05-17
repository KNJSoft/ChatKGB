package com.example.chatkgb.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatkgb.R
import com.example.chatkgb.adapter.Amisrecycleadapter
import com.example.chatkgb.adapter.UserRecycle
import com.example.chatkgb.model.Amis
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var amisrecycleadapter: Amisrecycleadapter
    lateinit var liste_amis: RecyclerView
    lateinit var chat: FloatingActionButton

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        liste_amis=findViewById(R.id.liste_amis)
        chat=findViewById(R.id.chat)
        chat.setOnClickListener{
            Intent(this, Search::class.java).also {
                startActivity(it)
            }

        }
        val listetest= mutableListOf(
            Amis("Joel NG","salut","",145421),
            Amis("Dr KNJ","salut prof","",210122),
            Amis("KNJ Soft","how","",113388),
            Amis("Hack er","lance l'attaque","",149826),
            Amis("Joel NG","salut","",145421),
            Amis("Dr KNJ","salut prof","",210122),
            Amis("KNJ Soft","how","",113388),
            Amis("Hack er","lance l'attaque","",149826),
            Amis("Joel NG","salut","",145421),
            Amis("Dr KNJ","salut prof","",210122),
            Amis("KNJ Soft","how","",113388),
            Amis("Hack er","lance l'attaque","",149826),
        )
        amisrecycleadapter=Amisrecycleadapter()
        amisrecycleadapter.items=listetest
        liste_amis.apply {
            layoutManager=LinearLayoutManager(this@MainActivity)
            adapter=amisrecycleadapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.logout){
            val auth=Firebase.auth
            auth.signOut()
            Intent(this,Authentification::class.java).also {
                startActivity(it)
            }
            finish()
        }
        if (item.itemId==R.id.idsettings){
            Intent(this,Settings::class.java).also {
                startActivity(it)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}