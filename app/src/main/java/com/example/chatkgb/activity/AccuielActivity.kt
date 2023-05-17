package com.example.chatkgb.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.chatkgb.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AccuielActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accuiel)

        Handler(Looper.getMainLooper()).postDelayed({
            val auth=Firebase.auth
            val user=auth.currentUser
            if (user !=null){
                Intent(this,MainActivity::class.java).also {
                    startActivity(it)
                }
            }else{
                Intent(this,Authentification::class.java).also {
                    startActivity(it)
                }
            }

            finish()
        },3000)
    }
}