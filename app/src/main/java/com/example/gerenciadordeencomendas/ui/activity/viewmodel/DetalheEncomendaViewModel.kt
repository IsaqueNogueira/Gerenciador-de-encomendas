package com.example.gerenciadordeencomendas.ui.activity.viewmodel

import androidx.lifecycle.ViewModel
import com.example.gerenciadordeencomendas.repository.Repository

class DetalheEncomendaViewModel(
    private val repository: Repository
): ViewModel() {

    val liveDataEncomendaId = repository.liveDataEncomendaId

    fun buscaEncomendaPorId(encomendaId: String) {
        repository.buscaEncomendaPorId(encomendaId)
    }



}