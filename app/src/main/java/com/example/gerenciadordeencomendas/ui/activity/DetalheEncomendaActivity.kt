package com.example.gerenciadordeencomendas.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.gerenciadordeencomendas.R
import com.example.gerenciadordeencomendas.adapters.DetalheEncomendaAdapter
import com.example.gerenciadordeencomendas.databinding.ActivityDetalheEncomendaBinding
import com.example.gerenciadordeencomendas.repository.Repository
import com.example.gerenciadordeencomendas.utils.Utils
import com.example.gerenciadordeencomendas.webcliente.model.Evento
import com.example.gerenciadordeencomendas.webcliente.model.RastreioWebCliente
import kotlinx.coroutines.launch

class DetalheEncomendaActivity : AppCompatActivity() {

    private var encomendaId = ""

    private val adpter = DetalheEncomendaAdapter(this)

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
        clicouBotaoVoltar()

    }

    private fun clicouBotaoVoltar() {
        binding.activityDetalheEncomendaVoltar.setOnClickListener {
            finish()
        }
    }

    private fun mostraEncomenda() {
        mostraProgressbar()
        repository.buscaEncomendaPorId(encomendaId)
        repository.liveDataEncomendaId.observe(this, Observer {
            lifecycleScope.launch {
                val nomePacote = binding.activityDetalheEncomendaNomePacote
                nomePacote.text = it.nomePacote
                val codigoRastreio = binding.activityDetalheEncomendaCodigoRastreio
                codigoRastreio.text = it.codigoRastreio
                val mensagemErro = binding.activityDetalheEncomendaMensagemErro
                val iconeErro = binding.activityDetalheEncomendaIconErro

                val user = "isaquecross15@gmail.com"
                val token = "0a40c26417782427548f2aeb57f74c4038faf1f26ac662379425e35c848cce2b"
                val codigo = it.codigoRastreio
                val rastreio = webCliente.buscaRastreio(user, token, codigo)
                if (rastreio.quantidade != 0L) {
                    if (rastreio.eventos.size != 0) {
                        val tamanhoEvento = rastreio.eventos.size - 1
                        val primeiroStatus = rastreio.eventos.get(tamanhoEvento)
                        val ultimoStatus = rastreio.eventos.get(0)
                        if (ultimoStatus.status == "Objeto entregue ao destinatário") {
                            binding.activityDetalheEncomendaCirculo.setBackgroundResource(R.drawable.view_circular_verde)
                            binding.activityDetalheEncomendaIcon.setBackgroundResource(R.drawable.ic_packageentregue)
                        }
                        diasDePostagem(ultimoStatus, primeiroStatus)

                        adpter.atualiza(rastreio.eventos, ultimoStatus, encomendaId, primeiroStatus)
                        val recyclerView = binding.activityDetalheEncomendaRecyclerview
                        recyclerView.adapter = adpter
                        ocultaProgressbar()
                    }
                } else {
                    ocultaProgressbar()
                    mensagemErro.visibility = View.VISIBLE
                    iconeErro.visibility = View.VISIBLE
                    codigoRastreio.setTextColor(Color.RED)
                }
            }
        })

    }

    private fun diasDePostagem(ultimoStatus: Evento, primeiroStatus: Evento) {
        val dataPostagem = primeiroStatus.data
        var dataHoje = Utils().data()
        if (ultimoStatus.status == "Objeto entregue ao destinatário"){
            dataHoje = ultimoStatus.data
        }
        val diasEnviado = Utils().dias(dataPostagem, dataHoje)
        var dia: String
        var textoEnviado: String
        if (diasEnviado < 2) {
            dia = "dia"
        } else {
            dia = "dias"
        }

        if (ultimoStatus.status == "Objeto entregue ao destinatário") {
            textoEnviado = "Entregue em:"
        } else {
            textoEnviado = "Enviado há:"
        }
        binding.activityDetalheEncomendaDiasenviado.text = "$textoEnviado $diasEnviado $dia"
    }

    private fun ocultaProgressbar() {
        binding.activityDetalheEncomendaProgressbar.visibility = View.GONE
    }

    private fun mostraProgressbar() {
        binding.activityDetalheEncomendaProgressbar.visibility = View.VISIBLE
    }

    private fun tentaCarregarEncomenda() {
        val intent = getIntent()
        if (intent.hasExtra(CHAVE_ENCOMENDA_ID)) {
            encomendaId = intent.getStringExtra(CHAVE_ENCOMENDA_ID).toString()
        }

    }
}