package com.example.apppedidos.frontend.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apppedidos.R
import com.example.apppedidos.frontend.activities.rol_cliente.ComboPedido

class CarritoAdapter(
    private var items: List<ComboPedido>,
    private val onCarritoUpdated: (List<ComboPedido>) -> Unit
) : RecyclerView.Adapter<CarritoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.tvNombreCombo)
        val cantidad: TextView = view.findViewById(R.id.tvCantidad)
        val precio: TextView = view.findViewById(R.id.tvPrecioCombo)
        val btnMas: Button = view.findViewById(R.id.btnMas)
        val btnMenos: Button = view.findViewById(R.id.btnMenos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_carrito, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.nombre.text = item.nombre
        holder.cantidad.text = item.cantidad.toString()
        holder.precio.text = "₡${String.format("%.2f", item.precio * item.cantidad)}"

        holder.btnMas.setOnClickListener {
            val updatedItems = items.toMutableList()
            updatedItems[position] = item.copy(cantidad = item.cantidad + 1)
            items = updatedItems
            notifyItemChanged(position)
            onCarritoUpdated(items)
        }

        holder.btnMenos.setOnClickListener {
            if (item.cantidad > 1) {
                val updatedItems = items.toMutableList()
                updatedItems[position] = item.copy(cantidad = item.cantidad - 1)
                items = updatedItems
                notifyItemChanged(position)
                onCarritoUpdated(items)
            }
        }
    }

    override fun getItemCount() = items.size
}