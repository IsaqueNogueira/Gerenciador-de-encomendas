package com.example.gerenciadordeencomendas.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentOnAttachListener
import com.example.gerenciadordeencomendas.R
import com.example.gerenciadordeencomendas.model.Encomenda
import com.example.gerenciadordeencomendas.ui.activity.extensions.transacaoFragment
import com.example.gerenciadordeencomendas.ui.activity.fragment.ListaEncomendasFragment

class ListaEncomendasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_encomendas)
        title = "Minhas encomendas"
         if (savedInstanceState == null){
             abreListaEncomendas()
         }
    }

    init {
        val fm = supportFragmentManager

        val listener = FragmentOnAttachListener { fragmentManager, fragment ->
            if (fragment is ListaEncomendasFragment){
                fragment.quandoClicarSair = this::vaiParaLogin
                fragment.quandoClicarAdicionarEncomenda = this::vaiParaFormEncomenda
                fragment.quandoClicarNoItem = this::vaiParaDetalheEncomenda
            }
        }
        fm.addFragmentOnAttachListener(listener)
    }

    private fun abreListaEncomendas() {
        transacaoFragment {
            replace(R.id.activity_lista_encomendas_container, ListaEncomendasFragment())
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

    private fun vaiParaDetalheEncomenda(it: Encomenda) {
        val intent = Intent(
            this, DetalheEncomendaActivity::class.java
        ).apply {
            putExtra(CHAVE_ENCOMENDA_ID, it.firebaseId)
        }
        startActivity(intent)
    }

}