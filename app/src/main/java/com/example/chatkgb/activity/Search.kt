package com.example.chatkgb.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatkgb.R
import com.example.chatkgb.adapter.UserRecycle
import com.example.chatkgb.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Search : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var user: FirebaseUser? =null
    lateinit var liste:RecyclerView
    lateinit var editsearch:EditText
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        auth= Firebase.auth
        db= Firebase.firestore
        user=auth.currentUser
        liste=findViewById(R.id.liste)
        editsearch=findViewById(R.id.editrech)
        val liste_user = mutableListOf<User>()
        val userRecycle=UserRecycle()
        liste.apply {
            layoutManager=LinearLayoutManager(this@Search)
            adapter= userRecycle
        }
        db.collection("users")
            .whereNotEqualTo("email",user?.email)
            .get().addOnSuccessListener { result ->
            for (doc in result){
                val uuid=doc.id
                val email=doc.getString("email")
                val nom=doc.getString("nom")
                liste_user.add(User(uuid,email?: "",nom?: "",null))
            }
            userRecycle.items=liste_user
        }.addOnFailureListener{
            Log.e("Recherche","erreur")
        }

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