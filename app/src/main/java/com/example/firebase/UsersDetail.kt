package com.example.firebase

import Controls.ImageController
import Libreria.AppDataBaseUsers
import Models.User
import android.app.Activity
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
    private val EDIT_ACTIVITY = 49

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_detail)

        database = AppDataBaseUsers.getDatabase(this)

        val idUser = intent.getIntExtra("id", 0)

        val imageUri = ImageController.getImageUri(this, idUser.toLong())
        ivUser.setImageURI(imageUri)

        userLiveData = database.users().get(idUser)

        userLiveData.observe(this, Observer{
            user = it

            tvUsuario.text = user.usuario
            tvCorreo.text = user.email
            tvPassword.text = user.password
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
                startActivityForResult(intent, EDIT_ACTIVITY)
            }

            R.id.deleta_item -> {
                userLiveData.removeObservers(this)

                CoroutineScope(Dispatchers.IO).launch {
                    database.users().delete(user)
                    ImageController.daleteImage(this@UsersDetail, user.idUser.toLong())
                    this@UsersDetail.finish()
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when{
            requestCode == EDIT_ACTIVITY && resultCode == Activity.RESULT_OK ->{
                ivUser.setImageURI(data!!.data)
            }
        }
    }
}