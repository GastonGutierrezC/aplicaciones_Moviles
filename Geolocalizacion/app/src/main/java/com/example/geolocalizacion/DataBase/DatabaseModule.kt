package com.example.geolocalizacion.DataBase


import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRecorridoDatabase(context: Context): RouteDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            RouteDatabase::class.java,
            "recorrido_database"
        ).build()
    }

    @Provides
    fun provideRecorridoDao(database: RouteDatabase): RecorridoDao {
        return database.recorridoDao()
    }
}
