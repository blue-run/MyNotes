package com.example.mynotes.db

import androidx.room.*

@Dao                                // Data Access object -->
interface NoteDao {                 // Dao is an object used to execute commands to Query or
    @Insert                         // communicate with database
    suspend fun addNote(note: Note)


    @Query("SELECT * FROM note ORDER BY id DESC ")
    suspend fun getAllNotes(): List<Note>

    @Insert                                             // suspend functions can be called only inside
    suspend fun addMultipleNotes(vararg note: Note)      // Coroutine Scope

    @Update
    suspend fun updateNote(note:Note)

    @Delete
    suspend fun deleteNote(note: Note)

}