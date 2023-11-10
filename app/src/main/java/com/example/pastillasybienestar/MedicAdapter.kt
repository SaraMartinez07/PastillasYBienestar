package com.example.pastillasybienestar

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MedicAdapter(private val context: Context, private val medicList: List<Medic>, var medicClickListener: MedicClickListener) :
    RecyclerView.Adapter<MedicAdapter.MedicViewHolder>() {

     interface MedicClickListener {
         fun onMedicSelected(medic: Medic)
     }

     // Variable para almacenar el oyente
     //var medicClickListener: MedicClickListener? = null

     inner class MedicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val idTextView: TextView = itemView.findViewById(R.id.ID)
         val nombreTextView: TextView = itemView.findViewById(R.id.nombre)
         val descripcionTextView: TextView = itemView.findViewById(R.id.descripcion)
         val imagenImageView: ImageView = itemView.findViewById(R.id.imagenImageView)
         val checkbox: CheckBox = itemView.findViewById(R.id.checkbox)

         init {
             // Configurar el clic en el ViewHolder
             itemView.setOnClickListener {
                 val position = adapterPosition
                 if (position != RecyclerView.NO_POSITION) {
                     val selectedMedic = medicList[position]
                     // Notificar al oyente que se ha seleccionado un medicamento
                     medicClickListener?.onMedicSelected(selectedMedic)
                 }
             }

         }

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
         val bitmap =
             BitmapFactory.decodeByteArray(currentMedic.imagenBlob, 0, currentMedic.imagenBlob.size)
         holder.imagenImageView.setImageBitmap(bitmap)

         holder.checkbox.isChecked = currentMedic.isSelected

         // Configurar el cambio de estado del CheckBox
         holder.checkbox.setOnCheckedChangeListener { _, isChecked ->
             currentMedic.isSelected = isChecked
         }
     }

     fun setIsSelectedAllItems(isSelected: Boolean) {
         medicList.forEach { it.isSelected = isSelected }
         notifyDataSetChanged()
     }

     override fun getItemCount(): Int {
         return medicList.size
         }

 }