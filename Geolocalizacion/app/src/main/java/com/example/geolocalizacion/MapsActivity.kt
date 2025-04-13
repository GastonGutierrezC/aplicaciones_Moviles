package com.example.geolocalizacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.geolocalizacion.Fragments.RouteFragment

import com.example.geolocalizacion.databinding.ActivityMapsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, RouteFragment())
            .commit()
    }
}
