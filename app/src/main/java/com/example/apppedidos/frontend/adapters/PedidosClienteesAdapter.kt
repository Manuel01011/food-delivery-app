package com.example.apppedidos.frontend.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apppedidos.R
import com.example.apppedidos.frontend.models.PedidoClientee

class PedidosClienteesAdapter(
    private val lista: List<PedidoClientee>
) : RecyclerView.Adapter<PedidosClienteesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreCliente: TextView = itemView.findViewById(R.id.txtNombreCliente)
        val idPedido: TextView = itemView.findViewById(R.id.txtIdPedido)
        val fechaPedido: TextView = itemView.findViewById(R.id.txtFecha)
        val total: TextView = itemView.findViewById(R.id.txtTotal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pedido_clientee, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pedido = lista[position]
        holder.nombreCliente.text = pedido.nombre_cliente
        holder.idPedido.text = "Pedido #${pedido.id_pedido}"
        holder.fechaPedido.text = pedido.fecha_pedido
        holder.total.text = "₡${pedido.total}"
    }

    override fun getItemCount(): Int = lista.size
}