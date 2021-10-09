package com.mobile.hackatoners.utils

import android.content.Context
import android.content.SharedPreferences

class DataHolder private constructor(
    private val sharedPreferences: SharedPreferences
) {

    var coins: Int
        get() = sharedPreferences.getInt(KEY_COINS, 0)
        set(value) = sharedPreferences.edit().putInt(KEY_COINS, value).apply()

    var potions: Int
        get() = sharedPreferences.getInt(KEY_POTIONS, 0)
        set(value) = sharedPreferences.edit().putInt(KEY_POTIONS, value).apply()

    var forestLevel: Int
        get() = sharedPreferences.getInt(KEY_FOREST_LEVEL, 0)
        set(value) = sharedPreferences.edit().putInt(KEY_FOREST_LEVEL, value).apply()

    var desertLevel: Int
        get() = sharedPreferences.getInt(KEY_DESERT_LEVEL, 0)
        set(value) = sharedPreferences.edit().putInt(KEY_DESERT_LEVEL, value).apply()

    var hillLevel: Int
        get() = sharedPreferences.getInt(KEY_HILL_LEVEL, 0)
        set(value) = sharedPreferences.edit().putInt(KEY_HILL_LEVEL, value).apply()

    var isMapTutorialComplete: Boolean
        get() = sharedPreferences.getBoolean(KEY_MAP_TUTORIAL_COMPLETE, false)
        set(value) = sharedPreferences.edit().putBoolean(KEY_MAP_TUTORIAL_COMPLETE, value).apply()

    var isRealWorldUnlocked: Boolean
        get() = sharedPreferences.getBoolean(KEY_REAL_WORLD_UNLOCKED, false)
        set(value) = sharedPreferences.edit().putBoolean(KEY_REAL_WORLD_UNLOCKED, value).apply()

    var isFightOnboardingComplete: Boolean
        get() = sharedPreferences.getBoolean(FIGHT_ONBOARDING_COMPLETE, false)
        set(value) = sharedPreferences.edit().putBoolean(FIGHT_ONBOARDING_COMPLETE, value).apply()

    companion object {

        private const val XML_FILE_NAME = "com.mobile.hackatoners_preferences"

        private const val KEY_COINS = "coins"
        private const val KEY_POTIONS = "potions"

        private const val KEY_FOREST_LEVEL = "forest_level"
        private const val KEY_DESERT_LEVEL = "desert_level"
        private const val KEY_HILL_LEVEL = "hill_level"

        private const val KEY_MAP_TUTORIAL_COMPLETE = "is_map_tutorial_complete"
        private const val KEY_REAL_WORLD_UNLOCKED = "is_real_world_unlocked"

        private const val FIGHT_ONBOARDING_COMPLETE = "fight_onboarding_complete"

        private var dataHolder: DataHolder? = null

        fun getInstance(context: Context): DataHolder {
            val sharedPrefs = context.getSharedPreferences(XML_FILE_NAME, Context.MODE_PRIVATE)
            return dataHolder ?: DataHolder(sharedPrefs).also {
                dataHolder = it
            }
        }
    }
}