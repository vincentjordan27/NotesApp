package com.example.android.mynotesapp.data

import com.example.android.mynotesapp.data.room.NoteDatabase

class Repository(private val noteDatabase: NoteDatabase): DataSource {

    companion object {
        @Volatile
        private var INSTANCE: Repository? = null

        fun getInstance(noteDatabase: NoteDatabase) =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: Repository(noteDatabase)
            }
    }

    override fun getNotes(): androidx.paging.DataSource.Factory<Int, Note> {
        return noteDatabase.noteDao().getNotes()
    }

    override suspend fun insertNote(note: Note) {
        noteDatabase.noteDao().addNote(note)
    }

    override suspend fun updateNote(note: Note) {
        noteDatabase.noteDao().updateNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        noteDatabase.noteDao().deleteNote(note)
    }

    override fun getNote(): Note {
        return noteDatabase.noteDao().getNote()
    }
}