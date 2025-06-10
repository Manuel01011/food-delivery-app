package com.example.apppedidos.frontend.activities.cliente

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.apppedidos.R
import com.example.apppedidos.frontend.api.ApiClient
import com.example.apppedidos.frontend.models.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistroClienteActivity : AppCompatActivity() {

    private lateinit var cedula: EditText
    private lateinit var nombre: EditText
    private lateinit var direccion: EditText
    private lateinit var numeroTarjeta: EditText
    private lateinit var telefono: EditText
    private lateinit var correo: EditText
    private lateinit var registrarBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_cliente)

        cedula = findViewById(R.id.etCedula)
        nombre = findViewById(R.id.etNombre)
        direccion = findViewById(R.id.etDireccion)
        numeroTarjeta = findViewById(R.id.etNumeroTarjeta)
        telefono = findViewById(R.id.etTelefono)
        correo = findViewById(R.id.etCorreo)
        registrarBtn = findViewById(R.id.btnRegistrar)

        registrarBtn.setOnClickListener {
            val usuario = Usuario(
                cedula = cedula.text.toString(),
                nombre = nombre.text.toString(),
                direccion = direccion.text.toString(),
                numero_tarjeta = numeroTarjeta.text.toString(),
                telefono = telefono.text.toString(),
                correo = correo.text.toString()
            )

            registrarUsuario(usuario)
        }
    }

    private fun registrarUsuario(usuario: Usuario) {
        val call = ApiClient.instance.registrarUsuario(usuario)

        call.enqueue(object : Callback<Map<String, Any>> {
            override fun onResponse(call: Call<Map<String, Any>>, response: Response<Map<String, Any>>) {
                if (response.isSuccessful && response.body()?.get("success") == true) {
                    Toast.makeText(this@RegistroClienteActivity, "Registro exitoso", Toast.LENGTH_LONG).show()
                    finish() // o redirigir a Login
                } else {
                    Toast.makeText(
                        this@RegistroClienteActivity,
                        "Error: ${response.body()?.get("message") ?: "Desconocido"}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                Toast.makeText(this@RegistroClienteActivity, "Error de red: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}