package com.example.loginflow.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.loginflow.data.local.NotesData
import com.example.loginflow.utils.Constant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [NotesData::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun notesDao(): NotesDao

    private class FileDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let {
                scope.launch(Dispatchers.IO) {
                }
            }
        }
    }

    companion object {
        var INSTANCE: NotesDatabase? = null
        fun getDatabase(
            context: Context,
            scope: CoroutineScope): NotesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(context, NotesDatabase::class.java, Constant.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .addCallback(FileDatabaseCallback(scope))
                        .build()
                INSTANCE = instance
                instance
            }
        }
    }
}