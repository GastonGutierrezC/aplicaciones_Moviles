package com.example.geolocalizacion.DataBase



import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecorridoDao {

    @Insert
    suspend fun insertarRecorrido(recorrido: RecorridoDTO)

    @Query("SELECT * FROM recorridos")
    suspend fun obtenerRecorridos(): List<RecorridoDTO>
}
