package com.example.apppedidos.frontend.activities.repartidor
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apppedidos.R
import com.example.apppedidos.frontend.adapters.RepartidorAdapter
import com.example.apppedidos.frontend.api.ApiClient
import com.example.apppedidos.frontend.models.Repartidor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.apppedidos.databinding.ActivityRepartidorListBinding
import com.example.apppedidos.frontend.activities.menu.MenuActivity


class RepartidorListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRepartidorListBinding
    private lateinit var adapter: RepartidorAdapter
    private val repartidores = mutableListOf<Repartidor>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepartidorListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        loadRepartidores()
        onResume()
        setupListeners()

        binding.btnSalir.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }
    }



    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Lista de Repartidores"
    }


    private fun setupRecyclerView() {
        adapter = RepartidorAdapter(repartidores) { repartidor ->
            // Manejar clic en repartidor
            showRepartidorDetails(repartidor)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@RepartidorListActivity)
            adapter = this@RepartidorListActivity.adapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun loadRepartidores() {
        binding.progressBar.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiClient.instance.obtenerRepartidores()
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.GONE
                    if (response.isSuccessful) {
                        repartidores.clear()
                        response.body()?.repartidores?.let { repartidores.addAll(it) }
                        adapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(
                            this@RepartidorListActivity,
                            "Error al cargar repartidores",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        this@RepartidorListActivity,
                        "Error de conexión: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadRepartidores()
    }

    private fun showRepartidorDetails(repartidor: Repartidor) {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Detalles del Repartidor")
            .setMessage(
                """
            Nombre: ${repartidor.nombre}
            Cédula: ${repartidor.cedula}
            Estado: ${repartidor.estado.replaceFirstChar { it.uppercase() }}
            Distancia actual: ${"%.2f km".format(repartidor.distancia_pedido)}
            KM Recorridos hoy: ${"%.2f km".format(repartidor.km_recorridos_diarios)}
            Amonestaciones: ${repartidor.amonestaciones}
            Calificación: ${"%.1f".format(repartidor.calificacion_promedio)}/5
            Costo por km: ${"%.2f".format(repartidor.costo_por_km)}
            """.trimIndent()
            )
            .setPositiveButton("Amonestar") { _, _ ->
                asignarAmonestacion(repartidor.id_repartidor)
            }
            .setNegativeButton("Cerrar", null)
            .create()

        dialog.show()
    }

    private fun asignarAmonestacion(idRepartidor: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiClient.instance.asignarAmonestacion(idRepartidor)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@RepartidorListActivity,
                            "Amonestación asignada",
                            Toast.LENGTH_LONG
                        ).show()
                        loadRepartidores() // Recargar lista
                    } else {
                        Toast.makeText(
                            this@RepartidorListActivity,
                            "Amonestación asignada",
                            Toast.LENGTH_LONG
                        ).show()
                        loadRepartidores()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@RepartidorListActivity,
                        "Error de conexión: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun setupListeners() {
        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, RepartidorRegisterActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}