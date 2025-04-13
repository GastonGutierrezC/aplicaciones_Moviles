package com.example.geolocalizacion.ViewModels

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.geolocalizacion.DataBase.RecorridoDTO
import com.example.geolocalizacion.DataBase.RouteRepository
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    application: Application,
    private val routeRepository: RouteRepository
) : AndroidViewModel(application) {

    private val _pathPoints = MutableLiveData<MutableList<LatLng>>(mutableListOf())
    val pathPoints: LiveData<MutableList<LatLng>> get() = _pathPoints

    private lateinit var locationCallback: LocationCallback

    fun hasLocationPermission(context: Context): Boolean {
        return ActivityCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    context, Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdates(
        fusedLocationClient: FusedLocationProviderClient,
        looper: Looper
    ) {
        val locationRequest = LocationRequest.create().apply {
            interval = 3000
            fastestInterval = 2000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                for (location in result.locations) {
                    updatePath(location)
                }
            }
        }

        if (hasLocationPermission(getApplication())) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, looper)
        }
    }

    fun stopLocationUpdates(fusedLocationClient: FusedLocationProviderClient) {
        if (::locationCallback.isInitialized) {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }

    private fun updatePath(location: Location) {
        val newPoint = LatLng(location.latitude, location.longitude)
        _pathPoints.value?.add(newPoint)
        _pathPoints.postValue(_pathPoints.value)
    }

    fun guardarRecorrido() {
        val fecha = System.currentTimeMillis().toString()
        val puntos = _pathPoints.value?.joinToString(",") { "${it.latitude},${it.longitude}" } ?: ""
        val puntoInicio = _pathPoints.value?.firstOrNull()?.let { "${it.latitude},${it.longitude}" } ?: ""
        val puntoFinal = _pathPoints.value?.lastOrNull()?.let { "${it.latitude},${it.longitude}" } ?: ""

        val recorrido = RecorridoDTO(
            fecha = fecha,
            puntos = puntos,
            puntoInicio = puntoInicio,
            puntoFinal = puntoFinal
        )

        viewModelScope.launch {
            routeRepository.insertarRecorrido(recorrido)
        }
    }

    fun mostrarRecorridoEnMapa(map: GoogleMap, recorrido: RecorridoDTO) {
        val puntos = recorrido.puntos.split(",")

        if (puntos.size % 2 == 0) {
            val latLngList = mutableListOf<LatLng>()

            for (i in puntos.indices step 2) {
                try {
                    val lat = puntos[i].toDouble()
                    val lng = puntos[i + 1].toDouble()
                    latLngList.add(LatLng(lat, lng))
                } catch (e: NumberFormatException) {
                    Log.e("MapsViewModel", "Coordenada no válida: ${puntos[i]}, ${puntos[i + 1]}")
                }
            }

            if (latLngList.isNotEmpty()) {
                map.addPolyline(
                    PolylineOptions()
                        .addAll(latLngList)
                        .width(8f)
                        .color(android.graphics.Color.RED)
                )
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngList.first(), 17f))
            } else {
                Log.e("MapsViewModel", "No se encontraron puntos válidos.")
            }
        } else {
            Log.e("MapsViewModel", "La cantidad de puntos es inválida.")
        }
    }
}
