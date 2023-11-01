package com.example.loginflow.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.example.loginflow.data.local.NotesData
import com.example.loginflow.databinding.ItemNotesBinding
import com.example.loginflow.ui.addnote.AddNoteActivity
import com.example.loginflow.utils.Constant

class NoteListAdapter(val context: Context) : RecyclerView.Adapter<NoteListAdapter.ViewHolder>() {

    private var notesList: ArrayList<NotesData> = arrayListOf()


    inner class ViewHolder(val binding: ItemNotesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notesData = notesList[position]
        holder.binding.txtNoteTitle.text = notesData.noteTitle
        holder.binding.txtNoteDesc.text = notesData.notesDesc
//        holder.binding.txtEmail.text = notesData.emailId
//        holder.binding.txtDate.text = notesData.createdAt.toString()
        holder.binding.root.setOnClickListener {
            val intent = Intent(context, AddNoteActivity::class.java)
            intent.putExtra(Constant.NOTE_TYPE,Constant.EDIT_NOTE)
            intent.putExtra(Constant.NOTE_DATA,Gson().toJson(notesData))
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    fun addAddData(notesList: ArrayList<NotesData>) {
        try {
            this.notesList.clear()
            this.notesList.addAll(notesList)
            notifyDataSetChanged()
//            notifyItemRangeInserted(0, notesList.size)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun clear() {
        try {
            this.notesList.clear()
            notifyDataSetChanged()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}