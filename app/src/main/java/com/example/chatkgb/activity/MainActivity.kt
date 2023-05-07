package com.example.chatkgb.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.RecyclerView
import com.example.chatkgb.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    lateinit var liste_amis: RecyclerView
    lateinit var chat: FloatingActionButton

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        liste_amis=findViewById(R.id.liste_amis)
        chat=findViewById(R.id.chat)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.logout){
            Intent(this,Authentification::class.java).also {
                startActivity(it)
            }
            finish()
        }
        if (item.itemId==R.id.settings){
            /*Intent(this,settings::class.java).also {
                startActivity(it)
            }*/
        }
        return super.onOptionsItemSelected(item)
    }
}