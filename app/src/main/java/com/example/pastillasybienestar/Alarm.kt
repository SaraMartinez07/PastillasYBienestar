package com.example.pastillasybienestar

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Alarm (
     @PrimaryKey val idA:Int,
     //val id:Int,
     @ColumnInfo (name = "hora") val hora:TimePickerDialog,
     @ColumnInfo (name = "fecha")val fecha:DatePickerDialog
)