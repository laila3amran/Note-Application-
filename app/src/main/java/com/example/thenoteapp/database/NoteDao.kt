package com.example.thenoteapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.thenoteapp.model.Note

@Dao
abstract class NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note) {}

    @Update
    suspend fun updateNote(note: Note){}

    @Delete
    suspend fun deleteNote(note: Note){}

    @Query ("SELECT * FROM NOTES ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Note>> {return TODO("Provide the return value") }

    @Query ("SELECT * FROM NOTES WHERE noteTitle  LIKE :query OR noteDec LIKE :query ")
    fun searchNote(query: String? ) : LiveData<List<Note>>{ return TODO("Provide the return value") }





}