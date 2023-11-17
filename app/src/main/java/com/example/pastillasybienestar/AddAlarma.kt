package com.example.pastillasybienestar

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Calendar
import androidx.room.Room
import java.text.SimpleDateFormat
import java.util.Locale

 class AddAlarma : AppCompatActivity(), MedicAdapter.MedicClickListener {

    lateinit var recicler: RecyclerView
    lateinit var alarma: ImageView
    lateinit var boton: Button
    var medicamentoSeleccionado: Medic? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_alarma)

        recicler = findViewById(R.id.listaMedic)
        alarma = findViewById(R.id.imgAlarma)
        boton = findViewById(R.id.btnGuardarA)

        alarma.setOnClickListener {
            mostrarDialogoFechaYHora()
        }
        // Agrega un listener al botón de guardar
        boton.setOnClickListener {
            // Verifica si se ha seleccionado un medicamento antes de mostrar el diálogo de fecha y hora
            if (medicamentoSeleccionado != null) {
                mostrarDialogoFechaYHora()
            } else {
                mostrarMensajeAdvertencia("Seleccione un medicamento antes de guardar.")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mostrarDatosA()
    }

    fun mostrarDatosA(){

        val db = AppBaseDatos.getInstance(this)

        val listaMedicamentos = db.getAllMedics()

        Log.e("Datos", "${listaMedicamentos.size}")

        // Configura el RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.listaMedic)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val adapter = MedicAdapter(this, listaMedicamentos,this )
        adapter.medicClickListener = this // Asigna la actividad como oyente
        recyclerView.adapter = adapter
        
    }

    fun mostrarDialogoFechaYHora() {
        val calendar = Calendar.getInstance()

        // Muestra el DatePickerDialog para seleccionar la fecha
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                // Muestra el TimePickerDialog después de seleccionar la fecha
                val timePickerDialog = TimePickerDialog(
                    this,
                    TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                        // Aquí obtienes la fecha y la hora seleccionadas
                        val fechaHora = Calendar.getInstance()
                        fechaHora.set(year, month, dayOfMonth, hourOfDay, minute)

                        // Llama a la función para guardar la alarma en la base de datos
                        guardarAlarmaEnBaseDeDatos(fechaHora)

                        // AQUÍ llama a la función para programar la notificación
                        scheduleNotification(fechaHora)
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                )
                timePickerDialog.show()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }

    fun guardarAlarmaEnBaseDeDatos(fechaHora: Calendar) {
        val db = AppBaseDatos.getInstance(this)

        // Verifica si se ha seleccionado un medicamento
        if (medicamentoSeleccionado != null) {
            // Formatea la fecha y la hora según tus necesidades
            val formatoFechaHora = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val fechaHoraString = formatoFechaHora.format(fechaHora.time)

            // Crea una instancia de la entidad Alarm y guárdala en la base de datos
            val nuevaAlarma = Alarm(
                idA = System.currentTimeMillis(),
                hora = fechaHoraString,
                fecha = fechaHoraString,
                idMedicamento = medicamentoSeleccionado!!.id,
                nomMedicamento = medicamentoSeleccionado!!.nombre
            )

            db.alarmaDao().agregarAlarm(nuevaAlarma)

            // Puedes mostrar un mensaje de éxito, actualizar la interfaz, etc.
            Log.e("Alarma", "Alarma guardada con éxito: $fechaHoraString")
            mostrarMensajeExito("Alarma guardada con éxito: $fechaHoraString")

            // Inicia la actividad ActivityListaMedi
            val intent = Intent(this, ListaMedi::class.java)
            startActivity(intent)

            // Cierra la actividad actual para regresar a la actividad anterior
            finish()
            // AQUI VOY A MANDAR LLAMAR LA ALARMA

            val fechaHoraActual = Calendar.getInstance()
            //scheduleAlarm( AQUI VA LA FEHCA Y LA HORA )
            scheduleNotification(fechaHoraActual)
        } else {
            // Manejar el caso en que no se haya seleccionado ningún medicamento
            Log.e("Error", "No se ha seleccionado ningún medicamento.")
        }
    }

     // Función para mostrar un mensaje utilizando Toast
     private fun mostrarMensajeExito(mensaje: String) {
         Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
     }

     // Función para mostrar un mensaje de advertencia utilizando Toast
     private fun mostrarMensajeAdvertencia(mensaje: String) {
         Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
     }


     override fun onMedicSelected(medic: Medic) {
        Log.e("datitois", medic.id.toString())
        medicamentoSeleccionado = medic
     }

     @SuppressLint("ScheduleExactAlarm")
     private fun scheduleNotification(fechaHora: Calendar) {
         Log.d("Notificacion", "scheduleNotification llamado")

         // Crea un Intent y PendingIntent como antes
         val intent = Intent(applicationContext, AlarmNotificacion::class.java)
         intent.putExtra("medicamento", "Medicamento")
         val pendingIntent = PendingIntent.getBroadcast(
             applicationContext,
             AlarmNotificacion.Notification_ID,
             intent,
             PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
         )

         // Obtén el servicio de AlarmManager
         val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

         // Establece la alarma con la fecha y la hora proporcionadas
         alarmManager.setExact(
             AlarmManager.RTC_WAKEUP,
             fechaHora.timeInMillis,
             pendingIntent
         )
     }
 }