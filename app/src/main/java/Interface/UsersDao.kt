package Interface

import Models.User
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UsersDao {
    @Query("SELECT * FROM users")
    fun getAll(): LiveData<List<User>>

    @Query("SELECT * FROM users WHERE idUser = :id")
    fun get(id: Int): LiveData<User>

    @Insert
    fun insertAll(vararg users: User): List<Long>

    @Update
    fun update(users: User)

    @Delete
    fun delete(users: User)
}