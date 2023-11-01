package com.example.loginflow.ui.addnote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.google.gson.Gson
import com.example.loginflow.NotesApp
import com.example.loginflow.R
import com.example.loginflow.data.local.NotesData
import com.example.loginflow.databinding.ActivityAddNoteBinding
import com.example.loginflow.utils.Constant
import com.example.loginflow.utils.SharedPreference
import com.example.loginflow.utils.Validation

class AddNoteActivity : AppCompatActivity() {

    private val viewModel: AddNoteViewModel by viewModels { AddNoteModelFactory((application as NotesApp).notesRepository) }
    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var validation: Validation
    private lateinit var sharedPreference: SharedPreference
    private var type: String? = null
    private var notesData: NotesData? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        validation = Validation(this)
        sharedPreference = SharedPreference(this)
        init()
    }

    private fun init() {
        initData()
        initObservers()
        binding.btnSave.setOnClickListener {
            addNoteIntoDB()
        }
        binding.btnDelete.setOnClickListener {
            notesData?.let { it1 -> viewModel.delete(it1) }
        }
    }

    private fun initData() {
        type = intent.getStringExtra(Constant.NOTE_TYPE)
        if (type == Constant.EDIT_NOTE) {
            notesData =
                Gson().fromJson(intent.getStringExtra(Constant.NOTE_DATA), NotesData::class.java)
            binding.etNotesTitle.setText(notesData?.noteTitle)
            binding.etNotes.setText(notesData?.notesDesc)
            binding.btnSave.text = getString(R.string.edit)
            binding.btnDelete.visibility = View.VISIBLE
        } else {
            binding.btnSave.text = getString(R.string.save)
            binding.btnDelete.visibility = View.GONE
        }
    }


    private fun initObservers() {
        viewModel.isDataInsertedOrNot.observe(this) {
            if (it.toInt() > 0) {
                Toast.makeText(
                    this, getString(R.string.note_added_successfully), Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(Constant.LIST_ACTION)
                sendBroadcast(intent)
                finish()
            }
        }

        viewModel.isDataUpdatedOrNot.observe(this) {
            if (it.toInt() > 0) {
                Toast.makeText(this,
                    getString(R.string.note_update_successfully), Toast.LENGTH_SHORT).show()
                val intent = Intent(Constant.LIST_ACTION)
                sendBroadcast(intent)
                finish()
            }

        }

        viewModel.isDataDeletedOrNot.observe(this) {
            if (it.toInt() > 0) {
                Toast.makeText(this,
                    getString(R.string.note_delete_successfully), Toast.LENGTH_SHORT).show()
                val intent = Intent(Constant.LIST_ACTION)
                sendBroadcast(intent)
                finish()
            }
        }
    }

    private fun addNoteIntoDB() {
        if (!validation.validateNoteTitle(binding.etNotesTitle.text.toString())) {
            return
        } else if (!validation.validateNoteDesc(binding.etNotes.text.toString())) {
            return
        } else {
            val time = System.currentTimeMillis()

            if (type == Constant.EDIT_NOTE) {
                notesData?.notesDesc = binding.etNotes.text.toString()
                notesData?.noteTitle = binding.etNotesTitle.text.toString()
                notesData?.updatedAt = time
                notesData?.let { viewModel.update(it) }

            } else {
                viewModel.insert(
                    NotesData(
                        noteTitle = binding.etNotesTitle.text.toString(),
                        notesDesc = binding.etNotes.text.toString(),
                        emailId = sharedPreference.getString(Constant.EMAIL_ID),
                        createdAt = time,
                        updatedAt = time
                    )
                )
            }
        }
    }


}