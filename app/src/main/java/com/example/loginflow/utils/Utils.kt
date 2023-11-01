package com.example.loginflow.utils

import android.app.UiModeManager
import android.content.Context
import android.os.Build
import android.os.Build.VERSION
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES

object Utils {

    fun setNightMode(sharedPreference: SharedPreference, context: Context) {
        if (VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val oUiModeManager = context.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
            if (sharedPreference.getString(Constant.THEME_TYPE) == Constant.DARK_THEME) {
                oUiModeManager.setApplicationNightMode(UiModeManager.MODE_NIGHT_YES)
            } else if (sharedPreference.getString(Constant.THEME_TYPE) == Constant.LIGHT_THEME) {
                oUiModeManager.setApplicationNightMode(UiModeManager.MODE_NIGHT_NO)
            }else{
                oUiModeManager.setApplicationNightMode(UiModeManager.MODE_NIGHT_AUTO)
            }
        } else {
            if (sharedPreference.getString(Constant.THEME_TYPE) == Constant.DARK_THEME) {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            } else if (sharedPreference.getString(Constant.THEME_TYPE) == Constant.LIGHT_THEME) {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }
    }

}