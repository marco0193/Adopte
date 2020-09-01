package com.example.firebase

import Libreria.MemoryData
import ViewModels.LoginViewModels
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.firebase.databinding.VerifyEmailBinding

class VerifyEmail: AppCompatActivity() {
    private var memoryData: MemoryData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.verify_email)

        //Creamos una instancia de memory data para ver si hay un dadto guardd en la memoria
        memoryData = MemoryData.getInstance(this)

        //Si el dato esta vacio mostramos la actividad de ingrear email
        if(memoryData!!.getData("user") == ""){
            var binding = DataBindingUtil.setContentView<VerifyEmailBinding>(this, R.layout.verify_email)
            binding.emailModel = LoginViewModels(this, binding, null)

            val window = this.window

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.colorBlue, null)
        }else{
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}