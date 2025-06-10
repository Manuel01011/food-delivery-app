package com.example.apppedidos.frontend.activities.cliente

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apppedidos.R
import com.example.apppedidos.frontend.adapters.ClientesAdapter
import com.example.apppedidos.frontend.api.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListaClientesActivity : AppCompatActivity() {

    private lateinit var rvClientes: RecyclerView
    private lateinit var adapter: ClientesAdapter

    companion object {
        private const val TAG = "ListaClientesActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_clientes)

        Log.d(TAG, "onCreate: Iniciando actividad")

        rvClientes = findViewById(R.id.rvClientes)
        rvClientes.layoutManager = LinearLayoutManager(this)

        cargarClientes()
    }

    private fun cargarClientes() {
        Log.d(TAG, "cargarClientes: Iniciando llamada a la API")
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiClient.instance.obtenerClientes()
                Log.d(TAG, "cargarClientes: Respuesta recibida")

                if (response.isSuccessful) {
                    val body = response.body()
                    Log.d(TAG, "cargarClientes: isSuccessful = true, body = $body")

                    if (body?.success == true) {
                        val clientes = body.clientes ?: emptyList()
                        Log.d(TAG, "cargarClientes: clientes recibidos = ${clientes.size}")

                        withContext(Dispatchers.Main) {
                            adapter = ClientesAdapter(clientes)
                            rvClientes.adapter = adapter
                        }
                    } else {
                        Log.e(TAG, "cargarClientes: La respuesta indica success=false o body nulo")
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@ListaClientesActivity, "Error al cargar clientes", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Log.e(TAG, "cargarClientes: Error en la respuesta - code: ${response.code()}, message: ${response.message()}")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@ListaClientesActivity, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "cargarClientes: Excepción atrapada", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ListaClientesActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
