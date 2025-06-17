package com.example.apppedidos.frontend.activities.menu

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apppedidos.R
import com.example.apppedidos.frontend.adapters.RestaurantesPopularesAdapter
import com.example.apppedidos.frontend.api.ApiClient
import com.example.apppedidos.frontend.models.RestaurantePopular
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RestaurantesPopularesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RestaurantesPopularesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurantes_populares)

        // Configurar RecyclerView
        recyclerView = findViewById(R.id.rvRestaurantesPopulares)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RestaurantesPopularesAdapter(emptyList())
        recyclerView.adapter = adapter

        // Cargar los restaurantes populares
        cargarRestaurantesPopulares()
    }

    private fun cargarRestaurantesPopulares() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiClient.instance.obtenerRestaurantesPopulares()

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body?.get("success") == true) {
                            val restaurantesData = body["restaurantes"] as? List<Map<String, Any>>
                            val restaurantes = restaurantesData?.mapNotNull {
                                try {
                                    RestaurantePopular(
                                        id_restaurante = (it["id_restaurante"] as? Number)?.toInt() ?: 0,
                                        nombre = it["nombre"] as? String ?: "",
                                        tipo_comida = it["tipo_comida"] as? String ?: "",
                                        total_pedidos = (it["total_pedidos"] as? Number)?.toInt() ?: 0,
                                        ranking = (it["ranking"] as? Number)?.toInt() ?: 0
                                    )
                                } catch (e: Exception) {
                                    Log.e("RestaurantesPopulares", "Error parsing restaurante: ${e.message}")
                                    null
                                }
                            } ?: emptyList()

                            adapter.actualizarLista(restaurantes)
                        } else {
                            Toast.makeText(
                                this@RestaurantesPopularesActivity,
                                "No se pudieron cargar los restaurantes",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@RestaurantesPopularesActivity,
                            "Error al obtener datos: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.e("RestaurantesPopulares", "Error: ${response.errorBody()?.string()}")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@RestaurantesPopularesActivity,
                        "Error de conexión: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("RestaurantesPopulares", "Error: ", e)
                }
            }
        }
    }
}