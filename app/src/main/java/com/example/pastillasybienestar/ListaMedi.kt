package com.example.pastillasybienestar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room


class ListaMedi : AppCompatActivity(), AlarmaAdapter.AlarmaClickListener {

    lateinit var recicler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_medi)

        recicler = findViewById(R.id.recAlarma)

        val db = AppBaseDatos.getInstance(this)
        val listaAlarmas = db.getAllAlarmas()

        Log.e("Datos", "${listaAlarmas.size}")

        // Configura el RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recAlarma)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val adapter = AlarmaAdapter(this, listaAlarmas,this)
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        mostrarDatosAlarm()
    }

    fun mostrarDatosAlarm(){

        val db = AppBaseDatos.getInstance(this)

        val listaAlarmas = db.getAllAlarmas()

        Log.e("Datos", "${listaAlarmas.size}")

        // Configura el RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recAlarma)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val adapter = AlarmaAdapter(this, listaAlarmas,this )
        adapter.alarmaClickListener = this // Asigna la actividad como oyente
        recyclerView.adapter = adapter

    }

    override fun onAlarmaSelected(alarma: Alarm) {

    }
}



