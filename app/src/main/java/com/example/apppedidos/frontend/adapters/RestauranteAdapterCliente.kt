package com.example.apppedidos.frontend.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apppedidos.R
import com.example.apppedidos.frontend.models.Restaurante

class RestauranteAdapterCliente(
    private val restaurantes: List<Restaurante>,
    private val onItemClick: (Restaurante) -> Unit
) : RecyclerView.Adapter<RestauranteAdapterCliente.RestauranteViewHolder>() {

    inner class RestauranteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre: TextView = itemView.findViewById(R.id.tvNombreRestaurante)
        val direccion: TextView = itemView.findViewById(R.id.tvDireccionRestaurante)
        val tipoComida: TextView = itemView.findViewById(R.id.tvTipoComida)

        init {
            itemView.setOnClickListener {
                onItemClick(restaurantes[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestauranteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_restaurantecliente, parent, false)
        return RestauranteViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestauranteViewHolder, position: Int) {
        val restaurante = restaurantes[position]
        holder.nombre.text = restaurante.nombre
        holder.direccion.text = restaurante.direccion
        holder.tipoComida.text = restaurante.tipo_comida
    }

    override fun getItemCount(): Int = restaurantes.size
}