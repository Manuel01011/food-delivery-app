package com.example.apppedidos.frontend.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apppedidos.R
import com.example.apppedidos.frontend.activities.rol_cliente.CalificarPedidoActivity
import com.example.apppedidos.frontend.models.PedidoCliente

class PedidosClienteAdapter(
    private val pedidos: List<PedidoCliente>,
    private val idCliente: Int
) : RecyclerView.Adapter<PedidosClienteAdapter.PedidoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pedido_cliente, parent, false)
        return PedidoViewHolder(view, idCliente)
    }

    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        val pedido = pedidos[position]
        holder.bind(pedido)
    }

    override fun getItemCount() = pedidos.size

    class PedidoViewHolder(
        itemView: View,
        private val idCliente: Int // Recibe el idCliente como parámetro
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(pedido: PedidoCliente) {
            itemView.apply {
                findViewById<TextView>(R.id.tvRestaurante).text = pedido.restaurante
                findViewById<TextView>(R.id.tvFecha).text = pedido.fecha_pedido
                findViewById<TextView>(R.id.tvEstado).text = pedido.estado
                findViewById<TextView>(R.id.tvTotal).text = "Total: ${pedido.total}"

                val tvRepartidor = findViewById<TextView>(R.id.tvRepartidor)
                if (pedido.nombre_repartidor != null) {
                    tvRepartidor.text = "Repartidor: ${pedido.nombre_repartidor}"

                    Log.d("PedidosClienteAdapter", "Repartidor ID: ${pedido.id_repartidor}")
                    tvRepartidor.visibility = View.VISIBLE
                } else {
                    Log.d("PedidosClienteAdapter", "Repartidor ID is null")
                    tvRepartidor.visibility = View.GONE
                }

                val btnCalificar = findViewById<Button>(R.id.btnCalificar)
                if (pedido.estado == "entregado" && !pedido.calificado) {
                    btnCalificar.visibility = View.VISIBLE
                    btnCalificar.setOnClickListener {
                        val context = itemView.context
                        val intent = Intent(context, CalificarPedidoActivity::class.java).apply {
                            putExtra("id_pedido", pedido.id_pedido)
                            putExtra("id_cliente", idCliente) // Usa el idCliente recibido
                        }
                        context.startActivity(intent)
                    }
                } else {
                    btnCalificar.visibility = View.GONE
                }
            }
        }
    }
}