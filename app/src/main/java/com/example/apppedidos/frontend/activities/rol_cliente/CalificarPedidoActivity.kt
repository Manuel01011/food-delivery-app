package com.example.apppedidos.frontend.activities.rol_cliente
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.apppedidos.R
import com.example.apppedidos.frontend.api.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CalificarPedidoActivity : AppCompatActivity() {

    private lateinit var ratingRepartidor: RatingBar
    private lateinit var ratingRestaurante: RatingBar
    private lateinit var etComentario: EditText
    private lateinit var btnEnviar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calificar_pedido)

        val idPedido = intent.getIntExtra("id_pedido", -1)
        val idCliente = intent.getIntExtra("id_cliente", -1)

        ratingRepartidor = findViewById(R.id.ratingRepartidor)
        ratingRestaurante = findViewById(R.id.ratingRestaurante)
        etComentario = findViewById(R.id.etComentario)
        btnEnviar = findViewById(R.id.btnEnviarCalificacion)

        btnEnviar.setOnClickListener {
            val puntajeRepartidor = ratingRepartidor.rating.toInt()
            val puntajeRestaurante = ratingRestaurante.rating.toInt()
            val comentario = etComentario.text.toString()
            val queja = false // Puedes agregar un checkbox para esto

            if (puntajeRepartidor == 0 || puntajeRestaurante == 0) {
                Toast.makeText(this, "Califique ambos aspectos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            GlobalScope.launch(Dispatchers.Main) {
                try {
                    val request = mapOf(
                        "id_pedido" to idPedido,
                        "id_cliente" to idCliente,
                        "puntaje_repartidor" to puntajeRepartidor,
                        "puntaje_restaurante" to puntajeRestaurante,
                        "comentario" to comentario,
                        "queja" to queja
                    )

                    val response = ApiClient.instance.calificarPedido(request)
                    if (response.isSuccessful) {
                        Toast.makeText(this@CalificarPedidoActivity, "Calificación enviada", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@CalificarPedidoActivity, "Error al calificar", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@CalificarPedidoActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}