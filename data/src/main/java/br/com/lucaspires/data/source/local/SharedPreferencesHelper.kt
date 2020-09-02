package br.com.lucaspires.data.source.local

interface SharedPreferencesHelper {
    fun checkIfNeedUpdateCache(): Boolean
    fun saveLastUpdate()
}
