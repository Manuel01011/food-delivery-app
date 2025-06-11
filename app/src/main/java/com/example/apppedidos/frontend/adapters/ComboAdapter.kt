package com.example.apppedidos.frontend.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apppedidos.R
import com.example.apppedidos.frontend.models.Combo

class ComboAdapter(private val combos: List<Combo>) :
    RecyclerView.Adapter<ComboAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.tvNombreCombo)
        val descripcion: TextView = view.findViewById(R.id.tvDescripcionCombo)
        val precio: TextView = view.findViewById(R.id.tvPrecioCombo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_combo, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = combos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val combo = combos[position]
        holder.nombre.text = combo.nombre
        holder.descripcion.text = combo.descripcion
        holder.precio.text = "₡${combo.precio}"
    }
}