package com.rolandoselvera.founditall.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

private const val CATEGORY_PREF_KEY = "category"

class PreferencesProvider(context: Context?) {

    private val appContext = context?.applicationContext

    private val prefManager: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    fun saveCategory(category: Int) {
        prefManager.edit().putInt(CATEGORY_PREF_KEY, category).apply()
    }

    fun getCategory(): Int {
        return when (prefManager.getInt(CATEGORY_PREF_KEY, 0)) {
            1 -> 1

            2 -> 2

            3 -> 3

            4 -> 4

            5 -> 5

            6 -> 6

            7 -> 7

            else -> 0
        }
    }
}