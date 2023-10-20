package com.example.pastillasybienestar

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DAOMedic {
    @Query("Select * from medic")
   fun todosMedic(): List<Medic>
    @Insert
   fun agregarMedic(vararg medicamento:Medic)
}