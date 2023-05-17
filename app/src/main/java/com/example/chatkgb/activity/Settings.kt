package com.example.chatkgb.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.example.chatkgb.R
import com.example.chatkgb.model.User
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlin.math.log

class Settings : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    private lateinit var db:FirebaseFirestore
    private var user:FirebaseUser? =null
    lateinit var noms: TextInputLayout
    lateinit var emails: TextInputLayout
    lateinit var pp: ShapeableImageView
    lateinit var btn: MaterialButton
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        noms=findViewById(R.id.Noms)
        emails=findViewById(R.id.Mails)
        pp=findViewById(R.id.pp)
        btn=findViewById(R.id.enrs)
        auth=Firebase.auth
        db=Firebase.firestore
        user=auth.currentUser
        if (user!=null){
            db.collection("users").document(user!!.uid).get().addOnSuccessListener { result ->
                if (result!=null){
                    var usrs = result.toObject(User::class.java)
                    usrs?.let {
                        usrs.uuid = user!!.uid
                        noms.editText?.setText(usrs.nom)
                        emails.editText?.setText(usrs.email)
                    }
                }
            }
        }else{
            Log.d("Settings","pas d'utilisateur")
        }

        val img=registerForActivityResult(ActivityResultContracts.GetContent()){
            it?.let {
                pp.setImageURI(it)
            }
        }
        pp.setOnClickListener{
            img.launch("image/*")
        }
    }
}