// RecorridoDTO.kt
package com.example.geolocalizacion.DataBase

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "recorridos")
data class RecorridoDTO(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val fecha: String,
    val puntos: String,
    val puntoInicio: String,
    val puntoFinal: String
) : Serializable
