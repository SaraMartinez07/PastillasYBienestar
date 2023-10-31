package com.example.pastillasybienestar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room

class AddAlarma : AppCompatActivity() {

    lateinit var recicler: RecyclerView
    lateinit var alarma: ImageView
    lateinit var boton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_alarma)

        recicler = findViewById(R.id.listaMedic)
        alarma = findViewById(R.id.imgAlarma)
        boton = findViewById(R.id.btnGuardarA)
    }

    override fun onResume() {
        super.onResume()
        mostrarDatosA()
    }

    fun mostrarDatosA(){

        val db = Room.databaseBuilder(applicationContext,
            AppBaseDatos::class.java, "alarm").allowMainThreadQueries().build()

        val alarmas = db.alarmaDao().allAlarma()

        Log.e("DATOS", "Alarmas: ${alarmas.size}")

        alarmas.forEach { x ->
            Log.e("DATOS", "${x.idA} ${x.hora} ${x.fecha}")
        }
    }
}