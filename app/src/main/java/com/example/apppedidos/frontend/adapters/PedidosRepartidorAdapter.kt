package com.example.apppedidos.frontend.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.apppedidos.R
import com.example.apppedidos.frontend.api.ApiClient
import com.example.apppedidos.frontend.api.ApiService
import com.example.apppedidos.frontend.models.PedidoRepartidor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PedidosRepartidorAdapter(
    private val context: Context,
    private var pedidos: List<PedidoRepartidor>,
    private val onItemClick: (PedidoRepartidor) -> Unit
) : RecyclerView.Adapter<PedidosRepartidorAdapter.PedidoViewHolder>() {

    private val apiService: ApiService = ApiClient.instance

    inner class PedidoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvRestaurante: TextView = itemView.findViewById(R.id.tvRestaurante)
        private val tvCliente: TextView = itemView.findViewById(R.id.tvCliente)
        private val tvDireccion: TextView = itemView.findViewById(R.id.tvDireccion)
        private val tvEstado: TextView = itemView.findViewById(R.id.tvEstado)
        private val tvItems: TextView = itemView.findViewById(R.id.tvItems)
        private val tvGanancia: TextView = itemView.findViewById(R.id.tvGanancia)
        private val tvTiempo: TextView = itemView.findViewById(R.id.tvTiempo)
        private val btnEntregado: Button = itemView.findViewById(R.id.btnEntregado)

        fun bind(pedido: PedidoRepartidor) {
            tvRestaurante.text = pedido.restaurante
            tvCliente.text = "Cliente: ${pedido.cliente}"
            tvDireccion.text = "Dirección: ${pedido.direccion_entrega}"
            tvEstado.text = pedido.estado_descripcion
            tvItems.text = "Items: ${pedido.cantidad_items}"
            tvGanancia.text = "Ganancia: $${pedido.ganancia_repartidor}"
            tvTiempo.text = "Hace ${pedido.minutos_transcurridos} min"

            // Mostrar botón solo para pedidos en camino
            btnEntregado.visibility = if (pedido.estado == "en_camino") View.VISIBLE else View.GONE
            btnEntregado.setOnClickListener {
                actualizarEstadoPedido(pedido.id_pedido, "entregado")
            }

            itemView.setOnClickListener { onItemClick(pedido) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pedido_repartidor, parent, false)
        return PedidoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        holder.bind(pedidos[position])
    }

    override fun getItemCount(): Int = pedidos.size

    fun actualizarLista(nuevaLista: List<PedidoRepartidor>) {
        pedidos = nuevaLista
        notifyDataSetChanged()
    }

    private fun actualizarEstadoPedido(idPedido: Int, nuevoEstado: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.actualizarEstadoPedidoRepartidor(
                    idPedido,
                    mapOf("estado" to nuevoEstado)
                )

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            context,
                            "Pedido marcado como entregado",
                            Toast.LENGTH_SHORT
                        ).show()
                        // Actualizar la lista
                        pedidos.find { it.id_pedido == idPedido }?.let {
                            onItemClick(it.copy(estado = nuevoEstado))
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Error al actualizar estado: ${response.message()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        "Error de conexión: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}