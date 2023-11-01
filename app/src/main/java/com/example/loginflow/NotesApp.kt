package com.example.loginflow

import com.loginflow.authenticationmodule.AuthenticationApp
import com.example.loginflow.database.NotesDatabase
import com.example.loginflow.database.NotesRepository
import com.example.loginflow.utils.SharedPreference
import com.example.loginflow.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NotesApp : AuthenticationApp() {


    private val applicationScope = CoroutineScope(SupervisorJob())
    private val database by lazy { NotesDatabase.getDatabase(this, applicationScope) }
    val notesRepository by lazy { NotesRepository(database.notesDao()) }
    private lateinit var sharedPreference: SharedPreference


    override fun onCreate() {
        super.onCreate()
        instance = this
        sharedPreference = SharedPreference(this)
        Utils.setNightMode(sharedPreference, this)
    }

    companion object {
        var instance: NotesApp? = null
    }

}