package com.rolandoselvera.founditall.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ResultDTO::class], version = 1, exportSchema = false)
abstract class ResultRoomDatabase : RoomDatabase() {

    // La base de datos necesita saber sobre el DAO. Dentro del cuerpo de la clase, declara una
    // función abstracta que muestre el ItemDao. Puedes tener varios DAO.
    abstract fun resultDao(): ResultDao

    companion object {

        @Volatile
        private var INSTANCE: ResultRoomDatabase? = null

        fun getDatabase(context: Context): ResultRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ResultRoomDatabase::class.java,
                    "founditall_db"
                ).fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance

                return instance
            }
        }
    }
}