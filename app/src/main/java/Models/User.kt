package Models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "users")
class User(val usuario:String,
           val email:String,
           val password:String,
           val imagen:Int,
           @PrimaryKey(autoGenerate = true)
           var idUser: Int = 0) : Serializable