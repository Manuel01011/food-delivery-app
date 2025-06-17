package com.example.apppedidos.frontend.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apppedidos.R
import com.example.apppedidos.frontend.models.CalificacionRepartidor


class CalificacionesRepartidoresAdapter(
    private var lista: List<CalificacionRepartidor>
) : RecyclerView.Adapter<CalificacionesRepartidoresAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre = view.findViewById<TextView>(R.id.tvNombreRepartidor)
        val puntaje = view.findViewById<TextView>(R.id.tvPuntaje)
        val comentario = view.findViewById<TextView>(R.id.tvComentario)
        val queja = view.findViewById<TextView>(R.id.tvQueja)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_calificacion_repartidor, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]
        holder.nombre.text = item.nombre_repartidor
        holder.puntaje.text = "Puntaje: ${item.puntaje_repartidor}"
        holder.comentario.text = "Comentario: ${item.comentario ?: "Sin comentario"}"
        holder.queja.text = if (item.queja) "Con queja" else "Sin queja"
    }

    fun actualizar(listaNueva: List<CalificacionRepartidor>) {
        lista = listaNueva
        notifyDataSetChanged()
    }
}