package com.example.geolocalizacion.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.geolocalizacion.R
import com.example.geolocalizacion.Adapters.RouteAdapter
import com.example.geolocalizacion.ViewModels.RouteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RouteFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var routeAdapter: RouteAdapter
    private lateinit var btnAgregarRuta: Button

    private val viewModel: RouteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_recorridos, container, false)

        // Configurar RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewRecorridos)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        routeAdapter = RouteAdapter(emptyList()) { recorrido ->
            val bundle = Bundle().apply {
                putSerializable("recorrido", recorrido)
            }

            val mapsFragment = MapsFragment()
            mapsFragment.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, mapsFragment)
                .addToBackStack(null)
                .commit()
        }

        recyclerView.adapter = routeAdapter

        btnAgregarRuta = view.findViewById(R.id.btnAgregarRuta)
        btnAgregarRuta.setOnClickListener {
            val mapsFragment = MapsFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, mapsFragment)
                .addToBackStack(null)
                .commit()
        }

        viewModel.recorridos.observe(viewLifecycleOwner) { recorridos ->
            routeAdapter.actualizarRecorridos(recorridos)

            if (recorridos.isEmpty()) {
                Toast.makeText(requireContext(), "No hay recorridos registrados", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.cargarRecorridos()

        return view
    }
}

