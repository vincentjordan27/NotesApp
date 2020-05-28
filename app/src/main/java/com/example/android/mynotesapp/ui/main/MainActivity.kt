package com.example.android.mynotesapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.android.mynotesapp.R
import com.example.android.mynotesapp.ui.editnote.EditNote
import com.example.android.mynotesapp.viewmodel.ViewModelFactory
import com.example.android.mynotesapp.worker.NoteWorker
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar.setTitleTextColor(ContextCompat.getColor(applicationContext,android.R.color.white))
        setSupportActionBar(toolbar)

        fab_btn.setOnClickListener {
            val intent = Intent(this, EditNote::class.java)
            intent.putExtra("requestCode",EditNote.ADD)
            startActivityForResult(intent,EditNote.ADD)

        }

        noteAdapter = NoteAdapter {
            val intent = Intent(this,EditNote::class.java)
            intent.putExtra(EditNote.DATA, it)
            intent.putExtra("requestCode",EditNote.UPDATE)
            startActivityForResult(intent, EditNote.UPDATE)
        }
        val factory = ViewModelFactory.getInstance(applicationContext)
        viewModel = ViewModelProvider(this,factory)[MainViewModel::class.java]
        viewModel.fetchData()?.observe(this, Observer {
            if (it != null){
                rv_main.visibility = View.VISIBLE
                tv_not_found.visibility = View.GONE
                noteAdapter.submitList(it)
                Log.d("Debug",it.toString())
                noteAdapter.notifyDataSetChanged()
            }else{
                rv_main.visibility = View.GONE
                tv_not_found.visibility = View.VISIBLE
            }
        })

        val periodicWorkRequest = PeriodicWorkRequest.Builder(NoteWorker::class.java,1, TimeUnit.DAYS)
            .build()

        WorkManager.getInstance(applicationContext).enqueue(periodicWorkRequest)

        with(rv_main){
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = noteAdapter
        }


    }



    override fun onResume() {
        super.onResume()

        viewModel.fetchData()?.observe(this, Observer {
            if (it != null){
                rv_main.visibility = View.VISIBLE
                tv_not_found.visibility = View.GONE
                noteAdapter.submitList(it)
                noteAdapter.notifyDataSetChanged()
            }else{
                rv_main.visibility = View.GONE
                tv_not_found.visibility = View.VISIBLE
            }
        })

    }


}
