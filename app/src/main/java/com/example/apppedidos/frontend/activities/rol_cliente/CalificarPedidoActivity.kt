package com.example.apppedidos.frontend.activities.rol_cliente
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.apppedidos.R
import com.example.apppedidos.frontend.api.ApiClient
import com.example.apppedidos.frontend.models.CalificacionRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CalificarPedidoActivity : AppCompatActivity() {

    private val TAG = "CalificarPedidoActivity"
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
            val queja = false

            Log.d(TAG, "Datos a enviar: idPedido=$idPedido, idCliente=$idCliente, " +
                    "puntajeRep=$puntajeRepartidor, puntajeRes=$puntajeRestaurante, " +
                    "comentario=$comentario, queja=$queja")

            if (puntajeRepartidor == 0 || puntajeRestaurante == 0) {
                Toast.makeText(this, "Califique ambos aspectos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            GlobalScope.launch(Dispatchers.Main) {
                try {
                    val request = CalificacionRequest(
                        id_pedido = idPedido,
                        id_cliente = idCliente,
                        puntaje_repartidor = puntajeRepartidor,
                        puntaje_restaurante = puntajeRestaurante,
                        comentario = comentario,
                        queja = queja
                    )
                    Log.d(TAG, "Request creado: $request")


                    val response = ApiClient.instance.calificarPedido(request)
                    Log.d(TAG, "Respuesta recibida. Código: ${response.code()}, Cuerpo: ${response.body()}")

                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        Log.d(TAG, "Respuesta exitosa: $responseBody")
                        Toast.makeText(this@CalificarPedidoActivity, response.body()?.message ?: "Calificación enviada", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e(TAG, "Error en la respuesta: $errorBody")
                        Toast.makeText(this@CalificarPedidoActivity, "Error al calificar", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Excepción al calificar pedido", e)
                    Toast.makeText(this@CalificarPedidoActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}