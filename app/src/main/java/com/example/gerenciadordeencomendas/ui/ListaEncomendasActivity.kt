package com.example.gerenciadordeencomendas.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gerenciadordeencomendas.R
import com.example.gerenciadordeencomendas.databinding.ActivityListaEncomendasBinding
import com.example.gerenciadordeencomendas.repository.Repository
import com.google.firebase.auth.FirebaseAuth

class ListaEncomendasActivity : AppCompatActivity() {

    private val repository by lazy {
        Repository()
    }
    private val binding by lazy {
        ActivityListaEncomendasBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Minha encomendas"
        clicouBotaoAdicionarPacote()
    }

    private fun clicouBotaoAdicionarPacote() {
        binding.activityListaEncomendaBotao.setOnClickListener {
            vaiParaFormEncomenda()
        }
    }

    private fun vaiParaFormEncomenda() {
        Intent(this, FormEncomendaActivity::class.java).apply {
            startActivity(this)
        }
    }
}