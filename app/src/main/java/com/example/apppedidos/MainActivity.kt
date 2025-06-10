package com.example.apppedidos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.apppedidos.frontend.activities.menu.MenuActivity
import com.example.apppedidos.frontend.activities.repartidor.RepartidorListActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginButton = findViewById<Button>(R.id.btnLogin)
        loginButton.setOnClickListener {
            // Aquí iría la lógica de autenticación. Por ahora pasa directo.
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
           // finish()
        }
    }
}