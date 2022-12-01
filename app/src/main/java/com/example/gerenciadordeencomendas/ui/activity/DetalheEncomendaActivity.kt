package com.example.gerenciadordeencomendas.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentOnAttachListener
import com.example.gerenciadordeencomendas.R
import com.example.gerenciadordeencomendas.ui.activity.extensions.transacaoFragment
import com.example.gerenciadordeencomendas.ui.activity.fragment.DetalheEncomendaFragment
import com.example.gerenciadordeencomendas.ui.activity.fragment.ListaEncomendasFragment

class DetalheEncomendaActivity : AppCompatActivity() {

    private var encomendaId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe_encomenda)
        supportActionBar?.hide()
        tentaCarregarEncomenda()
        abreDetalheEncomenda()
    }

    init {
        val fm = supportFragmentManager

        val listener = FragmentOnAttachListener { fragmentManager, fragment ->
            if (fragment is DetalheEncomendaFragment){
                fragment.botaoVoltar = this::finish

            }
        }
        fm.addFragmentOnAttachListener(listener)
    }

    private fun abreDetalheEncomenda(){
        val fragment = DetalheEncomendaFragment()
        val dados = Bundle()
        dados.putString(CHAVE_ENCOMENDA_ID, encomendaId)
        fragment.arguments = dados
        transacaoFragment {
            replace(R.id.activity_detalhe_encomenda_container, fragment)
        }
    }

    private fun tentaCarregarEncomenda() {
        val intent = getIntent()
        if (intent.hasExtra(CHAVE_ENCOMENDA_ID)) {
            encomendaId = intent.getStringExtra(CHAVE_ENCOMENDA_ID).toString()
        }

    }

}