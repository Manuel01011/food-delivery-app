package com.example.apppedidos.frontend.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apppedidos.databinding.ItemRepartidorBinding
import com.example.apppedidos.frontend.models.Repartidor

class RepartidorAdapter(
    private val repartidores: List<Repartidor>,
    private val onItemClick: (Repartidor) -> Unit
) : RecyclerView.Adapter<RepartidorAdapter.RepartidorViewHolder>() {

    inner class RepartidorViewHolder(private val binding: ItemRepartidorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(repartidor: Repartidor) {
            binding.apply {
                tvNombre.text = repartidor.nombre
                tvCedula.text = "Cédula: ${repartidor.cedula}"
                tvEstado.text = "Estado: ${repartidor.estado.replaceFirstChar { it.uppercase() }}"
                tvAmonestaciones.text = "Amonestaciones: ${repartidor.amonestaciones}"

                root.setOnClickListener { onItemClick(repartidor) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepartidorViewHolder {
        val binding = ItemRepartidorBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RepartidorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepartidorViewHolder, position: Int) {
        holder.bind(repartidores[position])
    }

    override fun getItemCount() = repartidores.size
}