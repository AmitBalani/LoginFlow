package com.loginflow.authenticationmodule.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.loginflow.authenticationmodule.utils.Constant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [UserData::class], version = 1)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

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
        var INSTANCE: UserDatabase? = null
        fun getDatabase(
            context: Context,
            scope: CoroutineScope): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(context, UserDatabase::class.java, Constant.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .addCallback(FileDatabaseCallback(scope))
                        .build()
                INSTANCE = instance
                instance
            }
        }
    }
}