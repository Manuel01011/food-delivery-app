package com.example.apppedidos.frontend.activities.menu

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.apppedidos.MainActivity
import com.example.apppedidos.R
import com.example.apppedidos.frontend.activities.restaurante.ListaRestaurantesActivity
import com.example.apppedidos.frontend.activities.rol_cliente.ListaRestaurantesCliente
import com.example.apppedidos.frontend.activities.rol_cliente.ListapedidosCliente

class MenuCliente : AppCompatActivity() {

    private var idUsuario: Int = -1
    private lateinit var pedidos: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menucliente)


        idUsuario = intent.getIntExtra("id_usuario", -1)
        pedidos = findViewById(R.id.btnHistorial)

        // Ahora sí inicializar las vistas
        val btnSalir = findViewById<Button>(R.id.salir)
        val btnRestaurantes = findViewById<Button>(R.id.btnRestaurantes)


        btnSalir.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        pedidos.setOnClickListener {
            if (idUsuario != -1) {
                val intent = Intent(this, ListapedidosCliente::class.java).apply {
                    putExtra("id_usuario", idUsuario)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "ID de usuario no válido", Toast.LENGTH_SHORT).show()
            }
        }

        btnRestaurantes.setOnClickListener {
            val intent = Intent(this, ListaRestaurantesCliente::class.java).apply {
                putExtra("id_usuario", idUsuario)
            }
            startActivity(intent)
        }
    }
}