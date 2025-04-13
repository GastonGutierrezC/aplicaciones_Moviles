package com.example.geolocalizacion.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.geolocalizacion.DataBase.RecorridoDTO
import com.example.geolocalizacion.R

class RouteAdapter(
    private var recorridos: List<RecorridoDTO>,
    private val onRecorridoClick: (RecorridoDTO) -> Unit
) : RecyclerView.Adapter<RouteAdapter.RecorridoViewHolder>() {

    fun actualizarRecorridos(nuevosRecorridos: List<RecorridoDTO>) {
        this.recorridos = nuevosRecorridos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecorridoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recorrido, parent, false)
        return RecorridoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecorridoViewHolder, position: Int) {
        val recorrido = recorridos[position]
        holder.fechaTextView.text = "Fecha: ${recorrido.fecha}"
        holder.puntoInicioTextView.text = "Inicio: ${recorrido.puntoInicio}"
        holder.puntoFinalTextView.text = "Final: ${recorrido.puntoFinal}"

        holder.verRecorridoButton.setOnClickListener {
            onRecorridoClick(recorrido)
        }
    }

    override fun getItemCount(): Int {
        return recorridos.size
    }

    class RecorridoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fechaTextView: TextView = itemView.findViewById(R.id.textViewFecha)
        val puntoInicioTextView: TextView = itemView.findViewById(R.id.textViewPuntoInicio)
        val puntoFinalTextView: TextView = itemView.findViewById(R.id.textViewPuntoFinal)
        val verRecorridoButton: Button = itemView.findViewById(R.id.btnVerRecorrido) // Botón para mostrar recorrido
    }
}
