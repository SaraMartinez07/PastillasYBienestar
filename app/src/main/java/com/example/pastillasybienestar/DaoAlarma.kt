package com.example.pastillasybienestar

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DaoAlarma {
    @Query("Select * from alarm")
    fun allAlarma(): List<Alarm>
    @Insert
    fun agregarAlarm(vararg alarm: Alarm)
}