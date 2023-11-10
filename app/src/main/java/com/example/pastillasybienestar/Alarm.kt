package com.example.pastillasybienestar

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Alarm (
     @PrimaryKey val idA:Long,
     @ColumnInfo (name = "hora") val hora:String,
     @ColumnInfo (name = "fecha")val fecha:String,
     @ColumnInfo(name = "idMedicamento") val idMedicamento: Int
)