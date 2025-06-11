package com.example.apppedidos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.apppedidos.frontend.activities.menu.MenuActivity
import com.example.apppedidos.frontend.activities.repartidor.RepartidorListActivity
import com.example.apppedidos.frontend.api.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var etCedula: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etCedula = findViewById(R.id.etCedula)
        loginButton = findViewById(R.id.btnLogin)

        loginButton.setOnClickListener {
            val cedula = etCedula.text.toString().trim()
            if (cedula.isNotEmpty()) {
                verificarUsuario(cedula)
            } else {
                Toast.makeText(this, "Ingrese la cédula", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun verificarUsuario(cedula: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val cedula = etCedula.text.toString().trim()
                Log.d("LOGIN", "Cédula ingresada: '$cedula', longitud: ${cedula.length}")
                val response = ApiClient.instance.verificarUsuario(cedula)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val usuario = response.body()
                        if (usuario != null) {
                                Toast.makeText(this@MainActivity, "Bienvenido ${usuario.nombre}", Toast.LENGTH_SHORT).show()
                                // Redireccionar según el tipo
                                val intent = when (usuario.tipo) {
                                    "cliente" -> Intent(this@MainActivity, MenuActivity::class.java)
                                    "repartidor" -> Intent(this@MainActivity, MenuActivity::class.java)
                                    else -> {
                                        Toast.makeText(this@MainActivity, "Tipo no soportado", Toast.LENGTH_SHORT).show()
                                        return@withContext
                                    }
                                }
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this@MainActivity, "Usuario suspendido", Toast.LENGTH_SHORT).show()
                            }

                    } else {
                        Toast.makeText(this@MainActivity, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Error de red o del servidor", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}