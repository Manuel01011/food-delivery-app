package com.example.apppedidos.frontend.activities.menu
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.apppedidos.R
import com.example.apppedidos.frontend.activities.estadisticas.CalificacionesRepartidores
import com.example.apppedidos.frontend.activities.estadisticas.ClientesYpedidos
import com.example.apppedidos.frontend.activities.estadisticas.RestaurantesPopularesActivity
import com.example.apppedidos.frontend.activities.estadisticas.RestaurantesVentasActivity

class MenuEstadisticas: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menuestadistica)

        val btnPopulares = findViewById<LinearLayout>(R.id.btnPopulares)
        val btnVentas = findViewById<LinearLayout>(R.id.btnVentas)
        val btnPedidos = findViewById<LinearLayout>(R.id.btnPedidos)
        val btnCalificaciones = findViewById<LinearLayout>(R.id.btnCalificaciones)
        val btnSalir = findViewById<Button>(R.id.btnSalir)


        btnSalir.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }
        btnPopulares.setOnClickListener {
            startActivity(Intent(this, RestaurantesPopularesActivity::class.java))
        }
        btnVentas.setOnClickListener() {
            startActivity(Intent(this, RestaurantesVentasActivity::class.java))
        }
        btnCalificaciones.setOnClickListener() {
            startActivity(Intent(this, CalificacionesRepartidores::class.java))
        }
        btnPedidos.setOnClickListener() {
            startActivity(Intent(this, ClientesYpedidos::class.java))
        }
    }
}