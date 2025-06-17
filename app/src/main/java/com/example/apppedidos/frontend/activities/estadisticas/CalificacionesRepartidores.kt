package com.example.apppedidos.frontend.activities.estadisticas

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apppedidos.R
import com.example.apppedidos.frontend.adapters.CalificacionesRepartidoresAdapter
import com.example.apppedidos.frontend.api.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CalificacionesRepartidores : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CalificacionesRepartidoresAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calificaciones_repartidores)

        recyclerView = findViewById(R.id.rvCalificaciones)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CalificacionesRepartidoresAdapter(emptyList())
        recyclerView.adapter = adapter

        cargarCalificaciones()
    }

    private fun cargarCalificaciones() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiClient.instance.obtenerCalificacionesRepartidores()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        val calificaciones = response.body()?.calificaciones ?: emptyList()
                        adapter.actualizar(calificaciones)
                    } else {
                        Toast.makeText(this@CalificacionesRepartidores, "Error al obtener datos", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@CalificacionesRepartidores, "Error de red: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
