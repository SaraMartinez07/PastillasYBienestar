package com.example.pastillasybienestar

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


class Agregar : AppCompatActivity() {

     lateinit var TextID: EditText
     lateinit var imageView: ImageView
     lateinit var nomMedicamentoEditText: EditText
     lateinit var infMedicamentoEditText: EditText
     lateinit var btnGuardar: Button
     var currentPhotoPath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar)

        TextID = findViewById(R.id.edID)
        imageView = findViewById(R.id.miImageView)
        nomMedicamentoEditText = findViewById(R.id.nomMedicamento)
        infMedicamentoEditText = findViewById(R.id.infMedicamento)
        btnGuardar = findViewById(R.id.btnGuardar)


        btnGuardar.setOnClickListener {
            guardarMedic(it)
        }

        imageView.setOnClickListener {
            checkCameraPermissionAndOpenCamera()
        }

    }

    override fun onResume() {
        super.onResume()
       mostrarDatos()
    }

    fun mostrarDatos(){

        val db = Room.databaseBuilder(applicationContext,
            AppBaseDatos::class.java, "medicamento").allowMainThreadQueries().build()

        val medicamentos = db.medicDao().todosMedic()

        Log.e("DATOS", "Medicamentos: ${medicamentos.size}")

        medicamentos.forEach { x ->
            Log.e("DATOS", "${x.id} ${x.nombre} ${x.descripcion}")
        }
    }

    fun guardarMedic(v:View) {
        val id = TextID.text.toString().toInt()
        val nombre = nomMedicamentoEditText.text.toString()
        val descripcion = infMedicamentoEditText.text.toString()

        TextID.setText("")
        nomMedicamentoEditText.setText("")
        infMedicamentoEditText.setText("")


        val imageBitmap: Bitmap = BitmapFactory.decodeFile(currentPhotoPath)

        val byteArrayOutputStream = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray: ByteArray = byteArrayOutputStream.toByteArray()

        val medicamento = Medic(id, nombre, descripcion, imagenBlob = byteArray)

        val db = Room.databaseBuilder(applicationContext,
            AppBaseDatos::class.java, "medicamento").allowMainThreadQueries()
            //.fallbackToDestructiveMigration()
            .build()

        db.medicDao().agregarMedic(medicamento)

        Snackbar.make(v,"Se Guardo", Snackbar.LENGTH_LONG).show()

        mostrarDatos()

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

                // Obtener la orientación de la imagen desde los metadatos
                val orientation = data?.data?.let { getCameraPhotoOrientation(it) } ?: 0

                // Rotar la imagen según la orientación
                val rotatedBitmap = rotateBitmap(imageBitmap, orientation)

                // Guardar la imagen en el almacenamiento externo y obtener la ruta
                val rutaImagen = saveImageToExternalStorage(rotatedBitmap)

                imageView.setImageBitmap(rotatedBitmap)

                // Actualizar currentPhotoPath con la ruta de la imagen
                currentPhotoPath = rutaImagen
            }
        }

    // Función para obtener la orientación de la imagen
    private fun getCameraPhotoOrientation(imageUri: Uri?): Int {
        val cursor = contentResolver.query(imageUri!!, arrayOf(MediaStore.Images.ImageColumns.ORIENTATION), null, null, null)
        cursor?.let {
            if (it.moveToFirst()) {
                val orientation = it.getInt(0)
                it.close()
                return orientation
            }
        }
        return 0
    }

    // Función para rotar la imagen según la orientación
    private fun rotateBitmap(source: Bitmap?, orientation: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(orientation.toFloat())

        // Ajuste para corregir la orientación horizontal
        if (orientation == 90 || orientation == 270) {
            matrix.postScale(-1f, 1f)
        }

        return Bitmap.createBitmap(source!!, 0, 0, source.width, source.height, matrix, true)
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            takePictureLauncher.launch(takePictureIntent)
        } catch (e: ActivityNotFoundException) {
            // Mostrar estado de error al usuario
        }
    }

    private fun saveImageToExternalStorage(imageBitmap: Bitmap?): String {
        var rutaImagen = ""

        if (imageBitmap != null) {
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val imageFile = File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
            rutaImagen = imageFile.absolutePath

            try {
                val fos = FileOutputStream(imageFile)
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return rutaImagen
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 100
    }
}