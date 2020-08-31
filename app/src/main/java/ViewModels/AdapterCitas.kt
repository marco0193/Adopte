package ViewModels

import Models.Cita
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.firebase.R
import kotlinx.android.synthetic.main.item_cita.view.*

class AdapterCitas(private val mContext: Context, private val listaCitas: List<Cita>): ArrayAdapter<Cita>(mContext, 0, listaCitas) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.item_cita, parent, false)

        val cita = listaCitas[position]

        layout.nombreCliente.text = cita.cliente
        layout.fecha.text = cita.fecha
        layout.hora.text = cita.hora
        layout.imageView.setImageResource(R.mipmap.icon_nota)

        return layout
    }
}