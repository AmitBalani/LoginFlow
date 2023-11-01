package com.loginflow.authenticationmodule.database

import androidx.annotation.WorkerThread

class UserDataRepository(private val userDao: UserDao?) {
    @WorkerThread
    suspend fun insert(userData: UserData): Long? {
        return userDao?.insert(userData)
    }

    @WorkerThread
    suspend fun getUserByEmailID(emailId: String): UserData? {
        return userDao?.getUserData(emailId)
    }
}