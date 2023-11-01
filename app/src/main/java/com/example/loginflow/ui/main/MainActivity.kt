package com.example.loginflow.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.loginflow.databinding.ActivityMainBinding
import com.example.loginflow.ui.notelist.NoteListActivity
import com.example.loginflow.utils.Constant
import com.example.loginflow.utils.SharedPreference
import com.google.gson.Gson
import com.loginflow.authenticationmodule.AuthenticationApp
import com.loginflow.authenticationmodule.database.UserData
import com.loginflow.authenticationmodule.utils.UserCallBack

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var sharedPreference: SharedPreference
    private var handler: Handler? = null


    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        splashScreen.setKeepOnScreenCondition { true }
        setContentView(binding.root)
        if (intent.getStringExtra(Constant.LOGOUT_ACTION) == Constant.LOGOUT) {
            initApp()
        } else {
            handler = Handler(Looper.getMainLooper())
            handler?.postDelayed({
                splashScreen.setKeepOnScreenCondition { false }
                initApp()
            }, 3000)
        }
    }

    private fun initApp() {
        sharedPreference = SharedPreference(this)
        if (sharedPreference.getBoolean(Constant.IS_LOGIN)) {
            val intent = Intent(this@MainActivity, NoteListActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        } else {
            initAuthFLow()
        }
    }

    private fun initAuthFLow() {
        AuthenticationApp.instance?.startLoginActivity(this, object : UserCallBack {
            override fun onSuccess(userData: UserData) {
                Log.e("qwqw","onError")
                sharedPreference.setBoolean(Constant.IS_LOGIN, true)
                sharedPreference.setString(Constant.EMAIL_ID, userData.emailId)
                sharedPreference.setString(Constant.USER_DATA, Gson().toJson(userData))
                val intent = Intent(this@MainActivity, NoteListActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                finish()
            }

            override fun onError() {
                Log.e("qwqw","onError")
            }

            override fun onBackPressed() {
                finish()
            }
        })
    }
}