package com.example.apppedidos.frontend.activities.estadisticas

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apppedidos.R
import com.example.apppedidos.frontend.adapters.RestaurantesVentasAdapter
import com.example.apppedidos.frontend.api.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RestaurantesVentasActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RestaurantesVentasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurantes_ventas)

        recyclerView = findViewById(R.id.rvRestaurantesVentas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RestaurantesVentasAdapter(emptyList())
        recyclerView.adapter = adapter

        cargarReporteVentasRestaurantes()
    }

    private fun cargarReporteVentasRestaurantes() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiClient.instance.obtenerReporteVentasRestaurantes()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val reporteResponse = response.body()
                        if (reporteResponse?.success == true && !reporteResponse.reporte.isNullOrEmpty()) {
                            adapter.actualizarLista(reporteResponse.reporte)
                        } else {
                            Toast.makeText(this@RestaurantesVentasActivity, "No hay datos disponibles", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@RestaurantesVentasActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                        Log.e("RestaurantesVentas", "Error: ${response.errorBody()?.string()}")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@RestaurantesVentasActivity, "Error de conexión: ${e.message}", Toast.LENGTH_SHORT).show()
                    Log.e("RestaurantesVentas", "Exception", e)
                }
            }
        }
    }
}