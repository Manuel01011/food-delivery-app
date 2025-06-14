package com.example.apppedidos.frontend.activities.rol_cliente
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apppedidos.R
import com.example.apppedidos.frontend.adapters.ComboAdapterCliente
import com.example.apppedidos.frontend.api.ApiClient
import com.example.apppedidos.frontend.models.Combo
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ListaCombosCliente : AppCompatActivity() {
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ComboAdapterCliente
    private lateinit var btnCarrito: Button

    private val carrito = mutableListOf<ComboPedido>()
    private var idRestaurante: Int = -1
    private var idUsuario: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_comboscliente)

        idRestaurante = intent.getIntExtra("id_restaurante", -1)
        idUsuario = intent.getIntExtra("id_usuario", -1)

        Log.d("ID RESTAURANTE DESDE COMBOS", "ID Usuario: ${idRestaurante.toString()}")
        Log.d("ID USUARIOS DESDE COMBOS", "ID Usuario: ${idUsuario.toString()}")

        recycler = findViewById(R.id.recyclerCombos)
        btnCarrito = findViewById(R.id.btnCarrito)

        recycler.layoutManager = LinearLayoutManager(this)


        btnCarrito.setOnClickListener {
            if (carrito.isEmpty()) {
                Toast.makeText(this, "Agregue combos al carrito primero", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, CarritoActivity::class.java).apply {
                    putExtra("id_restaurante", idRestaurante)
                    putExtra("id_usuario", idUsuario)
                    putExtra("carrito", ArrayList(carrito.map { it.copy() }))
                }
                startActivity(intent)
            }
        }

        cargarCombos()
    }

    private fun cargarCombos() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = ApiClient.instance.obtenerCombos(idRestaurante)
                if (response.isSuccessful) {
                    val lista = response.body()?.get("combos") as? List<Map<String, Any>>
                    val combos = lista?.map {
                        Combo(
                            (it["id_combo"] as Double).toInt(),
                            it["nombre"] as String,
                            it["descripcion"] as String,
                            it["precio"] as Double
                        )
                    } ?: emptyList()

                    adapter = ComboAdapterCliente(combos, idRestaurante) { combo ->
                        // Manejar selección de combo
                        val existing = carrito.find { it.idCombo == combo.id_combo }
                        if (existing != null) {
                            existing.cantidad++
                        } else {
                            carrito.add(ComboPedido(
                                combo.id_combo,
                                combo.nombre,
                                combo.precio,
                                1
                            ))
                        }
                        Toast.makeText(
                            this@ListaCombosCliente,
                            "${combo.nombre} agregado al carrito",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    recycler.adapter = adapter
                }
            } catch (e: Exception) {
                Toast.makeText(this@ListaCombosCliente, "Error al cargar combos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}