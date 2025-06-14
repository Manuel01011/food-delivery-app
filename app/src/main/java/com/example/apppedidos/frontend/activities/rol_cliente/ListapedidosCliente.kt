package com.example.apppedidos.frontend.activities.rol_cliente

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apppedidos.R
import com.example.apppedidos.frontend.adapters.PedidosClienteAdapter
import com.example.apppedidos.frontend.api.ApiClient
import com.example.apppedidos.frontend.api.ApiService
import com.example.apppedidos.frontend.models.PedidoCliente
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListapedidosCliente : AppCompatActivity() {
    private lateinit var apiService: ApiService
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PedidosClienteAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_pedidos_cliente)

        // Inicializar vistas
        recyclerView = findViewById(R.id.recyclerViewPedidos)
        progressBar = findViewById(R.id.progressBar)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Configurar Retrofit
        apiService = ApiClient.instance

        // Obtener el ID del cliente desde el intent
        val idCliente = intent.getIntExtra("id_usuario", -1)
        if (idCliente == -1) {
            Toast.makeText(this, "ID de cliente no válido", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Cargar pedidos
        cargarPedidos(idCliente)
    }

    private fun cargarPedidos(idCliente: Int, estado: String? = null) {
        progressBar.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.listarPedidosCliente(idCliente, estado)

                withContext(Dispatchers.Main) {  // Aquí está la corrección
                    progressBar.visibility = View.GONE

                    if (response.isSuccessful) {
                        val body = response.body()
                        val success = body?.get("success") as? Boolean ?: false

                        if (success) {
                            val pedidosJson = body?.get("pedidos") as? List<Map<String, Any>> ?: emptyList()
                            val pedidos = pedidosJson.map { json ->
                                PedidoCliente(
                                    id_pedido = (json["id_pedido"] as Number).toInt(),
                                    fecha_pedido = json["fecha_pedido"] as String,
                                    estado = json["estado"] as String,
                                    total = (json["total"] as Number).toDouble(),
                                    restaurante = json["restaurante"] as String,
                                    tipo_comida = json["tipo_comida"] as String,
                                    id_repartidor = (json["id_repartidor"] as? Number)?.toInt(),
                                    nombre_repartidor = json["nombre_repartidor"] as? String,
                                    calificado = (json["calificado"] as? Number)?.toInt() == 1
                                )
                            }

                            adapter = PedidosClienteAdapter(pedidos)
                            recyclerView.adapter = adapter
                        } else {
                            Toast.makeText(
                                this@ListapedidosCliente,
                                "Error al cargar pedidos",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@ListapedidosCliente,
                            "Error en la respuesta del servidor",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {  // Y aquí también
                    progressBar.visibility = View.GONE
                    Toast.makeText(
                        this@ListapedidosCliente,
                        "Error: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}