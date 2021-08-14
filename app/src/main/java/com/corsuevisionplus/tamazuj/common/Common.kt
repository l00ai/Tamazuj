package com.corsuevisionplus.tamazuj.common

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.corsuevisionplus.tamazuj.models.UserInfo
import com.google.gson.Gson

class Common(val context: Context) {

    var sharedPreferences: SharedPreferences? = null
    init {
        sharedPreferences = context.getSharedPreferences("SETTING_PREF", AppCompatActivity.MODE_PRIVATE)
    }

    fun getUser(): UserInfo?{
        val gson = Gson()
        return gson.fromJson(sharedPreferences!!.getString("USER",""),UserInfo::class.java)
    }
}