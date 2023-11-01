package com.loginflow.authenticationmodule.database

import androidx.room.*
import com.loginflow.authenticationmodule.utils.Constant

@Dao
interface UserDao {

    @Insert
    suspend fun insert(userData: UserData) : Long
    
    @Query("SELECT * FROM ${Constant.DB_TABLE_NAME} WHERE emailId LIKE :emailId")
    suspend fun getUserData(emailId: String): UserData?
    
}