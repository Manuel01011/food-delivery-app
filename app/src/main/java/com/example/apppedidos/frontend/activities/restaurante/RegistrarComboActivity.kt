package com.example.apppedidos.frontend.activities.restaurante

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.apppedidos.R
import com.example.apppedidos.frontend.api.ApiClient
import com.example.apppedidos.frontend.models.ComboRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistrarComboActivity : AppCompatActivity() {

    private lateinit var etNumeroCombo: EditText
    private lateinit var etNombre: EditText
    private lateinit var etDescripcion: EditText
    private lateinit var btnRegistrar: Button
    private var idRestaurante: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_combo)

        etNumeroCombo = findViewById(R.id.etNumeroCombo)
        etNombre = findViewById(R.id.etNombre)
        etDescripcion = findViewById(R.id.etDescripcion)
        btnRegistrar = findViewById(R.id.btnRegistrar)

        idRestaurante = intent.getIntExtra("id_restaurante", -1)

        btnRegistrar.setOnClickListener {
            val numeroCombo = etNumeroCombo.text.toString().toIntOrNull()
            val nombre = etNombre.text.toString()
            val descripcion = etDescripcion.text.toString()

            if (numeroCombo == null || nombre.isBlank() || descripcion.isBlank()) {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val combo = ComboRequest(idRestaurante, numeroCombo, nombre, descripcion)

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = ApiClient.instance.registrarCombo(idRestaurante, combo)
                    runOnUiThread {
                        if (response.isSuccessful) {
                            val idCombo = response.body()?.get("id_combo")
                            Toast.makeText(this@RegistrarComboActivity, "Combo registrado con ID: $idCombo", Toast.LENGTH_LONG).show()
                            finish()
                        } else {
                            Toast.makeText(this@RegistrarComboActivity, "Error al registrar", Toast.LENGTH_LONG).show()
                        }
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(this@RegistrarComboActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}