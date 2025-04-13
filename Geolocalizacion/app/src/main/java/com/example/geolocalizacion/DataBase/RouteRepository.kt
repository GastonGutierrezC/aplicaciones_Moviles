// RouteRepository.kt
package com.example.geolocalizacion.DataBase


import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RouteRepository @Inject constructor(
    private val recorridoDao: RecorridoDao
) {

    suspend fun insertarRecorrido(recorrido: RecorridoDTO) {
        withContext(Dispatchers.IO) {
            recorridoDao.insertarRecorrido(recorrido)
        }
    }

    suspend fun obtenerRecorridos(): List<RecorridoDTO> {
        return withContext(Dispatchers.IO) {
            recorridoDao.obtenerRecorridos()
        }
    }
}
