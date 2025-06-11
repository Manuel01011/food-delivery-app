package com.example.apppedidos.frontend.activities.restaurante

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apppedidos.R
import com.example.apppedidos.frontend.adapters.ComboAdapter
import com.example.apppedidos.frontend.api.ApiClient
import com.example.apppedidos.frontend.models.Combo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ListaCombosActivity : AppCompatActivity() {
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ComboAdapter
    private lateinit var btnSalir: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_combos)

        val idRestaurante = intent.getIntExtra("id_restaurante", -1)
        recycler = findViewById(R.id.recyclerCombos)
        btnSalir = findViewById(R.id.btnSalir)
        recycler.layoutManager = LinearLayoutManager(this)

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
                    adapter = ComboAdapter(combos)
                    recycler.adapter = adapter
                }
            } catch (e: Exception) {
                Toast.makeText(this@ListaCombosActivity, "Error al cargar combos", Toast.LENGTH_SHORT).show()
            }
        }

        btnSalir.setOnClickListener {
            startActivity(Intent(this, ListaRestaurantesActivity::class.java))
            finish()
        }
    }
}