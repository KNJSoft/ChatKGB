package com.example.chatkgb.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.chatkgb.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Inscription : AppCompatActivity() {
    private lateinit var fbauth: FirebaseAuth
    lateinit var Nom: TextInputEditText
    lateinit var mail: TextInputEditText
    lateinit var password: TextInputEditText
    lateinit var confirmpassword: TextInputEditText
    lateinit var ins: MaterialButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inscription)
        fbauth = Firebase.auth
        Nom=findViewById(R.id.Nom)
        mail=findViewById(R.id.Mail)
        password=findViewById(R.id.Password)
        confirmpassword=findViewById(R.id.confirpassword)
        ins=findViewById(R.id.ins)
        ins.setOnClickListener{
            initerrors()
            val nom=Nom.editableText.toString()
            val email=mail.editableText.toString()
            val pwd=password.editableText.toString()
            val confirmpwd=confirmpassword.editableText.toString()
            if(nom.isEmpty()||email.isEmpty()||pwd.isEmpty()||confirmpwd.isEmpty()){
                if (nom.isEmpty()){
                    Nom.error="Le nom est obligatoire!!!"
                    Nom.isEnabled=true
                }
                if (email.isEmpty()){
                    mail.error="L'email est obligatoire!!!"
                    mail.isEnabled=true
                }
                if (pwd.isEmpty()){
                    password.error="Le mot de passe est obligatoire!!!"
                    password.isEnabled=true
                }
                if (confirmpwd.isEmpty()){
                    confirmpassword.error="Le mot de passe de confirmation est obligatoire!!!"
                    confirmpassword.isEnabled=true
                }
            }else{
                if (pwd!=confirmpwd){
                    confirmpassword.error="Mot de passe incorrect!!!"
                    confirmpassword.isEnabled=true
                }else{
                    fbauth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener{
                        if (it.isSuccessful){
                            Intent(this, MainActivity::class.java).also {
                                startActivity(it)
                            }
                            finish()
                        }else{
                            confirmpassword.error="Erreur survenue!!!"
                            confirmpassword.isEnabled=true
                        }
                    }
                }
            }
        }
    }
    fun initerrors(){
        Nom.isEnabled=false
        mail.isEnabled=false
        password.isEnabled=false
        confirmpassword.isEnabled=false
    }
}