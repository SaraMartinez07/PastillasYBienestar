package com.example.pastillasybienestar

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Medic::class],version=1)
abstract class AppBaseDatos : RoomDatabase() {
    abstract fun medicDao(): DAOMedic
}