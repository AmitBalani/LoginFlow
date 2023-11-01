package com.example.loginflow.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.loginflow.utils.Constant

@Entity(tableName = Constant.DB_TABLE_NAME)
data class NotesData(

    @PrimaryKey(autoGenerate = true) var id: Int = 0,

    @ColumnInfo(name = "noteTitle") var noteTitle: String? = null,

    @ColumnInfo(name = "notesDesc") var notesDesc: String? = null,

    @ColumnInfo(name = "emailId") var emailId: String? = null,

    @ColumnInfo(name = "createdAt") var createdAt: Long? = 0,

    @ColumnInfo(name = "updatedDate") var updatedAt: Long? = 0,

    )