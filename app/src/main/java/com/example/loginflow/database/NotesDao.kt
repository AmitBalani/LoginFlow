package com.example.loginflow.database

import androidx.room.*
import com.example.loginflow.data.local.NotesData
import com.example.loginflow.utils.Constant

@Dao
interface NotesDao {

    @Insert
    suspend fun insert(notesData: NotesData) : Long
//
    @Query("SELECT * FROM ${Constant.DB_TABLE_NAME}")
    suspend fun getList(): List<NotesData>

    @Update
    suspend fun update(notesData: NotesData) : Int

    @Delete
    suspend fun delete(notesData: NotesData) : Int
}