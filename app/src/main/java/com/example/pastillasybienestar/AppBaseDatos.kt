package com.example.pastillasybienestar

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Medic::class , Alarm::class], version = 1
    //autoMigrations = [AutoMigration (from = 1, to = 2)]
)
abstract class AppBaseDatos : RoomDatabase() {
    abstract fun medicDao(): DAOMedic
    abstract fun alarmaDao(): DaoAlarma

    companion object {
        private var INSTANCE: AppBaseDatos? = null

        fun getInstance(context: Context): AppBaseDatos {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppBaseDatos::class.java,
                        "app_database"
                    ).build()
                }
                return INSTANCE!!
            }
        }
    }

    // Función para obtener todos los medicamentos desde el DAO
    fun getAllMedics(): List<Medic> {
        return medicDao().todosMedic()
    }

}