package com.example.gerenciadordeencomendas.repository

import android.util.Log
import com.example.gerenciadordeencomendas.model.Encomenda
import com.example.gerenciadordeencomendas.model.Usuario
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class Repository {


    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()


    fun cadastraUsuario(usuario: Usuario): Task<AuthResult> {
      val resultado =  auth.createUserWithEmailAndPassword(usuario.email, usuario.senha).addOnCompleteListener {
        }
        return resultado
    }

     fun salvarNomeDoUsuario(nome: String): Task<Void> {
        val user: MutableMap<String, Any> = HashMap()
        user["nome"] = nome
         val usuarioId = FirebaseAuth.getInstance().currentUser?.uid
            val documentReference = db.collection("Usuarios").document(usuarioId.toString())
          return  documentReference.set(user).addOnSuccessListener {
                Log.d(
                    "db",
                    "Sucesso ao salvar os dados"
                )
            }.addOnFailureListener { e -> Log.d("db_error", "Erros so salvar os dados$e") }

    }


    fun salvarEncomenda(encomenda: Encomenda): Task<Void> {
      val documentReference =  db.collection("Encomendas").document()
       return documentReference.set(encomenda).addOnSuccessListener {
            Log.i("Salvar encomenda", "salvarEncomenda: Sucesso ao salvar encomenda")
        }

    }

}