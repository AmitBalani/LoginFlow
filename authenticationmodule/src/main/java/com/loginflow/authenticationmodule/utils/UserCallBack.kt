package com.loginflow.authenticationmodule.utils

import com.loginflow.authenticationmodule.database.UserData


interface UserCallBack {

    fun onSuccess(userData: UserData)

    fun onError()

    fun onBackPressed()

}