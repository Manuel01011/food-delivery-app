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
import com.example.apppedidos.frontend.models.PedidoRestaurante
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PedidosRestauranteAdapter(
    private val context: Context,
    private val pedidos: List<PedidoRestaurante>,
    private val onItemClick: (PedidoRestaurante) -> Unit,
    private val idRestaurante: Int
) : RecyclerView.Adapter<PedidosRestauranteAdapter.PedidoViewHolder>() {

    private val apiService: ApiService = ApiClient.instance

    inner class PedidoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNumeroPedido: TextView = itemView.findViewById(R.id.tvNumeroPedido)
        private val tvFecha: TextView = itemView.findViewById(R.id.tvFecha)
        private val tvEstado: TextView = itemView.findViewById(R.id.tvEstado)
        private val tvTotal: TextView = itemView.findViewById(R.id.tvTotal)
        private val tvCliente: TextView = itemView.findViewById(R.id.tvCliente)
        private val tvItems: TextView = itemView.findViewById(R.id.tvItems)
        private val btnAccion: Button = itemView.findViewById(R.id.btnAccion)

        fun bind(pedido: PedidoRestaurante) {
            tvNumeroPedido.text = context.getString(R.string.pedido_numero, pedido.id_pedido)
            tvFecha.text = pedido.fecha_pedido
            tvEstado.text = pedido.estado
            tvTotal.text = context.getString(R.string.total_format, pedido.total)
            tvCliente.text = pedido.nombre_cliente
            tvItems.text = context.getString(R.string.items_format, pedido.cantidad_items)

            when (pedido.estado) {
                "pendiente" -> {
                    btnAccion.apply {
                        text = context.getString(R.string.preparar_pedido)
                        visibility = View.VISIBLE
                        setOnClickListener {
                            actualizarEstadoPedido(pedido.id_pedido, "en_camino")
                        }
                    }
                }
                else -> btnAccion.visibility = View.GONE
            }

            itemView.setOnClickListener { onItemClick(pedido) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pedido_restaurante, parent, false)
        return PedidoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        holder.bind(pedidos[position])
    }

    override fun getItemCount(): Int = pedidos.size

    private fun actualizarEstadoPedido(idPedido: Int, nuevoEstado: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.actualizarEstadoPedido(
                    idRestaurante,
                    idPedido,
                    mapOf(
                        "estado" to nuevoEstado,
                        "id_restaurante" to idRestaurante.toString()
                    )
                )

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.estado_actualizado),
                            Toast.LENGTH_SHORT
                        ).show()
                        // Buscar el pedido actualizado y notificar
                        pedidos.find { it.id_pedido == idPedido }?.let {
                            onItemClick(it.copy(estado = nuevoEstado))
                        }
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.error_actualizar_estado, response.message()),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.error_conexion, e.message),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}