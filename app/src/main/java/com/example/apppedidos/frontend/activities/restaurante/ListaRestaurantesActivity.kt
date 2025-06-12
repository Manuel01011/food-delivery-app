package com.example.apppedidos.frontend.activities.restaurante

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apppedidos.R
import com.example.apppedidos.frontend.activities.menu.MenuActivity
import com.example.apppedidos.frontend.activities.repartidor.RepartidorRegisterActivity
import com.example.apppedidos.frontend.adapters.RestauranteAdapter
import com.example.apppedidos.frontend.api.ApiClient
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers


class ListaRestaurantesActivity : AppCompatActivity() {
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: RestauranteAdapter
    private lateinit var fabAddRestaurante : FloatingActionButton
    private lateinit var btnSalir: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_restaurantes)

        recycler = findViewById(R.id.recyclerRestaurantes)
        fabAddRestaurante = findViewById(R.id.fabAddRestaurante)
        btnSalir = findViewById(R.id.btnSalir)

        fabAddRestaurante.setOnClickListener {
            val intent = Intent(this, RegistroRestauranteActivity::class.java)
            startActivity(intent)
        }

        btnSalir.setOnClickListener() {
            startActivity(Intent(this, MenuActivity::class.java))
        }

        recycler.layoutManager = LinearLayoutManager(this)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = ApiClient.instance.obtenerRestaurantes()
                if (response.isSuccessful) {
                    val restaurantes = response.body()?.restaurantes ?: emptyList()
                    adapter = RestauranteAdapter(restaurantes) {
                        val intent = Intent(this@ListaRestaurantesActivity, ListaCombosActivity::class.java)
                        intent.putExtra("id_restaurante", it.id_restaurante)
                        startActivity(intent)
                    }
                    recycler.adapter = adapter
                } else {
                    Toast.makeText(this@ListaRestaurantesActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@ListaRestaurantesActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

    }
}