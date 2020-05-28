package com.example.android.mynotesapp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.android.mynotesapp.data.Note

@Database(entities = [Note::class],
    version = 1,
exportSchema = false)
abstract class NoteDatabase : RoomDatabase(){
    abstract fun noteDao(): NoteDao

    companion object {
        private var INSTANCE: NoteDatabase? = null

        fun getInstance(context: Context): NoteDatabase  =
            INSTANCE ?: synchronized(this){
                    INSTANCE ?: Room.databaseBuilder(
                        context.applicationContext,
                        NoteDatabase::class.java,
                        "mynotesapp"
                    ).build()
                }

    }
}