package com.example.gerenciadordeencomendas.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gerenciadordeencomendas.databinding.ActivityFormEncomendaBinding

class FormEncomendaActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormEncomendaBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        clicoubotaoVoltar()


    }

    private fun clicoubotaoVoltar() {
        binding.activityFormEncomendaVoltar.setOnClickListener {
            finish()
        }
    }
}