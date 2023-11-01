package com.example.loginflow.ui.addnote


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.loginflow.data.local.NotesData
import com.example.loginflow.database.NotesRepository
import kotlinx.coroutines.launch

class AddNoteViewModel(private val notesRepository: NotesRepository) : ViewModel() {

    var isDataInsertedOrNot = MutableLiveData<Long>()
    var isDataUpdatedOrNot = MutableLiveData<Int>()
    var isDataDeletedOrNot = MutableLiveData<Int>()


    fun insert(notesData: NotesData) = viewModelScope.launch {
        isDataInsertedOrNot.postValue(notesRepository.insert(notesData))
    }

    fun update(notesData: NotesData) = viewModelScope.launch {
        isDataUpdatedOrNot.postValue(notesRepository.update(notesData))
    }

    fun delete(notesData: NotesData) = viewModelScope.launch {
        isDataDeletedOrNot.postValue(notesRepository.delete(notesData))
    }


}

class AddNoteModelFactory(private val notesRepository: NotesRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddNoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddNoteViewModel(notesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}



