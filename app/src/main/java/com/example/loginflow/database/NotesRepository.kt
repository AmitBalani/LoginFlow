package com.example.loginflow.database

import androidx.annotation.WorkerThread
import com.example.loginflow.data.local.NotesData

class NotesRepository(private val notesDao: NotesDao?) {


    @WorkerThread
    suspend fun insert(notesData: NotesData): Long? {
        return notesDao?.insert(notesData)
    }

    //
    @WorkerThread
    suspend fun update(notesData: NotesData) : Int?{
      return notesDao?.update(notesData)
    }

    //
    @WorkerThread
    suspend fun delete(notesData: NotesData) : Int?{
        return notesDao?.delete(notesData)
    }

    @WorkerThread
    suspend fun getList(): List<NotesData>? {
        return notesDao?.getList()
    }


}