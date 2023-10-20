package com.example.pastillasybienestar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configura la animación
        val anim = AnimationUtils.loadAnimation(this, R.anim.mi_animacion)
        // Puedes personalizar esta vista según tu diseño
        val rootView = findViewById<View>(android.R.id.content)

        // Aplica la animación a la vista principal
        rootView.startAnimation(anim)

        // Luego, establece el diseño de la actividad
        setContentView(R.layout.activity_main)

    }
    fun sendVentanaAgr(view: View){
        val intent = Intent(this, ProgMedi::class.java)
        startActivity(intent)
    }
}