package com.example.firebase

import Libreria.AppDataBaseUsers
import Models.User
import ViewModels.AdapterUsuarios
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_ver_usuarios.*
import kotlinx.android.synthetic.main.activity_ver_usuarios.lista

class VerUsuarios : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_usuarios)

        var listaUsers = emptyList<User>()

        val database = AppDataBaseUsers.getDatabase(this)

        database.users().getAll().observe(this, Observer {
            listaUsers = it

            val adapter = AdapterUsuarios(this, listaUsers)

            lista.adapter = adapter
        })

        lista.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, UsersDetail::class.java)
            intent.putExtra("id", listaUsers[position].idUser)
            startActivity(intent)
        }
    }
}