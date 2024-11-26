package com.myprimer.seproductive.Database

import android.app.Application
import androidx.room.Room


class MainApplication: Application() {


    companion object {            // acceso global y eficiente a la base de datos de tu aplicación.
        lateinit var  todoDatabase: TodoDatabase
    }

    override  fun onCreate() {
        super.onCreate()
        todoDatabase = Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java,
            TodoDatabase.NAME
        ).build()
    }

}