package Interface

import Models.Cita
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CitasDao {
    @Query("SELECT * FROM citas")

    fun getAll(): LiveData<List<Cita>>

    @Query("SELECT * FROM citas WHERE idCita = :id")
    fun get(id: Int): LiveData<Cita>

    @Insert
    fun insertAll(vararg citas: Cita)

    @Update
    fun update(cita: Cita)

    @Delete
    fun delete(cita: Cita)
}