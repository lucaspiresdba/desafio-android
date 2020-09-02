package br.com.lucaspires.data.source.local

import android.content.SharedPreferences

class SharedPreferencesHelperImp(private val sharedPreferences: SharedPreferences) :
    SharedPreferencesHelper {

    companion object {
        private const val LAST_UPDATE_CACHE = "lastUpdateCache"
    }

    override fun checkIfNeedUpdateCache(): Boolean {
        return ((System.currentTimeMillis() -
                sharedPreferences.getLong(LAST_UPDATE_CACHE, 0)) / 1000) / 60 >= 15
    }

    override fun saveLastUpdate() {
        sharedPreferences.edit().putLong(LAST_UPDATE_CACHE, System.currentTimeMillis()).apply()
    }
}