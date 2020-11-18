package com.montbrungroup.androidpaguin

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Preferences @Inject constructor(app: Application) {

    companion object {

        private const val SHARED_PREF_NAME = "paging_preferences"

        private inline fun SharedPreferences.put(body: SharedPreferences.Editor.() -> Unit) {
            val editor = this.edit()
            editor.body()
            editor.apply()
            editor.commit()
        }

    }

    private val sharedPreferences: SharedPreferences = app.getSharedPreferences(
        SHARED_PREF_NAME,
        Context.MODE_PRIVATE
    )

    var firstPage: Int
        get() = sharedPreferences.getInt("firstPage", Consts.NO_PAGE)
        set(value) = sharedPreferences.put {
            putInt("firstPage", value)
        }

    var lastPage: Int
        get() = sharedPreferences.getInt("lastPage", Consts.NO_PAGE)
        set(value) = sharedPreferences.put {
            putInt("lastPage", value)
        }

}