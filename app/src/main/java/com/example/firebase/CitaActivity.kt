package com.example.firebase

import Libreria.AppDataBase
import Models.Cita
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_cita.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CitaActivity : AppCompatActivity() {

    private lateinit var database: AppDataBase
    private lateinit var cita: Cita
    private lateinit var citaLiveData: LiveData<Cita>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cita)

        database = AppDataBase.getDatabase(this)

        val idCita = intent.getIntExtra("id", 0)

        citaLiveData = database.citas().get(idCita)

        citaLiveData.observe(this, Observer {
            cita = it

            textoValorCliente.text = cita.cliente
            textoValorFecha.text = cita.fecha
            textoValorHora.text = cita.hora+" hrs"
            textoValorTelefono.text = cita.telefono
            textoValorTipo.text = cita.tipo
            textoValorPrecio.text = cita.precio.toString()+"$"
            textoValorDescripcion.text = cita.descripcion
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cita_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.edit_item -> {
                val intent = Intent(this, NuevaCitaActivity::class.java)
                intent.putExtra("cita", cita)
                startActivity(intent)
            }

            R.id.deleta_item ->{
                citaLiveData.removeObservers(this)

                CoroutineScope(Dispatchers.IO).launch {
                    database.citas().delete(cita)
                    this@CitaActivity.finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

}