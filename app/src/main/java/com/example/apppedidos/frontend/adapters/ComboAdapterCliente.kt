package com.example.apppedidos.frontend.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apppedidos.R
import com.example.apppedidos.frontend.models.Combo

class ComboAdapterCliente(
    private val combos: List<Combo>,
    private val idRestaurante: Int,
    private val onComboSelected: (Combo) -> Unit
) : RecyclerView.Adapter<ComboAdapterCliente.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.tvNombreCombo)
        val descripcion: TextView = view.findViewById(R.id.tvDescripcionCombo)
        val precio: TextView = view.findViewById(R.id.tvPrecioCombo)
        val imagen: ImageView = view.findViewById(R.id.imgCombo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_combo, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = combos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val combo = combos[position]
        holder.nombre.text = combo.nombre
        holder.descripcion.text = combo.descripcion
        holder.precio.text = "₡${combo.precio}"

        // Cargar imagen desde recursos drawable sin usar librerías externas
        val context = holder.itemView.context
        val resourceId = context.resources.getIdentifier(
            "combo_${combo.id_combo}", // Nombre del recurso en drawable
            "drawable",
            context.packageName
        )

        if (resourceId != 0) {
            holder.imagen.setImageResource(resourceId)
        } else {
            // Imagen por defecto si no se encuentra la específica
            holder.imagen.setImageResource(R.drawable.bu2)
        }

        // Manejar clic en el combo
        holder.itemView.setOnClickListener {
            onComboSelected(combo)
        }
    }
}