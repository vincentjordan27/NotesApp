package com.example.android.mynotesapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android.mynotesapp.R
import com.example.android.mynotesapp.data.Note
import kotlinx.android.synthetic.main.item_note.view.*

class NoteAdapter(private val listener: (Note) -> Unit): PagedListAdapter<Note, NoteAdapter.NoteViewHolder>(DIFF_CALLBACK)  {

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Note> = object : DiffUtil.ItemCallback<Note>(){
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(note: Note, listener: (Note) -> Unit){
            with(itemView){
                when (note.priority) {
                    3 -> {
                        card_view.background = ContextCompat.getDrawable(context,R.drawable.cardview_bg_3)
                    }
                    2 -> {
                        card_view.background = ContextCompat.getDrawable(context,R.drawable.cardview_bg_2)
                    }
                    else -> {
                        card_view.background = ContextCompat.getDrawable(context,R.drawable.cardview_bg_1)
                    }
                }
                title_item.text = note.title
                desc_item.text = note.data
                setOnClickListener {
                    listener(note)
                }

            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note,parent,false)
        return NoteViewHolder(view)
    }


    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position) as Note, listener)
    }
}