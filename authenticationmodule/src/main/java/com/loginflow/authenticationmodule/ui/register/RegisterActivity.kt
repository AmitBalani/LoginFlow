package com.loginflow.authenticationmodule.ui.register

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.loginflow.authenticationmodule.AuthenticationApp
import com.loginflow.authenticationmodule.R
import com.loginflow.authenticationmodule.database.UserData
import com.loginflow.authenticationmodule.databinding.ActivityRegisterBinding
import com.loginflow.authenticationmodule.ui.login.LoginActivity
import com.loginflow.authenticationmodule.utils.Validation

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var validation: Validation
    private val viewModel: RegisterViewModel by viewModels { RegisterViewModelFactory((application as AuthenticationApp).repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        validation = Validation(this)
        init()
    }

    private fun init() {
        initObservers()
        binding.btnRegister.setOnClickListener {
            registerUser()
        }

        binding.txtLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

    }

    private fun initObservers() {
        viewModel.userData.observe(this) {
            if (it == null) {
                insertDataInDB()
            } else {
                showAlertDialog(
                    getString(R.string.this_email_id_is_already_exist), false
                )
            }
        }

        viewModel.isDataInsertedOrNot.observe(this) {
            if (it.toInt() > 0) {
                showAlertDialog(
                    getString(R.string.register_successfully), true
                )
            }
        }
    }


    private fun registerUser() {
        if (!validation.validateFirstName(binding.etFirstName.text.toString())) {
            return
        } else if (!validation.validateLastName(binding.etLastName.text.toString())) {
            return
        } else if (!validation.validatePhoneNo(binding.etPhoneNumber.text.toString())) {
            return
        } else if (!validation.validateEmail(binding.etEmailId.text.toString())) {
            return
        } else if (!validation.validatePassword(binding.etPassword.text.toString())) {
            return
        } else if (!validation.validateConfirmPassword(
                binding.etPassword.text.toString(), binding.etConfirmPass.text.toString()
            )
        ) {
            return
        } else {
            getDataFromDB()
        }
    }

    private fun getDataFromDB() {
        viewModel.getUserData(binding.etEmailId.text.toString())
    }

    private fun insertDataInDB() {
        viewModel.insert(
            UserData(
                firstName = binding.etFirstName.text.toString(),
                lastName = binding.etLastName.text.toString(),
                phoneNumber = binding.etPhoneNumber.text.toString(),
                emailId = binding.etEmailId.text.toString(),
                password = binding.etPassword.text.toString()
            )
        )
    }

    private fun showAlertDialog(message: String, isRedirect: Boolean) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage(message)
        builder.setCancelable(false)

        val typefaceRegular = ResourcesCompat.getFont(this, R.font.poppins_regular)
        val typefaceMedium = ResourcesCompat.getFont(this, R.font.poppins_medium)

        if (isRedirect) {
            builder.setPositiveButton(getString(R.string.login)) { _: DialogInterface?, _: Int ->
                val intent = Intent(this, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
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