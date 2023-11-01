package com.example.loginflow.data.remote

data class AuthUserResponse(
    var id: Int = 0,
    var firstName: String? = null,
    var lastName: String? = null,
    var phoneNumber: String? = null,
    var emailId: String? = null,
    var password: String? = null,
)
