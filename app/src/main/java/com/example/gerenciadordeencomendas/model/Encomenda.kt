package com.example.gerenciadordeencomendas.model

data class Encomenda(
    val usuarioId: String,
    val codigoRastreio: String,
    val nomePacote: String,
    val status: String,
    val dataCriado: Long,
    val dataAtualizado: String
)