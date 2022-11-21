package com.example.gerenciadordeencomendas.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.example.gerenciadordeencomendas.R
import com.example.gerenciadordeencomendas.adapters.ListaEncomendasAdapter
import com.example.gerenciadordeencomendas.databinding.ActivityListaEncomendasBinding
import com.example.gerenciadordeencomendas.repository.Repository
import com.google.firebase.auth.FirebaseAuth

class ListaEncomendasActivity : AppCompatActivity() {

    private val adapter = ListaEncomendasAdapter(this)

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lista_encomendas, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        AlertDialog.Builder(this)
            .setTitle("Deseja realmente sair?")
            .setPositiveButton("Sim"){ _,_ ->
                repository.auth.signOut()
                vaiParaLogin()
            }
            .setNegativeButton("Não"){_,_->}
            .show()
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        configuraRecyclerview()
    }

    private fun configuraRecyclerview() {
        repository.buscaTodasEncomendas()
        repository.liveDataEncomenda.observe(this, Observer { encomenda ->
            adapter.atualiza(encomenda)
            val recyclerView = binding.activityListaEncomendaRecyclerview
            recyclerView.adapter = adapter
        })
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
    private fun vaiParaLogin() {
       Intent(this, LoginActivity::class.java).apply {
           startActivity(this)
           finish()
       }
    }

}