package com.example.pastillasybienestar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListaMedi : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_medi)

        // Suponiendo que tienes una instancia de la base de datos
        val database = AppBaseDatos.getInstance(this)

        // Suponiendo que tienes una función en tu DAO para obtener la lista de medicamentos
        val tuListaDeMedicamentos: List<Medic> = database.medicDao().todosMedic()

        // Obtén una instancia de tu RecyclerView desde el diseño
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView2)

        // Configura el LinearLayoutManager para el RecyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // Crea una instancia del adaptador y asígnalo al RecyclerView
        val medicAdapter = MedicAdapter(this, tuListaDeMedicamentos)
        recyclerView.adapter = medicAdapter
    }
}