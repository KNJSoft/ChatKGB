package com.example.chatkgb.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatkgb.R
import com.example.chatkgb.adapter.UserRecycle
import com.example.chatkgb.model.User

class Search : AppCompatActivity() {
    lateinit var liste:RecyclerView
    lateinit var editsearch:EditText
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        liste=findViewById(R.id.liste)
        editsearch=findViewById(R.id.editrech)
        val user = mutableListOf(
            User("knjprod@gmail.com","KNJ Soft",""),
            User("knjprod.py@gmail.com","Dr KNJ",""),
            User("joel@gmail.com","joel Ng",""),

        )
        val userRecycle=UserRecycle()
        liste.apply {
            layoutManager=LinearLayoutManager(this@Search)
            adapter= userRecycle
        }
        userRecycle.items=user
        editsearch.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                userRecycle.filter.filter(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }
}