package com.example.firebase

import Libreria.AppDataBase
import Models.Cita
import ViewModels.AdapterCitas
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var listaCitas = emptyList<Cita>()

        val database = AppDataBase.getDatabase(this)

        database.citas().getAll().observe(this, Observer {
            listaCitas = it

            val adapter = AdapterCitas(this, listaCitas)

            lista.adapter = adapter
        })

        lista.setOnItemClickListener{parent, view, position, id ->
            val intent = Intent(this, CitaActivity::class.java)
            intent.putExtra("id", listaCitas[position].idCita)
            startActivity(intent)
        }

        floatingActionButton.setOnClickListener {
            val intent = Intent(this, NuevaCitaActivity::class.java)
            startActivity(intent)
        }
    }


}