package com.example.android.mynotesapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.mynotesapp.data.Repository
import com.example.android.mynotesapp.di.Injection
import com.example.android.mynotesapp.ui.editnote.EditViewModel
import com.example.android.mynotesapp.ui.main.MainViewModel

class ViewModelFactory private constructor(private val repository: Repository) : ViewModelProvider.NewInstanceFactory(){

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(context: Context) : ViewModelFactory =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: ViewModelFactory(Injection.provideRepository(context))
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(EditViewModel::class.java) -> {
                EditViewModel(
                    repository
                ) as T
            }
            else -> throw Throwable("Unknown ViewModel Class " + modelClass.name)
        }
    }
}