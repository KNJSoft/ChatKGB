package com.example.chatkgb.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.chatkgb.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputLayout

class Settings : AppCompatActivity() {
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