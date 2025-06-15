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
import com.example.apppedidos.frontend.activities.rol_restaurante.ListaPedidosRestauranteActivity
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
                Log.d("API_RESPONSE", "Código: ${response.code()}")
                Log.d("API_RESPONSE", "Mensaje: ${response.message()}")
                Log.d("API_RESPONSE", "Cuerpo: ${response.body()}")

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val usuario = response.body()
                        if (usuario != null && usuario.estado == "activo") {
                            Toast.makeText(this@MainActivity, "Bienvenido ${usuario.nombre}", Toast.LENGTH_SHORT).show()

                            val intent = when (usuario.tipo) {
                                "admin" -> Intent(this@MainActivity, MenuActivity::class.java)
                                "cliente" -> Intent(this@MainActivity, MenuCliente::class.java)
                                "restaurante" -> Intent(this@MainActivity, ListaPedidosRestauranteActivity::class.java)
                                else -> {
                                    Toast.makeText(this@MainActivity, "Tipo no soportado", Toast.LENGTH_SHORT).show()
                                    return@withContext
                                }
                            }.apply {
                                putExtra("nombre", usuario.nombre)
                                putExtra("id_usuario", usuario.id_usuario)
                                putExtra("user_type", usuario.tipo)
                                putExtra("user_origin", usuario.origen ?: "")
                                Log.d("INTENT_EXTRAS", "Extras enviados: id_usuario=${usuario.id_usuario}, tipo=${usuario.tipo}, origen=${usuario.origen}")
                            }

                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@MainActivity, "Usuario no encontrado o suspendido", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Error de conexión", Toast.LENGTH_SHORT).show()
                    Log.e("LoginError", "Error al verificar usuario", e)
                }
            }
        }
    }
}