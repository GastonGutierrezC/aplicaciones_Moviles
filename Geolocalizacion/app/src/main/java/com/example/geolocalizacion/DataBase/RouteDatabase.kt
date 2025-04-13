// RouteDatabase.kt
package com.example.geolocalizacion.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RecorridoDTO::class], version = 1, exportSchema = false)
abstract class RouteDatabase : RoomDatabase() {

    abstract fun recorridoDao(): RecorridoDao

    companion object {
        @Volatile
        private var INSTANCE: RouteDatabase? = null

        fun getDatabase(context: Context): RouteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RouteDatabase::class.java,
                    "recorrido_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
