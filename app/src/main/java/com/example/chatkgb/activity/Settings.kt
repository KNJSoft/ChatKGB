package com.example.chatkgb.activity

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
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
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.util.*
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
                        setuser(usrs)
                    }
                }
            }
        }else{
            Log.d("Settings","pas d'utilisateur")
        }

        val img=registerForActivityResult(ActivityResultContracts.GetContent()){
            it?.let {
                Glide.with(this).load(it).placeholder(R.drawable.avatar).into(pp)
            }
        }
        pp.setOnClickListener{
            img.launch("image/*")
        }
    }

    private fun setuser(usrs: User) {
        noms.editText?.setText(usrs.nom)
        emails.editText?.setText(usrs.email)
        usrs.image?.let {
            Glide.with(this).load(it).placeholder(R.drawable.avatar).into(pp)
        }
        btn.setOnClickListener{
            noms.isErrorEnabled=false
            var storageRef = Firebase.storage.reference
            val refimg=storageRef.child("images/${usrs.uuid}")
            val bitmap=(pp.drawable as BitmapDrawable).bitmap
            val baos=ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
            val data=baos.toByteArray()
            //mise a jour de l'image dans firebase storage
            val uploadimg=refimg.putBytes(data)
            uploadimg.addOnSuccessListener { taskSnapShot ->
                refimg.downloadUrl.addOnSuccessListener { uri ->
                    val urlimg=uri.toString()
                    usrs.image=urlimg
                    updateinfouser(usrs)
                }
            }
            updateinfouser(usrs)
        }
    }

    private fun updateinfouser(usrs: User) {
        var updateuser= hashMapOf<String,Any>(
            "nom" to noms.editText?.text.toString(),
            "imge" to (usrs.image ?: "")
        )
        db.collection("users").document(usrs.uuid).update(updateuser).addOnSuccessListener {
            Toast.makeText(this,"mise a jour réuissi",Toast.LENGTH_LONG).show()
        }.addOnFailureListener{
            noms.error="Erreur,reessayer !!!"
            noms.isErrorEnabled=true
        }
    }
}