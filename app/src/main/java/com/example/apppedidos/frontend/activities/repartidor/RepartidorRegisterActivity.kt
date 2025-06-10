package com.example.apppedidos.frontend.activities.repartidor

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.apppedidos.databinding.ActivityRepartidorRegisterBinding
import com.example.apppedidos.frontend.api.ApiClient
import com.example.apppedidos.frontend.models.Repartidor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RepartidorRegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRepartidorRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepartidorRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupListeners()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Registrar Repartidor"
    }

    private fun setupListeners() {
        binding.btnRegister.setOnClickListener {
            if (validateForm()) {
                registerRepartidor()
            }
        }
    }

    private fun validateForm(): Boolean {
        with(binding) {
            if (etNombre.text.isNullOrEmpty()) {
                etNombre.error = "Nombre requerido"
                return false
            }
            if (etCedula.text.isNullOrEmpty() || etCedula.text.toString().toIntOrNull() == null) {
                etCedula.error = "Cédula debe ser un número válido"
                return false
            }
            if (etCorreo.text.isNullOrEmpty() || !Patterns.EMAIL_ADDRESS.matcher(etCorreo.text.toString()).matches()) {
                etCorreo.error = "Correo inválido"
                return false
            }
            if (etDireccion.text.isNullOrEmpty()) {
                etDireccion.error = "Dirección requerida"
                return false
            }
            if (etCelular.text.isNullOrEmpty()) {
                etCelular.error = "Celular requerido"
                return false
            }
            if (etCostoPorKm.text.isNullOrEmpty() || etCostoPorKm.text.toString().toDoubleOrNull() == null) {
                etCostoPorKm.error = "Costo por km inválido"
                return false
            }
        }
        return true
    }

    private fun registerRepartidor() {
        val repartidor = RepartidorRegistroRequest(
            cedula = binding.etCedula.text.toString(),
            nombre = binding.etNombre.text.toString(),
            correo = binding.etCorreo.text.toString(),
            direccion = binding.etDireccion.text.toString(),
            telefono = binding.etCelular.text.toString(),
            costo_por_km = binding.etCostoPorKm.text.toString().toDouble()
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiClient.instance.registrarRepartidor(repartidor)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@RepartidorRegisterActivity,
                            "Repartidor registrado exitosamente",
                            Toast.LENGTH_LONG
                        ).show()
                        finish()
                    } else {
                        val errorMessage = try {
                            response.errorBody()?.string() ?: "Error desconocido"
                        } catch (e: Exception) {
                            "Error al leer mensaje de error"
                        }
                        Toast.makeText(
                            this@RepartidorRegisterActivity,
                            "Error al registrar: $errorMessage",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@RepartidorRegisterActivity,
                        "Error de conexión: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}