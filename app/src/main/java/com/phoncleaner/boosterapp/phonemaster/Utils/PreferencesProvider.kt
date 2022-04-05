package com.phoncleaner.boosterapp.phonemaster.Utils

import android.content.Context
import com.phoncleaner.boosterapp.phonemaster.MyApplication

object PreferencesProvider{

    private val preferences = MyApplication.getInstance().getSharedPreferences("waseem", Context.MODE_PRIVATE)

    fun getInstance() = preferences
}