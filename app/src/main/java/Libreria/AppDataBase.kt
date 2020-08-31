package Libreria

import Interface.CitasDao
import Models.Cita
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Cita::class], version = 1)
abstract class AppDataBase: RoomDatabase() {

    abstract fun citas(): CitasDao

    companion object{
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase{
            val tempInstance = INSTANCE

            if(tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext, AppDataBase::class.java, "app_Firebase").build()

                INSTANCE = instance

                return instance
            }
        }
    }

}