package com.example.pastillasybienestar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
    fun sendVentanaAgr(view: View){
        val intent = Intent(this, Agregar::class.java)
        startActivity(intent)
    }
}