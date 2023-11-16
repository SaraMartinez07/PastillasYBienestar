package com.example.pastillasybienestar

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.getSystemService
import com.example.pastillasybienestar.AlarmNotificacion.Companion.Notification_ID
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    val MY_CHANNEL_ID = "1"
    val REQUEST_ID_PERMISSION =23
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("Notificacion", "Entrando en onCreate")
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
            scheduleNotification()
        }

        /*val myNotificationButton = findViewById<Button>(R.id.btnNotification)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()

            myNotificationButton.setOnClickListener{
                scheduleNotification()
            }
        }*/


        //botNoti = findViewById(R.id.buttonNot)

        // Configura la animación
        val anim = AnimationUtils.loadAnimation(this, R.anim.mi_animacion)
        // Puedes personalizar esta vista según tu diseño
        val rootView = findViewById<View>(android.R.id.content)

        // Aplica la animación a la vista principal
        rootView.startAnimation(anim)

        // Luego, establece el diseño de la actividad
        setContentView(R.layout.activity_main)

    }

    @SuppressLint("ScheduleExactAlarm")
    private fun scheduleNotification() {

        Log.d("Notificacion", "Programando notificación")

        // Obtén la instancia de Calendar
        val calendar = Calendar.getInstance()

        // Establece la hora, minuto y segundo deseados
        calendar.set(Calendar.HOUR_OF_DAY, 12) // Hora del día en formato de 24 horas
        calendar.set(Calendar.MINUTE, 30) // Minuto
        calendar.set(Calendar.SECOND, 0) // Segundo

        // Crea un Intent y PendingIntent como antes
        val intent = Intent(applicationContext, AlarmNotificacion::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            Notification_ID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Obtén el servicio de AlarmManager
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Establece la alarma con la nueva hora, minuto y segundo, y repite cada día
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
  private fun createChannel(){
        val channel = NotificationChannel(
            MY_CHANNEL_ID,
            "MyChannel",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Jungkook"
        }
        val notificationManager:NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun sendVentanaAgr(view: View){
        val intent = Intent(this, ProgMedi::class.java)
        startActivity(intent)
    }
}