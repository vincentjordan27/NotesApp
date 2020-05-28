package com.example.android.mynotesapp.ui.editnote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.android.mynotesapp.R
import com.example.android.mynotesapp.data.Note
import com.example.android.mynotesapp.viewmodel.ViewModelFactory
import com.example.android.mynotesapp.worker.NoteWorker
import kotlinx.android.synthetic.main.activity_edit_note.*
import java.util.concurrent.TimeUnit

class EditNote : AppCompatActivity() {

    private lateinit var note: Note
    private lateinit var viewModel: EditViewModel
    private var priority = 1

    companion object {
        const val ADD = 100
        const val RESULT_ADD = 101
        const val UPDATE = 200
        const val RESULT_UPDATE = 201
        const val DATA = "data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(applicationContext)
        viewModel = ViewModelProvider(this,factory)[EditViewModel::class.java]

        val requestCode = intent.getIntExtra("requestCode",0)
        if (requestCode == ADD){
            addNote()
        } else if(requestCode == UPDATE){
            note = intent.getParcelableExtra(DATA) as Note
            updateNote(note)
        }

    }

    private fun updateNote(note: Note) {
        btn_save.text = getString(R.string.update)
        title_edit.setText(note.title)
        editText.setText(note.data)
        when (note.priority) {
            1 -> {
                priority_low.isChecked = true
            }
            2 -> {
                priority_medium.isChecked = true
            }
            else -> priority_high.isChecked = true
        }

        btn_save.setOnClickListener {
            if (title_edit.text?.trim() == ""){
                title_edit.error = "Title Is Empty"
            }
            if (editText.text.trim() == ""){
                editText.error = "Description Is Empty"
            }
            note.title = title_edit.text?.trim().toString()
            note.data = editText.text.trim().toString()
            note.priority = priority

            viewModel.update(note)

            Toast.makeText(this,"Note Updated", Toast.LENGTH_SHORT).show()
            setResult(RESULT_UPDATE)
            finish()
        }
    }

    private fun addNote(){
        btn_save.setOnClickListener {
            if (title_edit.text?.trim() == ""){
                title_edit.error = "Title Is Empty"
            }
            if (editText.text.trim() == ""){
                editText.error = "Description Is Empty"
            }
            note = Note(title = title_edit.text?.trim().toString(), data = editText.text.trim().toString(), priority = priority, id = 0)
            viewModel.insert(note)

            Toast.makeText(this,"Note added", Toast.LENGTH_SHORT).show()

            setResult(RESULT_ADD)
            finish()
        }


    }



    fun onRadioButtonClicked(view: View) {
        when(view.id){
            R.id.priority_high -> priority = 3
            R.id.priority_medium -> priority = 2
            R.id.priority_low -> priority = 1
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            val alertDialogBuilder = AlertDialog.Builder(this)
            with(alertDialogBuilder){
                setTitle("Discard Changes")
                setMessage("Are You Sure To Go Back ?")
                    .setPositiveButton("Yes"){_, _ ->
                        finish()
                    }
                    .setNegativeButton("No"){dialog,_ ->
                        dialog.cancel()
                    }
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
        else if(item.itemId == R.id.action_delete){
            deleteNote(note)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteNote(note: Note) {
        val alertDialog = AlertDialog.Builder(this)
        with(alertDialog){
            setTitle("Delete")
            setMessage("Are You Sure To Delete ?")
                .setPositiveButton("Yes"){ _, _ ->
                    viewModel.delete(note)
                    Toast.makeText(context, "Note Deleted", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .setNegativeButton("No"){ dialog, _ ->
                    dialog.cancel()
                }
        }
        val dialog = alertDialog.create()
        dialog.show()
    }


}
