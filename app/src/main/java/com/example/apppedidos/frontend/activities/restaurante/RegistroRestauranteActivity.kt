package com.example.apppedidos.frontend.activities.restaurante

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.apppedidos.R
import com.example.apppedidos.frontend.api.ApiClient
import com.example.apppedidos.frontend.models.Restaurante
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistroRestauranteActivity : AppCompatActivity() {
    private lateinit var nombreInput: EditText
    private lateinit var cedulaInput: EditText
    private lateinit var direccionInput: EditText
    private lateinit var tipoComidaInput: EditText
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_restaurante)

        nombreInput = findViewById(R.id.etNombre)
        cedulaInput = findViewById(R.id.etCedulaJuridica)
        direccionInput = findViewById(R.id.etDireccion)
        tipoComidaInput = findViewById(R.id.etTipoComida)
        btnGuardar = findViewById(R.id.btnGuardarRestaurante)

        btnGuardar.setOnClickListener {
            val restaurante = Restaurante(
                nombre = nombreInput.text.toString(),
                cedula_juridica = cedulaInput.text.toString(),
                direccion = direccionInput.text.toString(),
                tipo_comida = tipoComidaInput.text.toString()
            )

            GlobalScope.launch(Dispatchers.IO) {
                val response =  ApiClient.instance.registrarRestaurante(restaurante)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@RegistroRestauranteActivity, "Registrado exitosamente", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@RegistroRestauranteActivity, "Error al registrar", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}