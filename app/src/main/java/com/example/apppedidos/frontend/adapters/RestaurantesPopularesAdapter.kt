package com.example.apppedidos.frontend.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apppedidos.R
import com.example.apppedidos.frontend.models.RestaurantePopular

class RestaurantesPopularesAdapter(
    private var restaurantes: List<RestaurantePopular>
) : RecyclerView.Adapter<RestaurantesPopularesAdapter.RestauranteViewHolder>() {

    inner class RestauranteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNombre: TextView = itemView.findViewById(R.id.tvNombreRestaurante)
        private val tvTipoComida: TextView = itemView.findViewById(R.id.tvTipoComida)
        private val tvTotalPedidos: TextView = itemView.findViewById(R.id.tvTotalPedidos)
        private val tvRanking: TextView = itemView.findViewById(R.id.tvRanking)

        fun bind(restaurante: RestaurantePopular) {
            tvNombre.text = restaurante.nombre
            tvTipoComida.text = restaurante.tipo_comida
            tvTotalPedidos.text = "Pedidos: ${restaurante.total_pedidos}"
            tvRanking.text = "#${restaurante.ranking}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestauranteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_restaurante_popular, parent, false)
        return RestauranteViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestauranteViewHolder, position: Int) {
        holder.bind(restaurantes[position])
    }

    override fun getItemCount(): Int = restaurantes.size

    fun actualizarLista(nuevaLista: List<RestaurantePopular>) {
        restaurantes = nuevaLista
        notifyDataSetChanged()
    }
}