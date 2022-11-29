package com.example.gerenciadordeencomendas.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentOnAttachListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.gerenciadordeencomendas.R
import com.example.gerenciadordeencomendas.adapters.ListaEncomendasAdapter
import com.example.gerenciadordeencomendas.databinding.ActivityListaEncomendasBinding
import com.example.gerenciadordeencomendas.model.Encomenda
import com.example.gerenciadordeencomendas.repository.Repository
import com.example.gerenciadordeencomendas.ui.activity.extensions.transacaoFragment
import com.example.gerenciadordeencomendas.ui.activity.fragment.ListaEncomendasFragment
import com.example.gerenciadordeencomendas.ui.activity.viewmodel.ListaEncomendasViewModel
import com.example.gerenciadordeencomendas.ui.activity.viewmodel.factory.ListaEncomendasViewModelFactory

private const val TAG_FRAGMENT_LISTA_ENCOMENDAS = "listaEncomendas"
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