package com.example.gerenciadordeencomendas.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.gerenciadordeencomendas.databinding.ActivityDetalheEncomendaBinding
import com.example.gerenciadordeencomendas.repository.Repository
import com.example.gerenciadordeencomendas.webcliente.model.RastreioWebCliente
import kotlinx.coroutines.launch

class DetalheEncomendaActivity : AppCompatActivity() {

    private var encomendaId = ""
    private val binding by lazy {
        ActivityDetalheEncomendaBinding.inflate(layoutInflater)
    }
    private val repository by lazy {
        Repository()
    }

    private val webCliente by lazy {
        RastreioWebCliente()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        tentaCarregarEncomenda()
        mostraEncomenda()


     lifecycleScope.launch {
         val user = "isaquecross15@gmail.com"
         val token = "0a40c26417782427548f2aeb57f74c4038faf1f26ac662379425e35c848cce2b"
         val codigo = "NL265842915BR"
          val rastreio = webCliente.buscaRastreio(user, token, codigo)
         Log.i("TAG", "onCreate: $rastreio")
     }

    }

    private fun mostraEncomenda() {
        repository.buscaEncomendaPorId(encomendaId)
        repository.liveDataEncomendaId.observe(this, Observer {
            val nomePacote = binding.activityDetalheEncomendaNomePacote
            nomePacote.text = it.nomePacote

            val codigoRastreio = binding.activityDetalheEncomendaCodigoRastreio
            codigoRastreio.text = it.codigoRastreio
        })
    }

    private fun tentaCarregarEncomenda() {
        val intent = getIntent()
        if (intent.hasExtra(CHAVE_ENCOMENDA_ID)){
            encomendaId = intent.getStringExtra(CHAVE_ENCOMENDA_ID).toString()
        }

    }
}