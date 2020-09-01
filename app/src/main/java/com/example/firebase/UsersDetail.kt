package com.example.firebase

import Libreria.AppDataBaseUsers
import Models.User
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_users_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsersDetail : AppCompatActivity() {

    private lateinit var database:AppDataBaseUsers
    private lateinit var user: User
    private lateinit var userLiveData:LiveData<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_detail)

        database = AppDataBaseUsers.getDatabase(this)

        val idUser = intent.getIntExtra("id", 0)

        userLiveData = database.users().get(idUser)

        userLiveData.observe(this, Observer{
            user = it

            tvUsuario.text = user.usuario
            tvCorreo.text = user.email
            tvPassword.text = user.password
            tvPasswordVerif.text = user.password
            ivUser.setImageResource(user.imagen)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.users_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit_item -> {
                val intent = Intent(this, AddUser::class.java)
                intent.putExtra("user", user)
                startActivity(intent)
            }

            R.id.deleta_item -> {
                userLiveData.removeObservers(this)

                CoroutineScope(Dispatchers.IO).launch {
                    database.users().delete(user)
                    this@UsersDetail.finish()
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }
}