package com.example.pastillasybienestar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room


class ListaMedi : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_medi)


        val db = AppBaseDatos.getInstance(this)

       val listaMedicamentos = db.getAllMedics()

        Log.e("Datos", "${listaMedicamentos.size}")

        // Configura el RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.listaMedic)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        //val adapter = MedicAdapter(this, listaMedicamentos )
       // recyclerView.adapter = adapter
    }
}



