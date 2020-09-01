package Libreria

import Interface.UsersDao
import Models.User
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class AppDataBaseUsers: RoomDatabase() {

    abstract fun users(): UsersDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBaseUsers? = null

        fun getDatabase(context: Context): AppDataBaseUsers {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBaseUsers::class.java,
                    "app_database_users"
                ).build()

                INSTANCE = instance

                return instance
            }
        }
    }
}