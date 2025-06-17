package com.example.apppedidos.frontend.activities.estadisticas
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apppedidos.R
import com.example.apppedidos.frontend.adapters.PedidosClienteesAdapter
import com.example.apppedidos.frontend.api.ApiClient
import com.example.apppedidos.frontend.models.ClienteTop
import com.example.apppedidos.frontend.models.PedidoClientee
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClientesYpedidos : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var txtTopCliente: TextView
    private val pedidos = mutableListOf<PedidoClientee>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clientes_ypedidos)

        recycler = findViewById(R.id.recyclerPedidos)
        txtTopCliente = findViewById(R.id.txtClienteTop)

        recycler.layoutManager = LinearLayoutManager(this)

        obtenerReporte()
    }

    private fun obtenerReporte() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiClient.instance.obtenerClientesYPedidos()
                if (response.isSuccessful && response.body() != null) {
                    val data = response.body()!!

                    val pedidosJson = data["pedidos_por_cliente"] as ArrayList<*>
                    val topJson = data["cliente_top"] as Map<*, *>

                    pedidos.clear()
                    pedidosJson.forEach {
                        val obj = it as Map<*, *>
                        pedidos.add(
                            PedidoClientee(
                                id_usuario = (obj["id_usuario"] as Double).toInt(),
                                nombre_cliente = obj["nombre_cliente"] as String,
                                correo_cliente = obj["correo_cliente"] as String,
                                id_pedido = (obj["id_pedido"] as Double).toInt(),
                                fecha_pedido = obj["fecha_pedido"] as String,
                                total = obj["total"] as Double
                            )
                        )
                    }

                    val clienteTop = ClienteTop(
                        id_usuario = (topJson["id_usuario"] as Double).toInt(),
                        nombre_cliente = topJson["nombre_cliente"] as String,
                        correo_cliente = topJson["correo_cliente"] as String,
                        cantidad_pedidos = (topJson["cantidad_pedidos"] as Double).toInt()
                    )

                    runOnUiThread {
                        txtTopCliente.text = "Top Cliente: ${clienteTop.nombre_cliente} (${clienteTop.cantidad_pedidos} pedidos)"
                        recycler.adapter = PedidosClienteesAdapter(pedidos)
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@ClientesYpedidos, "Error al cargar reporte", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this@ClientesYpedidos, "Excepción: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}