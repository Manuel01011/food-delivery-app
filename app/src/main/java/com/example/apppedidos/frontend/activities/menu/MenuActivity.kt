package com.example.apppedidos.frontend.activities.menu

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.apppedidos.R
import android.content.Intent
import com.example.apppedidos.MainActivity
import com.example.apppedidos.frontend.activities.cliente.ListaClientesActivity
import com.example.apppedidos.frontend.activities.repartidor.RepartidorListActivity
import com.example.apppedidos.frontend.activities.restaurante.ListaRestaurantesActivity

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val btnClientes = findViewById<Button>(R.id.btnClientes)
        val btnRepartidores = findViewById<Button>(R.id.btnRepartidores)
        val btnSalir = findViewById<Button>(R.id.btnSalir)
        val btnRestaurantes = findViewById<Button>(R.id.btnRestaurantes)

        btnSalir.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        btnClientes.setOnClickListener {
            startActivity(Intent(this, ListaClientesActivity::class.java))
        }
        btnRepartidores.setOnClickListener {
            startActivity(Intent(this, RepartidorListActivity::class.java))
        }
        btnRestaurantes.setOnClickListener {
            startActivity(Intent(this, ListaRestaurantesActivity::class.java))
        }
    }
}