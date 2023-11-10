package com.example.pastillasybienestar

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Medic (
    @PrimaryKey val id:Int,
    @ColumnInfo(name = "nombre") val nombre:String,
    @ColumnInfo(name = "descripcion") val descripcion:String,
    @ColumnInfo(name = "ruta_imagen") val imagenBlob: ByteArray,
    var isSelected: Boolean = false
)