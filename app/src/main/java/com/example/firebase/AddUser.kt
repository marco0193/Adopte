package com.example.firebase

import Libreria.AppDataBaseUsers
import Models.User
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_user.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        var idUser : Int? = null

        if (intent.hasExtra("user")) {
            val user = intent.extras?.getSerializable("user") as User

            etUsuario.setText(user.usuario)
            etCorreo.setText(user.email)
            etPassword1.setText(user.password)
            etPasswordVerif.setText(user.password)
            idUser = user.idUser
        }

        val database = AppDataBaseUsers.getDatabase(this)

        bnSave.setOnClickListener {
            val usuario = etUsuario.text.toString()
            val correo = etCorreo.text.toString()
            val password = etPassword1.text.toString()
            val passwordVerif = etPasswordVerif.text.toString()

            val user = User(usuario, correo, password, R.drawable.ic_launcher_background)

            if(idUser != null){
                CoroutineScope(Dispatchers.IO).launch {
                    user.idUser = idUser

                    database.users().update(user)

                    this@AddUser.finish()
                }
            }else{
                CoroutineScope(Dispatchers.IO).launch {
                    database.users().insertAll(user)

                    this@AddUser.finish()
                }
            }
        }
    }
}