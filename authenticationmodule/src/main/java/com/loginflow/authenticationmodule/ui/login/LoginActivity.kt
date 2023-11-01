package com.loginflow.authenticationmodule.ui.login

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.gson.Gson
import com.loginflow.authenticationmodule.AuthenticationApp
import com.loginflow.authenticationmodule.R
import com.loginflow.authenticationmodule.databinding.ActivityLoginBinding
import com.loginflow.authenticationmodule.ui.register.RegisterActivity
import com.loginflow.authenticationmodule.utils.Validation


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var validation: Validation
    private val viewModel: LoginViewModel by viewModels { LoginViewModelFactory((application as AuthenticationApp).repository) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        validation = Validation(this)
        init()
    }

    private fun init() {
        initObservers()
        binding.btnLogin.setOnClickListener {
            loginUser()
        }
        binding.txtRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        onBackPressedDispatcher.addCallback(this /* lifecycle owner */, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                AuthenticationApp.instance?.userCallBack?.onBackPressed()
                finish()
            }
        })
    }

    private fun initObservers() {
        viewModel.userData.observe(this) {
            if (it == null) {
                showAlertDialog(getString(R.string.login_error_message), true)
            } else {
                Log.e("TAG", "getDataFromDB: " + Gson().toJson(it))
                if (binding.etPassword.text.toString() == it.password) {
                    finish()
                    AuthenticationApp.instance?.userCallBack?.onSuccess(it)
                } else {
                    showAlertDialog(getString(R.string.you_enter_wrong_password), false)
                }
            }
        }
    }

    private fun loginUser() {
        if (!validation.validateEmail(binding.etEmail.text.toString())) {
            return
        } else if (!validation.validatePassword(binding.etPassword.text.toString())) {
            return
        } else {
            getDataFromDB()
        }
    }

    private fun getDataFromDB() {
        viewModel.getUserData(binding.etEmail.text.toString())
    }

    private fun showAlertDialog(message: String, isRedirect: Boolean) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage(message)
        builder.setCancelable(false)

        val typefaceRegular =  ResourcesCompat.getFont(this, R.font.poppins_regular)
        val typefaceMedium =  ResourcesCompat.getFont(this, R.font.poppins_medium)

        if (isRedirect) {
            builder.setPositiveButton(getString(R.string.yes)) { _: DialogInterface?, _: Int ->
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
            builder.setNegativeButton(getString(R.string.no)) { dialog: DialogInterface, _: Int ->
                dialog.cancel()
            }
        } else {
            builder.setPositiveButton(getString(R.string.cancel)) { dialog: DialogInterface, _: Int ->
                dialog.cancel()
            }
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
        alertDialog.findViewById<TextView>(android.R.id.message).typeface = typefaceRegular
        alertDialog.findViewById<Button>(android.R.id.button1).typeface = typefaceMedium
        alertDialog.findViewById<Button>(android.R.id.button2).typeface = typefaceMedium

    }

}