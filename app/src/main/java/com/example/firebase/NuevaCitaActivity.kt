package com.example.firebase

import Libreria.AppDataBase
import Models.Cita
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TimePicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_nueva_cita.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class NuevaCitaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_cita)

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val mes = calendar.get(Calendar.MONTH)
        val dia = calendar.get(Calendar.DAY_OF_MONTH)

        bnFecha.setOnClickListener {
            val dpn = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{view, year, month, dayOfMonth ->
                fechaCita.setText(""+dayOfMonth+"/"+month+"/"+year)
            }, year, mes, dia)

            dpn.show()
        }

        bnHora.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener{view: TimePicker?, hourOfDay: Int, minute: Int ->
                //obtenemos la hora
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                //obtenemos los minutos
                calendar.set(Calendar.MINUTE, minute)

                horaCita.text = SimpleDateFormat("HH:mm").format(calendar.time).toString()
            }

            TimePickerDialog(this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
        }

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
            if(nombreCliente.text.isEmpty()){
                Toast.makeText(this, "Ingresar un nombre de cliente valido", Toast.LENGTH_SHORT).show()
            }else{

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
}