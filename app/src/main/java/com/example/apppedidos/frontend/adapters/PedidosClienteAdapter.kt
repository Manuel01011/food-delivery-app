package com.example.apppedidos.frontend.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apppedidos.R
import com.example.apppedidos.frontend.models.PedidoCliente

class PedidosClienteAdapter(private val pedidos: List<PedidoCliente>) :
    RecyclerView.Adapter<PedidosClienteAdapter.PedidoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pedido_cliente, parent, false)
        return PedidoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        val pedido = pedidos[position]
        holder.bind(pedido)
    }

    override fun getItemCount() = pedidos.size

    class PedidoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(pedido: PedidoCliente) {
            itemView.apply {
                findViewById<TextView>(R.id.tvRestaurante).text = pedido.restaurante
                findViewById<TextView>(R.id.tvFecha).text = pedido.fecha_pedido
                findViewById<TextView>(R.id.tvEstado).text = pedido.estado
                findViewById<TextView>(R.id.tvTotal).text = "Total: ${pedido.total}"

                // Configurar botón de calificar si el pedido está entregado y no calificado
                val btnCalificar = findViewById<Button>(R.id.btnCalificar)
                if (pedido.estado == "entregado" && !pedido.calificado) {
                    btnCalificar.visibility = View.VISIBLE
                    btnCalificar.setOnClickListener {
                        // Implementar lógica para calificar
                    }
                } else {
                    btnCalificar.visibility = View.GONE
                }
            }
        }
    }
}