package com.mobile.hackatoners.utils

import android.content.Context
import android.content.SharedPreferences

class DataHolder private constructor(
    private val sharedPreferences: SharedPreferences
) {

    var money: Int
        get() = sharedPreferences.getInt(KEY_MONEY, 0)
        set(value) = sharedPreferences.edit().putInt(KEY_MONEY, value).apply()

    var currentRegion: Int
        get() = sharedPreferences.getInt(KEY_REGION, 0)
        set(value) = sharedPreferences.edit().putInt(KEY_REGION, value).apply()

    var isRealWorldUnlocked: Boolean
        get() = sharedPreferences.getBoolean(KEY_REAL_WORLD_UNLOCKED, false)
        set(value) = sharedPreferences.edit().putBoolean(KEY_REAL_WORLD_UNLOCKED, value).apply()

    companion object {

        private const val XML_FILE_NAME = "com.mobile.hackatoners_preferences"

        private const val KEY_MONEY = "money"
        private const val KEY_REGION = "region"

        private const val KEY_REAL_WORLD_UNLOCKED = "is_real_world_unlocked"

        private var dataHolder: DataHolder? = null

        fun getInstance(context: Context): DataHolder {
            val sharedPrefs = context.getSharedPreferences(XML_FILE_NAME, Context.MODE_PRIVATE)
            return dataHolder ?: DataHolder(sharedPrefs).also {
                dataHolder = it
            }
        }
    }
}