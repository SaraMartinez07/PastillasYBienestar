package com.example.pastillasybienestar

import android.Manifest
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlarmNotificacion:BroadcastReceiver() {
    companion object{
        const val Notification_ID = 1
    }
    override fun onReceive(context: Context?, p1: Intent?) {
        if (context != null) {
            createSimpleNotification(context)
        }
    }

    fun createSimpleNotification(context: Context){

        println("hola");
        val intent=Intent(context, AddAlarma::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        val pendigIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, flag)

        val notificacion = NotificationCompat.Builder(context, "1")
            .setSmallIcon(android.R.drawable.ic_delete)
            .setContentTitle("Pastillas")
            .setContentText("Alarma")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("HOLIIIIIII RECUERDA TOMAR TU MEDICAMENTO ;)")
            )
            .setContentIntent(pendigIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(Notification_ID, notificacion)
    }
}