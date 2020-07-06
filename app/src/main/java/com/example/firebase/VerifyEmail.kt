package com.example.firebase

import ViewModels.LoginViewModels
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.firebase.databinding.VerifyEmailBinding

class VerifyEmail: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.verify_email)

        var binding = DataBindingUtil.setContentView<VerifyEmailBinding>(this, R.layout.verify_email)
        binding.emailModel = LoginViewModels(this, binding, null)

        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.colorBlue, null)
    }
}