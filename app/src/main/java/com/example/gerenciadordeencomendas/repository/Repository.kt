package com.example.gerenciadordeencomendas.repository

import android.util.Log
import com.example.gerenciadordeencomendas.model.Usuario
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Repository {

    val usuarioId = FirebaseAuth.getInstance().currentUser?.uid.toString()
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()


    fun cadastraUsuario(usuario: Usuario): Task<AuthResult> {
      val resultado =  auth.createUserWithEmailAndPassword(usuario.email, usuario.senha).addOnCompleteListener {
            if (it.isSuccessful){
                salvarNomeDoUsuario(usuario.nome)
                Log.i("cadastro", "cadastraUsuario: sucesso")
            }
        }
        return resultado
    }

    private fun salvarNomeDoUsuario(nome: String) {
        val user: MutableMap<String, Any> = HashMap()
        user["nome"] = nome
        val documentReference = db.collection("Usuarios").document(usuarioId)
        documentReference.set(user).addOnSuccessListener {
            Log.d(
                "db",
                "Sucesso ao salvar os dados"
            )
        }.addOnFailureListener { e -> Log.d("db_error", "Erros so salvar os dados$e") }
    }


    private fun salvarEncomenda(){
        db.collection("Encomendas")
    }

}