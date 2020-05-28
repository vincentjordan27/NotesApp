package com.example.android.mynotesapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.android.mynotesapp.data.Note
import com.example.android.mynotesapp.data.Repository

class MainViewModel(private val repository: Repository): ViewModel() {
    fun fetchData() : LiveData<PagedList<Note>>? {
        return LivePagedListBuilder(repository.getNotes(), 10).build()
    }

    fun getNote() : Note {
        return repository.getNote()
    }
}