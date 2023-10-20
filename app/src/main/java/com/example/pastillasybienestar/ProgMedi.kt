package com.example.pastillasybienestar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ProgMedi : AppCompatActivity() {

    private lateinit var btnAMedi: Button
    private lateinit var btnAla: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prog_medi)

        btnAMedi= findViewById(R.id.btnAgreMedi)
        btnAla= findViewById(R.id.btnHorario)

        btnAMedi.setOnClickListener{
            val intent=Intent(this,Agregar::class.java)
            startActivity(intent)
        }
        btnAla.setOnClickListener{
            val intent2=Intent(this,AddAlarma::class.java)
            startActivity(intent2)
        }
    }
}