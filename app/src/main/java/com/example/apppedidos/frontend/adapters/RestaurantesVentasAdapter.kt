package com.example.apppedidos.frontend.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apppedidos.R
import com.example.apppedidos.frontend.models.RestaurantesVentas

class RestaurantesVentasAdapter(private var items: List<RestaurantesVentas>) : RecyclerView.Adapter<RestaurantesVentasAdapter.ViewHolder>() {

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre = view.findViewById<TextView>(R.id.tvNombreRestaurante)
        val tvVentas = view.findViewById<TextView>(R.id.tvVentasRestaurante)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_restaurante_venta, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurante = items[position]
        holder.tvNombre.text = restaurante.nombreRestaurante
        holder.tvVentas.text = "Ventas: ₡ ${restaurante.totalVendido}"
    }

    fun actualizarLista(nuevaLista: List<RestaurantesVentas>) {
        items = nuevaLista
        notifyDataSetChanged()
    }
}