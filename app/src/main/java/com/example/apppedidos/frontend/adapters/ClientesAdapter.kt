package com.example.apppedidos.frontend.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apppedidos.R
import com.example.apppedidos.frontend.models.Cliente

class ClientesAdapter(private val clientes: List<Cliente>) :
    RecyclerView.Adapter<ClientesAdapter.ClienteViewHolder>() {

    inner class ClienteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        val tvCedula: TextView = itemView.findViewById(R.id.tvCedula)
        val tvTelefono: TextView = itemView.findViewById(R.id.tvTelefono)
        val tvCorreo: TextView = itemView.findViewById(R.id.tvCorreo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cliente, parent, false)
        return ClienteViewHolder(view)
    }

    override fun getItemCount() = clientes.size

    override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) {
        val cliente = clientes[position]
        holder.tvNombre.text = cliente.nombre
        holder.tvCedula.text = "Cédula: ${cliente.cedula}"
        holder.tvTelefono.text = "Tel: ${cliente.telefono}"
        holder.tvCorreo.text = "Correo: ${cliente.correo}"
    }
}
