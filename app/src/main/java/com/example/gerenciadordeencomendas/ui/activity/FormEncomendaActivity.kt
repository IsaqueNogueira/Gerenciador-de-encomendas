package com.example.gerenciadordeencomendas.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import com.example.gerenciadordeencomendas.databinding.ActivityFormEncomendaBinding
import com.example.gerenciadordeencomendas.model.Encomenda
import com.example.gerenciadordeencomendas.repository.Repository
import com.example.gerenciadordeencomendas.utils.Utils
import java.util.*

class FormEncomendaActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormEncomendaBinding.inflate(layoutInflater)
    }
    private val repository by lazy {
        Repository()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        clicoubotaoVoltar()
        clicouBotaoAdicionarEncomenda()
    }

    private fun clicouBotaoAdicionarEncomenda() {
        binding.activityFormEncomendaBotao.setOnClickListener {
            val usuarioId = repository.auth.currentUser?.uid
            val codigoRastreio = binding.activityFormEncomendaCodigo.text.toString()
            val nomePacote = binding.activityFormEncomendaNomedopacote.text.toString()
            val dataAtualizado = Utils().dataHora()
            val dataCriado = Utils().dataHoraMillisegundos()
            val status = "Toque para atualizar"
            val firebaseId = ""
            if (!TextUtils.isEmpty(codigoRastreio) && !TextUtils.isEmpty(nomePacote)) {
                val encomenda = Encomenda(firebaseId, usuarioId.toString(), codigoRastreio, nomePacote, status, dataCriado, dataAtualizado)
                if (validaRastreio(codigoRastreio)) {
                    repository.salvarEncomenda(encomenda).addOnCompleteListener {
                        if (it.isSuccessful) {
                            finish()
                        } else {
                            binding.activityFormEncomendaMensagemErro.text =
                                "*Erro ao adicionar encomenda!"
                            Handler().postDelayed({
                                binding.activityFormEncomendaMensagemErro.text = ""
                            }, 4000)
                        }
                    }
                } else {
                    binding.activityFormEncomendaCodigo.setError("Código inválido")
                    binding.activityFormEncomendaCodigo.requestFocus()
                }
            }
            when {
                codigoRastreio.isEmpty() -> {
                    binding.activityFormEncomendaCodigo.setError("Campo obrigatório")
                    binding.activityFormEncomendaCodigo.requestFocus()
                }
                nomePacote.isEmpty() -> {
                    binding.activityFormEncomendaNomedopacote.setError("Campo obrigatório!")
                    binding.activityFormEncomendaNomedopacote.requestFocus()
                }
            }

        }
    }

    private fun clicoubotaoVoltar() {
        binding.activityFormEncomendaVoltar.setOnClickListener {
            finish()
        }
    }

    private fun validaRastreio(input: String): Boolean {
        val regex = Regex(pattern = "^[A-Z]{2}[0-9]{9}[A-Z]{2}\$", options = setOf(RegexOption.IGNORE_CASE))
        return regex.matches(input)
    }
}