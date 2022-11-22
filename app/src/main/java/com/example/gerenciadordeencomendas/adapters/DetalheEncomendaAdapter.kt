package com.example.gerenciadordeencomendas.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gerenciadordeencomendas.databinding.ItemRastreioBinding
import com.example.gerenciadordeencomendas.repository.Repository
import com.example.gerenciadordeencomendas.webcliente.model.Evento
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class DetalheEncomendaAdapter(
    private val context: Context,
    evento: List<Evento> = emptyList()
) : RecyclerView.Adapter<DetalheEncomendaAdapter.ViewHolder>() {

    private val evento = evento.toMutableList()
    private lateinit var ultimoStatus: Evento
    private lateinit var primeiroStatus: Evento
    private lateinit var encomendaId: String

    private val repository by lazy {
        Repository()
    }

    inner class ViewHolder(private val binding: ItemRastreioBinding)
        : RecyclerView.ViewHolder(binding.root) {

        private lateinit var evento: Evento



        fun vincula(evento: Evento) {
            this.evento = evento

            val status = binding.itemRastreioStatus
            status.text = evento.status

            val subStatus = binding.itemRastreioSubstatus
            if(evento.status != "Objeto recebido pelos Correios do Brasil") {
                subStatus.text = evento.subStatus.joinToString("\n")
            }else{
                subStatus.text = "Local: " +evento.local
            }

            if(evento.subStatus.isEmpty()){
                subStatus.text = "Local: " + evento.local
            }

            val local = binding.itemRastreioLocal
            local.text = evento.local

            val data = binding.itemRastreioData
            data.text = evento.data

            val hora = binding.itemRastreioHora
            hora.text = evento.hora

            repository.atualizaStatus(encomendaId, ultimoStatus.status)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ItemRastreioBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val evento = evento[position]
        holder.vincula(evento)
    }

    override fun getItemCount(): Int = evento.size

    fun atualiza(evento: List<Evento>, ultimoStatus : Evento, encomendaId: String, primeiroStatus: Evento) {
        this.ultimoStatus = ultimoStatus
        this.primeiroStatus = primeiroStatus
        this.encomendaId = encomendaId
        this.evento.clear()
        this.evento.addAll(evento)
        notifyDataSetChanged()
    }
}