package com.example.android.mynotesapp.di

import android.content.Context
import com.example.android.mynotesapp.data.DataSource
import com.example.android.mynotesapp.data.Repository
import com.example.android.mynotesapp.data.room.NoteDatabase

class Injection {
    companion object {
        fun provideRepository(context: Context): Repository{
            val database = NoteDatabase.getInstance(context)
            return Repository.getInstance(database)
        }
    }
}