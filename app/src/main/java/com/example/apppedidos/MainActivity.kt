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
import com.example.apppedidos.frontend.activities.menu.MenuCliente
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
                val response = ApiClient.instance.verificarUsuario(cedula)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val usuario = response.body()
                        if (usuario != null) {
                                Toast.makeText(this@MainActivity, "Bienvenido ${usuario.nombre}", Toast.LENGTH_SHORT).show()

                                val intent = when (usuario.tipo) {
                                    "admin" -> Intent(this@MainActivity, MenuActivity::class.java)
                                    "cliente" -> Intent(this@MainActivity, MenuCliente::class.java).apply {
                                        putExtra("id_usuario", usuario.id_usuario)
                                    }

                                    else -> {
                                        Toast.makeText(this@MainActivity, "Tipo no soportado", Toast.LENGTH_SHORT).show()
                                        return@withContext
                                    }
                                }
                            Log.d("ID USUARIOS", "ID Usuario: ${usuario.id_usuario.toString()}")

                            startActivity(intent)
                                finish()

                        } else {
                            Toast.makeText(this@MainActivity, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Error de conexión", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}