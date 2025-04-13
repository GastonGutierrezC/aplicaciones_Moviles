package com.example.geolocalizacion.ViewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.geolocalizacion.DataBase.RecorridoDTO
import com.example.geolocalizacion.DataBase.RouteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RouteViewModel @Inject constructor(
    application: Application,
    private val routeRepository: RouteRepository
) : AndroidViewModel(application) {

    private val _recorridos = MutableLiveData<List<RecorridoDTO>>()
    val recorridos: LiveData<List<RecorridoDTO>> get() = _recorridos

    fun cargarRecorridos() {
        viewModelScope.launch {
            try {
                val recorridosList = routeRepository.obtenerRecorridos()
                _recorridos.postValue(recorridosList)

                if (recorridosList.isEmpty()) {
                    Log.i("RouteViewModel", "No hay recorridos registrados")
                }
            } catch (e: Exception) {
                Log.e("RouteViewModel", "Error al cargar los recorridos", e)
            }
        }
    }
}
