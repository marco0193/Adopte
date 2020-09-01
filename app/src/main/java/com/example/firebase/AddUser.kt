package com.example.firebase

import Controls.ImageController
import Libreria.AppDataBaseUsers
import Models.User
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_user.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddUser : AppCompatActivity() {

    private val SELECT_ACTIVITY = 50
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        var idUser : Int? = null

        if (intent.hasExtra("user")) {
            val user = intent.extras?.getSerializable("user") as User

            etUsuario.setText(user.usuario)
            etCorreo.setText(user.email)
            etPassword1.setText(user.password)
            idUser = user.idUser

            val imageUri = ImageController.getImageUri(this, idUser.toLong())
            ivUser.setImageURI(imageUri)
        }

        val database = AppDataBaseUsers.getDatabase(this)

        bnSave.setOnClickListener {
            //Comprobamos que este lleno el campo del nombre del usuario
            if(etUsuario.text.isEmpty()){
                Toast.makeText(this, "Ingresar nombre del usuario", Toast.LENGTH_SHORT).show()
            }else{
                //comrpbamos que el campo correo tenga valores
                if(etCorreo.text.isEmpty()){
                    Toast.makeText(this, "Ingresar un email", Toast.LENGTH_SHORT).show()
                }else{
                    //validamos que password tenga valor
                    if(etPassword1.text.isEmpty()){
                        Toast.makeText(this, "No ingreso una contraseña", Toast.LENGTH_SHORT).show()
                    }else{
                        //validamos la otra password
                        if(etPassword1.text.length < 6){
                            Toast.makeText(this, "La contrasela es muy pequeña", Toast.LENGTH_SHORT).show()
                        }else{
                            val usuario = etUsuario.text.toString()
                            val correo = etCorreo.text.toString()
                            val password = etPassword1.text.toString()

                            val user = User(usuario, correo, password, R.mipmap.ic_user)

                            if(idUser != null){
                                CoroutineScope(Dispatchers.IO).launch {
                                    user.idUser = idUser

                                    database.users().update(user)

                                    imageUri?.let{
                                        val intent = Intent()
                                        intent.data = it
                                        setResult(Activity.RESULT_OK, intent)
                                        ImageController.saveImage(this@AddUser, idUser.toLong(), it)
                                    }

                                    this@AddUser.finish()
                                }
                            }else{
                                CoroutineScope(Dispatchers.IO).launch {
                                    val id = database.users().insertAll(user)[0]

                                    imageUri?.let{
                                        ImageController.saveImage(this@AddUser, id, it)
                                    }


                                    this@AddUser.finish()
                                }
                            }
                        }
                    }
                }
            }
        }

        ivUser.setOnClickListener {
            ImageController.selectPhotoFromGallery(this, SELECT_ACTIVITY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when{
            requestCode == SELECT_ACTIVITY && resultCode == Activity.RESULT_OK ->{
                imageUri = data!!.data

                ivUser.setImageURI(imageUri)
            }
        }
    }
}