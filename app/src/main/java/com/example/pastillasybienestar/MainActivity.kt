package com.example.pastillasybienestar

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {
    lateinit var botNoti: Button

    companion object {
        const val CHANNEL_ID = "mi_canal_de_notificacion"
        const val notificationId = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        botNoti = findViewById(R.id.buttonNot)

        // Configura la animación
        val anim = AnimationUtils.loadAnimation(this, R.anim.mi_animacion)
        // Puedes personalizar esta vista según tu diseño
        val rootView = findViewById<View>(android.R.id.content)

        // Aplica la animación a la vista principal
        rootView.startAnimation(anim)

        // Luego, establece el diseño de la actividad
       // setContentView(R.layout.activity_main)

        createNotificationChannel()

    }
    fun sendVentanaAgr(view: View){
        val intent = Intent(this, ProgMedi::class.java)
        startActivity(intent)
    }

    fun createNotificationChannel() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Channel Name"
            val descriptionText = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun sendNotification() {
        val textTitle = "Título de la notificación"
        val textContent = "Contenido de la notificación"

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.captura_de_pantalla_2023_09_15_205413)
            .setContentTitle(textTitle)
            .setContentText(textContent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    }

    fun sendNotificationOnClick(view: View) {
        sendNotification()
    }
}