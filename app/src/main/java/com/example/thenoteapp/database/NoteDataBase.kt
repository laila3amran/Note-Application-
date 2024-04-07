package com.example.thenoteapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.thenoteapp.model.Note

@Database(entities = [Note::class], version = 1)

abstract class NoteDataBase : RoomDatabase() {
    abstract fun getNoteDao(): NoteDao
    companion object{
        @Volatile
        private var instance : NoteDataBase?= null
        private val LOCK = Any()
        operator fun invoke(context: Context) =
            instance?:
            Synchronized(LOCK){
            instance?:
            createDatabase(context).also{
                instance = it
            }
        }

        private fun Synchronized(lock: Any, function: () -> NoteDataBase): Synchronized {
            TODO("Not yet implemented")
        }


        private fun createDatabase(context: Context)=
            Room.databaseBuilder(context.applicationContext,
                NoteDataBase:: class.java,"note_db").build()
    }
}