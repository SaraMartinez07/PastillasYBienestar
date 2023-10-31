package com.example.pastillasybienestar

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MedicAdapter(private val context: Context, private val medicList: List<Medic>) :
    RecyclerView.Adapter<MedicAdapter.MedicViewHolder>() {

    class MedicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idTextView: TextView = itemView.findViewById(R.id.ID)
        val nombreTextView: TextView = itemView.findViewById(R.id.nombre)
        val descripcionTextView: TextView = itemView.findViewById(R.id.descripcion)
        val imagenImageView: ImageView = itemView.findViewById(R.id.imagenImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_medic, parent, false)
        return MedicViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MedicViewHolder, position: Int) {
        val currentMedic = medicList[position]

        holder.idTextView.text = currentMedic.id.toString()
        holder.nombreTextView.text = currentMedic.nombre
        holder.descripcionTextView.text = currentMedic.descripcion

        // Decodificar el array de bytes y asignar la imagen a ImageView
        val bitmap = BitmapFactory.decodeByteArray(currentMedic.imagenBlob, 0, currentMedic.imagenBlob.size)
        holder.imagenImageView.setImageBitmap(bitmap)
    }

    override fun getItemCount(): Int {
        return medicList.size
    }
}