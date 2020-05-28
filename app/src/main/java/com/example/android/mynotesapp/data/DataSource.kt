package com.example.android.mynotesapp.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList

interface DataSource {
    fun getNotes(): DataSource.Factory<Int, Note>

    suspend fun insertNote(note: Note)

    suspend fun updateNote(note: Note)

    suspend fun deleteNote(note: Note)

    fun getNote(): Note
}