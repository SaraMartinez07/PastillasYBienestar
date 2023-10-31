package com.example.pastillasybienestar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ListaMedi : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_medi)

        // Obtén la instancia de la base de datos
        val dbHelper = AppBaseDatos.getInstance(this)

        // Obtén la lista de medicamentos desde la base de datos
        val listaMedicamentos = dbHelper.getAllMedics()

        // Configura el RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.listaMedic)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // Crea un adaptador para tu RecyclerView (reemplaza con tu adaptador personalizado)
        val adapter = MedicAdapter(listaMedicamentos)
        recyclerView.adapter = adapter
    }
}



