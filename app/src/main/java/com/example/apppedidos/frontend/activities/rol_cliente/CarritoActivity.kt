package com.example.apppedidos.frontend.activities.rol_cliente

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apppedidos.R
import com.example.apppedidos.frontend.adapters.CarritoAdapter
import com.example.apppedidos.frontend.api.ApiClient
import com.example.apppedidos.frontend.models.ComboRequest
import com.example.apppedidos.frontend.models.ComboRequest2
import com.example.apppedidos.frontend.models.PedidoRequest
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CarritoActivity : AppCompatActivity() {
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: CarritoAdapter
    private lateinit var btnConfirmar: Button
    private lateinit var tvTotal: TextView
    private lateinit var etDireccion: EditText

    private val carrito = mutableListOf<ComboPedido>()
    private var idRestaurante: Int = -1
    private var idUsuario: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        // Inicialización con mejores mensajes de error
        idRestaurante = intent.getIntExtra("id_restaurante", -1).also {
            if (it == -1) {
                showErrorAndFinish("Restaurante no válido")
            }
        }

        idUsuario = intent.getIntExtra("id_usuario", -1).also {
            if (it == -1) {
                showErrorAndFinish("Usuario no identificado")
            }
        }
        Log.d("ID USUARIOS DESDE CARRITO", "ID Usuario: ${idUsuario.toString()}")

        // Manejo mejorado del carrito
        (intent.getSerializableExtra("carrito") as? ArrayList<ComboPedido>)?.let {
            if (it.isEmpty()) {
                showErrorAndFinish("El carrito está vacío", finishAfter = false)
            } else {
                carrito.addAll(it)
            }
        } ?: showErrorAndFinish("Error al cargar el carrito")

        // Inicializar vistas
        initViews()
        setupRecyclerView()
        calcularTotal()

        btnConfirmar.setOnClickListener {
            if (validarFormulario()) {
                confirmarPedido()
            }
        }
    }

    private fun initViews() {
        recycler = findViewById(R.id.recyclerCarrito)
        btnConfirmar = findViewById(R.id.btnConfirmarPedido)
        tvTotal = findViewById(R.id.tvTotal)
        etDireccion = findViewById(R.id.etDireccion)
    }

    private fun setupRecyclerView() {
        recycler.layoutManager = LinearLayoutManager(this)
        adapter = CarritoAdapter(carrito) { updatedCarrito ->
            carrito.clear()
            carrito.addAll(updatedCarrito)
            calcularTotal()
        }
        recycler.adapter = adapter
    }

    private fun calcularTotal() {
        val total = carrito.sumOf { it.precio * it.cantidad }
        tvTotal.text = "Total: ₡${String.format("%.2f", total)}"
    }

    private fun validarFormulario(): Boolean {
        return when {
            etDireccion.text.toString().trim().isEmpty() -> {
                etDireccion.error = "Ingrese una dirección de entrega"
                false
            }
            carrito.isEmpty() -> {
                Toast.makeText(this, "El carrito está vacío", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    private fun confirmarPedido() {
        val direccion = etDireccion.text.toString().trim()

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val request = PedidoRequest(
                    idCliente = idUsuario,
                    idRestaurante = idRestaurante,
                    direccionEntrega = direccion,
                    combos = carrito.map { combo ->
                        ComboRequest2(
                            idCombo = combo.idCombo,
                            cantidad = combo.cantidad,
                        )
                    }
                )

                Log.d("REQUEST", "Enviando: ${Gson().toJson(request)}")
                val response = ApiClient.instance.crearPedidoCompleto(request)
                Log.d("API_RESPONSE_RAW", response.body().toString())

                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        // Asume que body es Map<String, Any>
                        val pedidoMap = body["pedido"] as? Map<String, Any>
                        val idPedido = when (val id = pedidoMap?.get("id_pedido")) {
                            is Double -> id.toInt()
                            is Int -> id
                            is Number -> id.toInt()
                            else -> null
                        }

                        if (idPedido != null) {
                            showSuccessAndNavigate(idPedido)
                        } else {
                            Toast.makeText(
                                this@CarritoActivity,
                                "Error: No se recibió ID de pedido válido",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                } else {
                    Toast.makeText(
                        this@CarritoActivity,
                        "Error ${response.code()}: ${response.errorBody()?.string()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@CarritoActivity,
                    "Error: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
                Log.e("CarritoActivity", "Error al confirmar pedido", e)
            }
        }
    }

    private fun showSuccessAndNavigate(idPedido: Int) {
        Toast.makeText(
            this,
            "Pedido #$idPedido creado exitosamente",
            Toast.LENGTH_SHORT
        ).show()

        Intent(this, ListapedidosCliente::class.java).apply {
            putExtra("id_pedido", idPedido)
            putExtra("id_usuario", idUsuario)  // Cambiado a id_usuario
            startActivity(this)
        }

        finish()
    }

    private fun showErrorAndFinish(message: String, finishAfter: Boolean = true) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        if (finishAfter) finish()
    }
}

// Modelo para items del carrito (sin cambios)
data class ComboPedido(
    val idCombo: Int,
    val nombre: String,
    val precio: Double,
    var cantidad: Int = 1
) : java.io.Serializable