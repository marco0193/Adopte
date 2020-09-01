package ViewModels

import Models.User
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.firebase.R
import kotlinx.android.synthetic.main.item_usuario.view.*

class AdapterUsuarios(private val mContext: Context, private val listaUsers: List<User>) : ArrayAdapter<User>(mContext, 0, listaUsers) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.item_usuario, parent, false)

        val user = listaUsers[position]

        layout.usuario.text = user.usuario
        layout.correo.text = user.email
        layout.ivUsuario.setImageResource(user.imagen)

        return layout
    }
}