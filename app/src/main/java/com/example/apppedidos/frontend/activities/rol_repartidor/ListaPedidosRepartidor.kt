package com.example.apppedidos.frontend.activities.rol_repartidor
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apppedidos.MainActivity
import com.example.apppedidos.R
import com.example.apppedidos.frontend.adapters.PedidosRepartidorAdapter
import com.example.apppedidos.frontend.api.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListaPedidosRepartidor : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PedidosRepartidorAdapter
    private var idRepartidor: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedidos_pendientes)
        val btnSalir = findViewById<Button>(R.id.salir)


        // Obtener el ID del repartidor del intent
        idRepartidor = intent.getIntExtra("id_usuario", 0)
        if (idRepartidor == 0) {
            Toast.makeText(this, "Error: ID de repartidor no válido", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Configurar RecyclerView
        recyclerView = findViewById(R.id.rvPedidosPendientes)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PedidosRepartidorAdapter(
            context = this,
            pedidos = emptyList(),
            onItemClick = { pedido ->
                cargarPedidos()
            }
        )

        btnSalir.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        recyclerView.adapter = adapter

        // Cargar los pedidos
        cargarPedidos()
    }

    private fun cargarPedidos() {
        CoroutineScope(Dispatchers.IO).launch {
            try {

                val response = ApiClient.instance.obtenerPedidosRepartidor(idRepartidor, "en_camino")
                Log.d("INFO Repartidor", "Response: ${response.body()}")

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val pedidosResponse = response.body()
                        if (pedidosResponse?.success == true) {
                            adapter.actualizarLista(pedidosResponse.pedidos)
                        } else {
                            Toast.makeText(
                                this@ListaPedidosRepartidor,
                                "No se encontraron pedidos",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@ListaPedidosRepartidor,
                            "Error al obtener pedidos: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.e("PedidosRepartidor", "Error: ${response.errorBody()?.string()}")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@ListaPedidosRepartidor,
                        "Error de conexión: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("PedidosRepartidor", "Error: ", e)
                }
            }
        }
    }
}