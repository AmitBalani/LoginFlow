package com.loginflow.authenticationmodule.utils

import android.content.Context
import android.widget.Toast
import com.loginflow.authenticationmodule.R

class Validation(private val context: Context) {

    private val passwordVal = "^" + "(?=\\S+$)" +  //no white spaces
            ".{6,}" +  //at least 4 characters
            "$"

    fun validatePhoneNo(phoneNumber: String?): Boolean {
        return if (phoneNumber.isNullOrEmpty()) {
            Toast.makeText(
                context, context.getString(R.string.please_enter_phone_number), Toast.LENGTH_SHORT
            ).show()
            false
        } else {
            true
        }
    }


    fun validateFirstName(firstName: String?): Boolean {
        return if (firstName.isNullOrEmpty()) {
            Toast.makeText(
                context, context.getString(R.string.please_enter_first_name), Toast.LENGTH_SHORT
            ).show()
            false
        } else {
            true
        }
    }


    fun validateLastName(lastName: String?): Boolean {
        return if (lastName.isNullOrEmpty()) {
            Toast.makeText(
                context, context.getString(R.string.please_enter_last_name), Toast.LENGTH_SHORT
            ).show()
            false
        } else {
            true
        }
    }

    fun validateEmail(emailText: String?): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return if (emailText.isNullOrEmpty()) {
            Toast.makeText(
                context, context.getString(R.string.please_enter_email_id), Toast.LENGTH_SHORT
            ).show()
            false
        } else if (!emailText.matches(Regex(emailPattern))) {
            Toast.makeText(
                context, context.getString(R.string.please_enter_valid_email), Toast.LENGTH_SHORT
            ).show()
            false
        } else {
            true
        }
    }

    fun validatePassword(passwordText: String): Boolean {
        return if (passwordText.isEmpty()) {
            Toast.makeText(
                context, context.getString(R.string.please_enter_password), Toast.LENGTH_SHORT
            ).show()
            false
        } else if (!passwordText.matches(passwordVal.toRegex())) {
            Toast.makeText(
                context, context.getString(R.string.password_pattern_mismatch), Toast.LENGTH_LONG
            ).show()
            false
        } else {
            true
        }
    }

    fun validateConfirmPassword(passwordText: String, confirmPasswordText: String): Boolean {
        return if (confirmPasswordText.isEmpty()) {
            Toast.makeText(
                context,
                context.getString(R.string.please_enter_password_confirm),
                Toast.LENGTH_SHORT
            ).show()
            false
        } else if (passwordText != confirmPasswordText) {
            Toast.makeText(
                context,
                context.getString(R.string.password_and_confirm_password_should_be_same),
                Toast.LENGTH_LONG
            ).show()
            false
        } else {
            true
        }
    }

    //        val passwordVal = "^" +  //"(?=.*[0-9])" +         //at least 1 digit
//                //"(?=.*[a-z])" +         //at least 1 lower case letter
//                //"(?=.*[A-Z])" +         //at least 1 upper case letter
////                "(?=.*[a-zA-Z])" +  //any letter
////                "(?=.*[@#$%^&+=])" +  //at least 1 special character
//                "(?=\\S+$)" +  //no white spaces
//                ".{6,}" +  //at least 4 characters
//                "$"

}