package com.example.pastillasybienestar

import android.Manifest
import android.app.Activity
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class Agregar : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var nomMedicamentoEditText: EditText
    private lateinit var infMedicamentoEditText: EditText
    private lateinit var btnGuardar: Button
    private lateinit var imgVAlarma: ImageView
    private var currentPhotoPath: String? = null
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar)

        imageView = findViewById(R.id.miImageView)
        nomMedicamentoEditText = findViewById(R.id.nomMedicamento)
        infMedicamentoEditText = findViewById(R.id.infMedicamento)
        btnGuardar = findViewById(R.id.btnGuardar)
        imgVAlarma = findViewById(R.id.imgVAlarma)

        sharedPreferences = getPreferences(Context.MODE_PRIVATE)

        sharedPreferences.edit().clear().apply()

        // Recuperar información del medicamento si existe
        val savedNombre = sharedPreferences.getString("NOMBRE_MEDICAMENTO", "")
        val savedInformacion = sharedPreferences.getString("INFORMACION_MEDICAMENTO", "")

        nomMedicamentoEditText.setText("")
        infMedicamentoEditText.setText("")

        nomMedicamentoEditText.setText(savedNombre)
        infMedicamentoEditText.setText(savedInformacion)

        btnGuardar.setOnClickListener {
            // Obtener el nombre e información del medicamento
            val nombreMedicamento = nomMedicamentoEditText.text.toString()
            val informacionMedicamento = infMedicamentoEditText.text.toString()

            // Guardar la información del medicamento en SharedPreferences
            with(sharedPreferences.edit()) {
                putString("NOMBRE_MEDICAMENTO", nombreMedicamento)
                putString("INFORMACION_MEDICAMENTO", informacionMedicamento)
                apply()
            }

            nomMedicamentoEditText.text.clear()
            infMedicamentoEditText.text.clear()

            // Guardar la imagen en el almacenamiento externo
            // Nota: Puedes llamar a la función saveImageToExternalStorage aquí si deseas

            // Mostrar un mensaje emergente (Toast)
            showToast("Datos Guardados Correctamente")
        }

        imageView.setOnClickListener {
            checkCameraPermissionAndOpenCamera()
        }

        imgVAlarma.setOnClickListener {
            openAlarmFormat()
        }
    }

    private fun openAlarmFormat() {
        // Aquí deberías abrir la actividad o diálogo para configurar la alarma
        // Puedes usar un Intent para abrir otra actividad o mostrar un diálogo.
        // Por ejemplo, puedes utilizar un DatePickerDialog y un TimePickerDialog.
        // Dependiendo de tus necesidades, podrías necesitar crear una nueva actividad.

        // Ejemplo para abrir un DatePickerDialog y TimePickerDialog:
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->
                // La fecha ha sido seleccionada, ahora puedes abrir el TimePickerDialog
                val timePickerDialog = TimePickerDialog(
                    this,
                    TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
                        // La hora también ha sido seleccionada
                        // Aquí puedes configurar la alarma con la fecha y hora seleccionadas
                        configureAlarm(selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute)
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    false
                )
                timePickerDialog.show()
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun configureAlarm(year: Int, month: Int, day: Int, hour: Int, minute: Int) {
        // Aquí debes implementar la lógica para configurar la alarma con la fecha y hora seleccionadas
        // Puedes usar AlarmManager y otros componentes según tus necesidades
        // Este es solo un ejemplo básico

        // Por ejemplo, podrías usar AlarmManager para programar una alarma
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, TuBroadcastReceiver::class.java)
        // Configura la lógica según tus necesidades
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)

        // Puedes ajustar la lógica según tus necesidades
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        showToast("Alarma configurada para: $year-$month-$day $hour:$minute")
    }


    private fun checkCameraPermissionAndOpenCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Si el permiso no está concedido, solicítalo.
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_REQUEST_CODE
                )
            } else {
                // Permiso ya concedido, abre la cámara.
                dispatchTakePictureIntent()
            }
        } else {
            // Versiones de Android anteriores a Marshmallow, los permisos se otorgan en la instalación.
            dispatchTakePictureIntent()
        }
    }

    private val takePictureLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val imageBitmap = data?.extras?.get("data") as Bitmap?
                imageView.setImageBitmap(imageBitmap)

                // Guardar la imagen en el almacenamiento externo
                saveImageToExternalStorage(imageBitmap)
            }
        }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            takePictureLauncher.launch(takePictureIntent)
        } catch (e: ActivityNotFoundException) {
            // Mostrar estado de error al usuario
        }
    }

    private fun saveImageToExternalStorage(imageBitmap: Bitmap?) {
        if (imageBitmap != null) {
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val imageFile = File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
            currentPhotoPath = imageFile.absolutePath

            try {
                val fos = FileOutputStream(imageFile)
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 100
    }
}