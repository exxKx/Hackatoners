package com.mobile.hackatoners.utils

import android.content.Context
import android.content.SharedPreferences

class DataHolder private constructor(
    private val sharedPreferences: SharedPreferences
) {

    var money: Int
        get() = sharedPreferences.getInt(KEY_MONEY, 0)
        set(value) = sharedPreferences.edit().putInt(KEY_MONEY, value).apply()

    var level: Int
        get() = sharedPreferences.getInt(KEY_LEVEL, 0)
        set(value) = sharedPreferences.edit().putInt(KEY_LEVEL, value).apply()

    companion object {

        private const val XML_FILE_NAME = "com.mobile.hackatoners_preferences"

        private const val KEY_MONEY = "money"
        private const val KEY_LEVEL = "level"

        private var dataHolder: DataHolder? = null

        fun getInstance(context: Context): DataHolder {
            val sharedPrefs = context.getSharedPreferences(XML_FILE_NAME, Context.MODE_PRIVATE)
            return dataHolder ?: DataHolder(sharedPrefs).also {
                dataHolder = it
            }
        }
    }
}