package com.loginflow.authenticationmodule.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.loginflow.authenticationmodule.utils.Constant

@Entity(tableName = Constant.DB_TABLE_NAME/*, indices = [Index(value = ["emailId"], unique = true)]*/)
data class UserData (

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "firstName")
    var firstName: String? = null,

    @ColumnInfo(name = "lastName")
    var lastName: String? = null,

    @ColumnInfo(name = "phone")
    var phoneNumber: String? = null,

    @ColumnInfo(name = "emailId")
    var emailId: String? = null,

    @ColumnInfo(name = "password")
    var password: String? = null,

)