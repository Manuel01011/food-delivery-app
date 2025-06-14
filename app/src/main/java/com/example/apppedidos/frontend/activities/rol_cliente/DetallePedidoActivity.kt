package com.example.apppedidos.frontend.activities.rol_cliente

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apppedidos.R
import com.example.apppedidos.frontend.adapters.DetallePedidoAdapter
import com.example.apppedidos.frontend.api.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetallePedidoActivity : AppCompatActivity() {

    private lateinit var tvEstado: TextView
    private lateinit var tvTotal: TextView
    private lateinit var tvRepartidor: TextView
    private lateinit var tvFecha: TextView
    private lateinit var recycler: RecyclerView
    private lateinit var btnCalificar: Button
    private lateinit var btnVolver: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_pedido)

        val idPedido = intent.getIntExtra("id_pedido", -1)
        val idCliente = intent.getIntExtra("id_cliente", -1)

        tvEstado = findViewById(R.id.tvEstado)
        tvTotal = findViewById(R.id.tvTotal)
        tvRepartidor = findViewById(R.id.tvRepartidor)
        tvFecha = findViewById(R.id.tvFecha)
        recycler = findViewById(R.id.recyclerDetalle)
        btnCalificar = findViewById(R.id.btnCalificar)
        btnVolver = findViewById(R.id.btnVolver)

        recycler.layoutManager = LinearLayoutManager(this)

        cargarDetallePedido(idPedido, idCliente)

        btnVolver.setOnClickListener {
            finish()
        }

        btnCalificar.setOnClickListener {
            // Implementar lógica de calificación
            val intent = Intent(this, CalificarPedidoActivity::class.java).apply {
                putExtra("id_pedido", idPedido)
                putExtra("id_cliente", idCliente)
            }
            startActivity(intent)
        }
    }

    private fun cargarDetallePedido(idPedido: Int, idCliente: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = ApiClient.instance.obtenerDetallePedido(idPedido, idCliente)
                if (response.isSuccessful) {
                    val detalle = response.body()

                    tvEstado.text = "Estado: ${detalle?.get("estado")}"
                    tvTotal.text = "Total: ₡${detalle?.get("total")}"
                    tvRepartidor.text = "Repartidor: ${detalle?.get("nombre_repartidor")}"
                    tvFecha.text = "Fecha: ${detalle?.get("fecha_pedido")}"

                    val detalles = detalle?.get("detalles") as? List<Map<String, Any>> ?: emptyList()
                    val adapter = DetallePedidoAdapter(detalles)
                    recycler.adapter = adapter
                } else {
                    Toast.makeText(this@DetallePedidoActivity, "Error al cargar detalle", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@DetallePedidoActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}