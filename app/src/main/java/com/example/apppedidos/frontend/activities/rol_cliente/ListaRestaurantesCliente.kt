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
import com.example.apppedidos.frontend.activities.menu.MenuCliente
import com.example.apppedidos.frontend.adapters.RestauranteAdapter
import com.example.apppedidos.frontend.api.ApiClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers


class ListaRestaurantesCliente : AppCompatActivity() {
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: RestauranteAdapter
    private lateinit var btnSalir: Button
    private var idUsuario: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_restaurantescliente)

        idUsuario = intent.getIntExtra("id_usuario", -1)
        recycler = findViewById(R.id.recyclerRestaurantes)
        btnSalir = findViewById(R.id.btnSalir)

        Log.d("ID USUARIOS DESDE LISTA RESTAURANTES", "ID Usuario: ${idUsuario.toString()}")

        btnSalir.setOnClickListener() {
            startActivity(Intent(this, MenuCliente::class.java))
        }

        recycler.layoutManager = LinearLayoutManager(this)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = ApiClient.instance.obtenerRestaurantes()
                if (response.isSuccessful) {
                    val restaurantes = response.body()?.restaurantes ?: emptyList()
                    adapter = RestauranteAdapter(restaurantes) {
                        val intent = Intent(this@ListaRestaurantesCliente, ListaCombosCliente::class.java).apply {
                            putExtra("id_restaurante", it.id_restaurante)
                            putExtra("id_usuario", idUsuario)
                        }
                        startActivity(intent)
                    }
                    recycler.adapter = adapter
                } else {
                    Toast.makeText(this@ListaRestaurantesCliente, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@ListaRestaurantesCliente, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}