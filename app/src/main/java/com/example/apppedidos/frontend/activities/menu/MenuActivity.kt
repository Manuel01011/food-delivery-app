package com.example.apppedidos.frontend.activities.menu

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.apppedidos.R
import android.content.Intent
import com.example.apppedidos.frontend.activities.cliente.ListaClientesActivity
import com.example.apppedidos.frontend.activities.repartidor.RepartidorListActivity
class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val btnClientes = findViewById<Button>(R.id.btnClientes)
        val btnRepartidores = findViewById<Button>(R.id.btnRepartidores)

        btnClientes.setOnClickListener {
            startActivity(Intent(this, ListaClientesActivity::class.java))
        }
        btnRepartidores.setOnClickListener {
            startActivity(Intent(this, RepartidorListActivity::class.java))
        }
    }
}