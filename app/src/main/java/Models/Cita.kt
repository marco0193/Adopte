package Models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "citas")
class Cita(val cliente:String,
           val fecha:String,
           val hora:String,
           val telefono:String,
           val tipo:String,
           val precio:Int,
           val descripcion:String,
           @PrimaryKey(autoGenerate = true) var idCita: Int = 0
): Serializable