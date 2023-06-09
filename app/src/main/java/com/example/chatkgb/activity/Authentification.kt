package com.example.chatkgb.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.chatkgb.Chat
import com.example.chatkgb.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Authentification : AppCompatActivity() {
    private lateinit var fbauth: FirebaseAuth
    lateinit var inscription: TextView
    lateinit var emailauth: TextInputEditText
    lateinit var passwordauth:TextInputEditText
    lateinit var auth: MaterialButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentification)
        fbauth = Firebase.auth
        inscription=findViewById(R.id.inscription)
        emailauth=findViewById(R.id.emailauth)
        passwordauth=findViewById(R.id.passwordauth)
        auth=findViewById(R.id.auth)

    }

    override fun onStart() {
        super.onStart()
        inscription.setOnClickListener{
            Intent(this,Inscription::class.java).also{
                startActivity(it)
            }
        }
        auth.setOnClickListener{
            val email=emailauth.editableText.toString()
            val password=passwordauth.editableText.toString()

            passwordauth.isEnabled=false
            emailauth.isEnabled=false

            if (email.isEmpty() || password.isEmpty()){
                if(password.isEmpty()){
                    passwordauth.error="Le mot de passe est obligatoire!!!"
                    passwordauth.isEnabled=true
                }
                if(email.isEmpty()){
                    emailauth.error="L'email est obligatoire!!!"
                    emailauth.isEnabled=true
                }
            }else{
                //sigin(email,password)
                fbauth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
                    if (it.isSuccessful){
                        Intent(this, MainActivity::class.java).also {
                            startActivity(it)
                        }
                        finish()
                    }else{
                        passwordauth.error="Authentification echoué!!!"
                        emailauth.isEnabled=true
                        passwordauth.isEnabled=true
                    }
                }
            }

        }
    }
    /*fun sigin(email:String,password:String){
        Log.d("signin","signin")
        if (email=="knjprod@gmail.com" && password=="azerty") {

        }
    }*/
}