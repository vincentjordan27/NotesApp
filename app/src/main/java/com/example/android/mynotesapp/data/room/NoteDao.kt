package com.example.android.mynotesapp.data.room

import androidx.paging.DataSource
import androidx.room.*
import com.example.android.mynotesapp.data.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM note ORDER BY priority DESC")
    fun getNotes() : DataSource.Factory<Int, Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM note ORDER BY priority DESC LIMIT 1")
    fun getNote() : Note

}