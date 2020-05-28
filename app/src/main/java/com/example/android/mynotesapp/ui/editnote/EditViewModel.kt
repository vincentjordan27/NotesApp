package com.example.android.mynotesapp.ui.editnote

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.mynotesapp.data.Note
import com.example.android.mynotesapp.data.Repository
import kotlinx.coroutines.launch
import java.lang.Exception

class EditViewModel(private val repository: Repository) : ViewModel() {
   fun insert(note: Note) {
       try {
           viewModelScope.launch {
               repository.insertNote(note)
           }
       } catch (e : Exception){
           Log.e(EditViewModel::class.java.simpleName, e.message.toString())
       }
   }

    fun delete(note: Note){
        try {
            viewModelScope.launch {
                repository.deleteNote(note)
            }
        }catch (e : Exception){
            Log.e(EditViewModel::class.java.simpleName, e.message.toString())
        }
    }

    fun update(note: Note){
        try {
             viewModelScope.launch {
                 repository.updateNote(note)
             }
        }catch (e : Exception){
            Log.e(EditViewModel::class.java.simpleName, e.message.toString())
        }
    }
}