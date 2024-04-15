package com.example.actevaluabletres.ui.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.actevaluabletres.ui.modelo.Team

object PreferencesUtil {
    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("favorites_pref", Context.MODE_PRIVATE)
    }

    fun saveFavorites(context: Context, favorites: List<Team>) {
        val editor = getPreferences(context).edit()
        val json = Gson().toJson(favorites)
        editor.putString("favorites", json)
        editor.apply()
        Log.d("PreferencesUtil", "Saved favorites: $json")

    }

    fun loadFavorites(context: Context): List<Team> {
        val json = getPreferences(context).getString("favorites", null)
        Log.d("PreferencesUtil", "Loaded favorites: $json")
        return if (json != null) {
            val type = object : TypeToken<List<Team>>() {}.type
            Gson().fromJson(json, type)
        } else {
            emptyList()
        }
    }
}
