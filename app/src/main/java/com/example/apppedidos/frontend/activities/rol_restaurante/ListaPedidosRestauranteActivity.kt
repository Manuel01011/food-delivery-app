package com.example.apppedidos.frontend.activities.rol_restaurante
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apppedidos.MainActivity
import com.example.apppedidos.R
import com.example.apppedidos.frontend.adapters.PedidosRestauranteAdapter
import com.example.apppedidos.frontend.api.ApiClient
import com.example.apppedidos.frontend.api.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListaPedidosRestauranteActivity : AppCompatActivity() {
    private lateinit var apiService: ApiService
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PedidosRestauranteAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var emptyStateView: View
    private lateinit var name : String
    private lateinit var tvEmptyMessage: TextView
    private lateinit var tvTituloPedidos: TextView


    private var idRestaurante: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_pedidos_restaurante)
        val btnSalir = findViewById<Button>(R.id.salir)

        idRestaurante = intent.getIntExtra("id_usuario", -1)
        name = intent.getStringExtra("nombre") ?: "Restaurante"
        tvTituloPedidos = findViewById(R.id.tvTituloPedidos)
        tvTituloPedidos.text = "Lista de pedidos de: $name"

        if (idRestaurante == -1) {
            Toast.makeText(this, "Error: ID de restaurante no válido", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        btnSalir.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // Inicializar vistas
        recyclerView = findViewById(R.id.recyclerViewPedidos)
        progressBar = findViewById(R.id.progressBar)
        emptyStateView = findViewById(R.id.emptyStateView)
        tvEmptyMessage = findViewById(R.id.tvEmptyMessage)
        recyclerView.layoutManager = LinearLayoutManager(this)
        apiService = ApiClient.instance

        cargarPedidos()
    }

    private fun cargarPedidos() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        emptyStateView.visibility = View.GONE

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.listarPedidosRestaurante(idRestaurante, "pendiente")

                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.GONE

                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody?.success == true && !responseBody.pedidos.isNullOrEmpty()) {
                            // Hay pedidos
                            recyclerView.visibility = View.VISIBLE
                            emptyStateView.visibility = View.GONE

                            adapter = PedidosRestauranteAdapter(
                                context = this@ListaPedidosRestauranteActivity,
                                pedidos = responseBody.pedidos,
                                onItemClick = { pedido ->
                                    cargarPedidos()
                                },
                                idRestaurante = idRestaurante
                            )
                            recyclerView.adapter = adapter
                        } else {
                            // No hay pedidos
                            showEmptyState("No hay pedidos pendientes")
                        }
                    } else {
                        showError("Error al cargar pedidos: ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.GONE
                    showError("Error: ${e.message}")
                }
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        Log.e("PEDIDOS_ERROR", message)
        // Mostrar estado de error si lo deseas
        tvEmptyMessage.text = message
        recyclerView.visibility = View.GONE
        emptyStateView.visibility = View.VISIBLE
    }

    private fun showEmptyState(message: String) {
        tvEmptyMessage.text = message
        recyclerView.visibility = View.GONE
        emptyStateView.visibility = View.VISIBLE
    }
}