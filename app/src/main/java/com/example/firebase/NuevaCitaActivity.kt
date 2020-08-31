package com.example.firebase

import Libreria.AppDataBase
import Models.Cita
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_nueva_cita.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NuevaCitaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_cita)

        var idCita:Int? = null

        if(intent.hasExtra("cita")){
            val cita = intent.extras?.getSerializable("cita") as Cita

            nombreCliente.setText(cita.cliente)
            fechaCita.setText(cita.fecha)
            horaCita.setText(cita.hora)
            telefono.setText(cita.telefono)
            tipo.setText(cita.tipo)
            precio.setText(cita.precio.toString())
            descripcion.setText(cita.descripcion)

            idCita = cita.idCita

        }

        val database = AppDataBase.getDatabase(this)

        bGuardar.setOnClickListener{
            val cliente = nombreCliente.text.toString()
            val fecha = fechaCita.text.toString()
            val hora = horaCita.text.toString()
            val telefono = telefono.text.toString()
            val tipo = tipo.text.toString()
            val precio = precio.text.toString().toInt()
            val descripcion = descripcion.text.toString()

            val cita = Cita(cliente, fecha, hora, telefono, tipo, precio, descripcion)

            if (idCita != null){
                CoroutineScope(Dispatchers.IO).launch {
                    cita.idCita = idCita

                    database.citas().update(cita)

                    this@NuevaCitaActivity.finish()
                }
            }else{
                CoroutineScope(Dispatchers.IO).launch {
                    database.citas().insertAll(cita)
                    this@NuevaCitaActivity.finish()
                }
            }
        }
    }
}