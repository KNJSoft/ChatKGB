package com.example.chatkgb.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.chatkgb.R

class AccuielActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accuiel)
        Handler(Looper.getMainLooper()).postDelayed({
            Intent(this,Authentification::class.java).also {
                startActivity(it)
            }
            finish()
        },3000)
    }
}