package com.example.apppedidos.frontend.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apppedidos.R

class DetallePedidoAdapter(private val detalles: List<Map<String, Any>>) :
    RecyclerView.Adapter<DetallePedidoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.tvNombreCombo)
        val cantidad: TextView = view.findViewById(R.id.tvCantidad)
        val precio: TextView = view.findViewById(R.id.tvPrecioUnitario)
        val subtotal: TextView = view.findViewById(R.id.tvSubtotal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_detalle_pedido, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = detalles.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val detalle = detalles[position]
        holder.nombre.text = detalle["nombre_combo"]?.toString() ?: ""
        holder.cantidad.text = "Cantidad: ${detalle["cantidad"]?.toString() ?: "0"}"
        holder.precio.text = "Precio: ₡${detalle["precio_unitario"]?.toString() ?: "0"}"
        holder.subtotal.text = "Subtotal: ₡${detalle["subtotal"]?.toString() ?: "0"}"
    }
}