package com.example.pastillasybienestar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AlarmaAdapter (private val context: Context, private val alarmaList: List<Alarm>, var alarmaClickListener: AlarmaClickListener) :
    RecyclerView.Adapter<AlarmaAdapter.AlarmaViewHolder>() {

    interface AlarmaClickListener {
        fun onAlarmaSelected(alarma: Alarm)
    }

    inner class AlarmaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombTextView: TextView = itemView.findViewById(R.id.nomMedicamento)
        //val horaTextView: TextView = itemView.findViewById(R.id.hora)
        val fechaTextView: TextView = itemView.findViewById(R.id.fecha)

        init {
            // Configurar el clic en el ViewHolder
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val selectedAlarma = alarmaList[position]
                    // Notificar al oyente que se ha seleccionado una alarma
                    alarmaClickListener?.onAlarmaSelected(selectedAlarma)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: AlarmaViewHolder, position: Int) {
        val currentAlarma = alarmaList[position]

        holder.nombTextView.text = currentAlarma.nomMedicamento
        //holder.horaTextView.text = currentAlarma.hora
        holder.fechaTextView.text = currentAlarma.fecha
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmaViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_alarm, parent, false)
        return AlarmaViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return alarmaList.size
        }
}