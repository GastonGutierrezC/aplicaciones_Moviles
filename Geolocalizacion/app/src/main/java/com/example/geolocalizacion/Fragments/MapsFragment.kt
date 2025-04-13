package com.example.geolocalizacion.Fragments


import android.Manifest
import android.content.pm.PackageManager

import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.geolocalizacion.ViewModels.MapsViewModel
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.example.geolocalizacion.databinding.FragmentMapBinding
import androidx.fragment.app.viewModels
import com.example.geolocalizacion.R
import com.example.geolocalizacion.DataBase.RecorridoDTO
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentMapBinding
    private lateinit var map: GoogleMap

    @Inject
    lateinit var fusedLocationClient: FusedLocationProviderClient

    private val viewModel: MapsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.map)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnTerminarRecorrido.setOnClickListener {
            viewModel.guardarRecorrido()
            parentFragmentManager.popBackStack()
        }

        binding.btnVerListaRecorridos.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, RouteFragment())
                .addToBackStack(null)
                .commit()
        }

        viewModel.pathPoints.observe(viewLifecycleOwner) { points ->
            if (points.size >= 2) {
                map.clear()
                map.addPolyline(
                    PolylineOptions()
                        .addAll(points)
                        .width(8f)
                        .color(android.graphics.Color.BLUE)
                )
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(points.last(), 17f))
            } else if (points.size == 1) {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(points.first(), 17f))
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1
            )
            return
        }

        map.isMyLocationEnabled = true

        val recorrido = arguments?.getSerializable("recorrido") as? RecorridoDTO
        if (recorrido != null) {
            viewModel.mostrarRecorridoEnMapa(map, recorrido)
        } else {
            viewModel.startLocationUpdates(fusedLocationClient, requireActivity().mainLooper)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.stopLocationUpdates(fusedLocationClient)
    }
}
